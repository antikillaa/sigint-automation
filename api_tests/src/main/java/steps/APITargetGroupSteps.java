package steps;

import abs.EntityList;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import http.OperationResult;
import http.OperationsResults;
import model.TargetGroup;
import model.TargetGroupFilter;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TargetGroupService;
import utils.DateHelper;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static conditions.Conditions.isTrue;

@SuppressWarnings("unchecked")
public class APITargetGroupSteps extends APISteps {

    private TargetGroupService service = new TargetGroupService();
    private static Logger LOGGER = Logger.getLogger(APITargetGroupSteps.class);

    @When("I send create target group $with targets request")
    public void sendCreateRequest(String with) {
        TargetGroup group = getRandomTargetGroup();

        if (with.toLowerCase().equals("with")) {
            List<String> targets = new ArrayList<>();
            targets.add(Entities.getTargets().random().getId());
            group.setTargets(targets);
        }

        context.put("targetGroup", group);
        service.add(group);

    }

    @Then("Created target group is correct")
    public void targetGroupCorrect() {
        TargetGroup contextTargetGroup = context.get("targetGroup", TargetGroup.class);
        TargetGroup createdTargetGroup = Entities.getTargetGroups().getLatest();

        Verify.shouldBe(isTrue.element(equalsTargetGroups(createdTargetGroup, contextTargetGroup)));
    }

    /**
     * Compare two targetGroups
     * new targetGroups keep description in TargetGroupProperties
     *
     * @param checked checked targetGroup
     * @param etalon etalon targetGroup
     * @return true if two targetGroups is equal, false if otherwise
     */
    private boolean equalsTargetGroups(TargetGroup checked, TargetGroup etalon) {
        return Verify.isTrue(Conditions.equals(checked.getType(), etalon.getType())) &&
                Verify.isTrue(Conditions.equals(checked.getName(), etalon.getName())) &&
                checked.getDescription() == null ?
                Objects.equals(checked.getProperties().getDescription(), etalon.getProperties().getDescription()) :
                Objects.equals(checked.getDescription(), etalon.getProperties().getDescription());
    }

    @When("I send get target group details request")
    public void getTargetGroupRequest() {
        TargetGroup createdTargetGroup = Entities.getTargetGroups().getLatest();
        OperationResult<TargetGroup> operationResult = service.view(createdTargetGroup.getId());
        context.put("viewedTargetGroup", operationResult.getEntity());
    }

    @Then("Viewed target group is correct")
    public void viewedTargetGroupIsCorrect() {
        TargetGroup createdTarget = Entities.getTargetGroups().getLatest();
        TargetGroup viewedTargetGroup = context.get("viewedTargetGroup", TargetGroup.class);
        equalsTargetGroups(viewedTargetGroup, createdTarget);
    }

    @When("I send get list of target group request")
    public void getListOfTargetGroupsRequest() throws NullReturnException {
        OperationResult<EntityList<TargetGroup>> operationResult = service.listG4Compatibility();
        context.put("targetGroupEntityList", operationResult.getEntity());
    }

    @Then("Created target group $criteria list")
    public void targetGroupsContainNewTargetGroup(String criteria) throws NullReturnException {
        TargetGroup targetGroup = Entities.getTargetGroups().getLatest();
        EntityList<TargetGroup> list = context.get("targetGroupEntityList", EntityList.class);
        Boolean contains = false;
        for (TargetGroup entity : list) {
            if (Verify.isTrue(isTrue.element(equalsTargetGroups(targetGroup, entity)))) {
                contains = true;
                break;
            }
        }
        if (criteria.toLowerCase().equals("in")) {
            Verify.shouldBe(isTrue.element(contains));
        } else if (criteria.toLowerCase().equals("not in")) {
            Verify.shouldNotBe(isTrue.element(contains));
        } else {
            throw new AssertionError("Incorrect argument passed to step");
        }
    }

    @Then("target group $criteria list")
    public void existingTargetGroupContainsInList(String criteria) throws NullReturnException {
        TargetGroup targetGroup = Entities.getTargetGroups().getLatest();
        LOGGER.debug("Requested target group: " + targetGroup.getName());
        EntityList<TargetGroup> list = context.get("targetGroupEntityList", EntityList.class);
        Boolean contains = false;
        for (TargetGroup entity : list) {
            LOGGER.debug("Comparing with target group: " + entity.getName());
            if (targetGroup.getName().equals(entity.getName())) {
                contains = true;
                break;
            }
        }

        if (criteria.toLowerCase().equals("in")) {
            Verify.shouldBe(isTrue.element(contains));
        } else if (criteria.toLowerCase().equals("not in")) {
            Verify.shouldNotBe(isTrue.element(contains));
        } else {
            throw new AssertionError("Incorrect argument passed to step");
        }
    }

    @When("I send delete target group request")
    public void deleteTargetGroupRequest() {
        TargetGroup targetGroup = Entities.getTargetGroups().getLatest();
        context.put("targetGroup", targetGroup);
        service.remove(targetGroup);

    }

    @When("I send update target group request")
    public void updateTargetGroupRequest() {
        TargetGroup createdTargetGroup = Entities.getTargetGroups().getLatest();
        TargetGroup updatedTargetGroup = updateTargetGroup(createdTargetGroup);
        OperationResult<TargetGroup> operationResult = service.update(updatedTargetGroup);
        context.put("updatedTargetGroup", operationResult.getEntity());
    }

    @Then("Target group updated correctly")
    public void targetGroupUpdatedCorrectly() {
        TargetGroup updatedTargetGroup = context.get("updatedTargetGroup", TargetGroup.class);
        TargetGroup responseTargetGroup = Entities.getTargetGroups().getLatest();
        equalsTargetGroups(responseTargetGroup, updatedTargetGroup);
    }

    @Then("existing group is listed in list only once")
    public void groupNotDuplicated() {
        List<TargetGroup> targetGroups = Entities.getTargets().getLatest().getGroups();
        EntityList<TargetGroup> groups = context.get("targetGroupEntityList", EntityList.class);
        for (TargetGroup uploadedGroup : targetGroups) {
            int count = 0;
            for (TargetGroup group : groups) {
                if (uploadedGroup.getName().equals(group.getName())) {
                    Entities.getTargetGroups().updateEntity(uploadedGroup, group);
                    count += 1;
                }
            }
            Assert.assertTrue(count == 1);
        }
    }

    static TargetGroup getRandomTargetGroup() {
        return objectInitializer.randomEntity(TargetGroup.class);
    }

    private TargetGroup updateTargetGroup(TargetGroup targetGroup) {
        targetGroup.setName(RandomStringUtils.randomAlphabetic(10));
        targetGroup.setDescription(RandomStringUtils.randomAlphabetic(20));
        return targetGroup;
    }

    @Then("Target group list size more than $size")
    public void targetGroupListMoreThan(String size) {
        EntityList<TargetGroup> groups = context.get("targetGroupEntityList", EntityList.class);

        Assert.assertTrue(groups.size() > Integer.valueOf(size));
    }

    @When("I send search targetGroups by $criteria and value $value")
    public void searchTargetGroupByCriteria(String criteria, String value) {
        log.info("Start search targetGroups by criteria: " + criteria + ", value: " + value);
        TargetGroup targetGroup = Entities.getTargetGroups().getLatest();

        if (criteria.toLowerCase().equals("includedeleted")) {
            value = value.equals("random") ? String.valueOf(targetGroup.isDeleted()) : value;
        } else if (criteria.toLowerCase().equals("updatedafter")) {
            value = String.valueOf(DateHelper.yesterday().getTime());
        } else if (criteria.toLowerCase().equals("empty")) {
            log.debug("Search without filter..");
        } else {
            throw new AssertionError("Unknown filter type");
        }

        TargetGroupFilter searchFilter = new TargetGroupFilter().filterBy(criteria, value);
        OperationResult<EntityList<TargetGroup>> operationResult = service.searchG4Compatibility(searchFilter);

        context.put("searchFilter", searchFilter);
        context.put("searchResult", operationResult.getEntity());
    }

    @Then("targetGroups search result are correct")
    public void targetGroupsSearchResultIsCorrert() {
        log.info("Checking if search targetGroups result is correct");
        TargetGroupFilter searchFilter = context.get("searchFilter", TargetGroupFilter.class);
        EntityList<TargetGroup> searchResult = context.get("searchResult", EntityList.class);

        if (searchResult.size() == 0) {
            log.warn("Search result can be incorrect. There are not records in it");
        } else {
            log.info("Search result size: " + searchResult.size());
        }
        for (TargetGroup targetGroup : searchResult.getEntities()) {
            Assert.assertTrue(String.format("Target:%s should not match to filter %s", targetGroup, Parser.entityToString(searchFilter)),
                    searchFilter.isAppliedToEntity(targetGroup));
        }
    }

    @Then("searched targetGroups $criteria search result list")
    public void searchedTargetGroupsInResultList(String criteria) {
        log.info("Checking if targetGroup entry " + criteria + " list");
        TargetGroup targetGroup = Entities.getTargetGroups().getLatest();
        EntityList<TargetGroup> list = context.get("searchResult", EntityList.class);

        Boolean contains = false;
        for (TargetGroup entity : list) {
            contains = equalsTargetGroups(entity, targetGroup);
            if (contains) {
                Entities.getTargetGroups().updateEntity(targetGroup, entity);
                break;
            }
        }

        if (criteria.toLowerCase().equals("in")) {
            Verify.shouldBe(isTrue.element(contains));
        } else if (criteria.toLowerCase().equals("not in")) {
            Verify.shouldNotBe(isTrue.element(contains));
        } else {
            throw new AssertionError("Incorrect argument passed to step");
        }
    }

}
