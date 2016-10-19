package steps;

import abs.EntityList;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import file_generator.FileGenerator;
import json.JsonCoverter;
import model.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TargetService;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;

import static conditions.Conditions.isTrue;

public class APITargetSteps extends APISteps {

    private Logger log = Logger.getLogger(APITargetGroupSteps.class);
    private TargetService service = new TargetService();

    @When("I send create target $with targets group request")
    public void sendCreateRequest(String with) throws NullReturnException {
        Target target = getRandomTarget();
        if (with.toLowerCase().equals("with")) {
            ArrayList<TargetGroup> groups = new ArrayList<TargetGroup>();
            groups.add(Entities.getTargetGroups().random());
            target.setGroups(groups);
        }

        int response = service.add(target);

        context.put("code", response);
        context.put("requestTarget", target);
    }

    @Then("Created target is correct")
    public void createdTargetCorrect() {
        Target contextTarget = context.get("requestTarget", Target.class);
        Target createdTarget = Entities.getTargets().getLatest();

        Assert.assertTrue(createdTarget.getId() != null);
        isEqualsTargets(createdTarget, contextTarget);
    }

    @When("I send get target details request")
    public void getTargetDetails() {
        Target createdTarget = Entities.getTargets().getLatest();

        Target viewedTarget = service.view(createdTarget.getId());

        context.put("viewedTarget", viewedTarget);
    }

    @Then("Viewed target is correct")
    public void viewedTargetIsCorrect() {
        Target createdTarget = Entities.getTargets().getLatest();

        Target viewedTarget = context.get("viewedTarget", Target.class);

        Verify.shouldBe(isTrue.element(isEqualsTargets(viewedTarget, createdTarget)));
    }

    private boolean isEqualsTargets(Target checkedTarget, Target etalonTarget) {
        return Verify.isTrue(Conditions.equals(checkedTarget.getDescription(), etalonTarget.getDescription())) &&
        Verify.isTrue(Conditions.equals(checkedTarget.getName(), etalonTarget.getName())) &&
        Verify.isTrue(Conditions.equals(checkedTarget.getKeywords(), etalonTarget.getKeywords())) &&
        Verify.isTrue(Conditions.equals(checkedTarget.getPhones(), etalonTarget.getPhones())) &&
        Verify.isTrue(Conditions.equals(checkedTarget.getLanguages(), etalonTarget.getLanguages())) &&
        Verify.isTrue(Conditions.equals(checkedTarget.getGroups(), etalonTarget.getGroups())) &&
        Verify.isTrue(Conditions.equals(checkedTarget.getType(), etalonTarget.getType()));
    }

    private boolean isEqualsUploadedTargets(Target checkedTarget, Target etalonTarget) {
        return Verify.isTrue(Conditions.equals(checkedTarget.getName(), etalonTarget.getName())) &&
                Verify.isTrue(Conditions.equals(checkedTarget.getKeywords(), etalonTarget.getKeywords())) &&
                Verify.isTrue(Conditions.equals(checkedTarget.getPhones(), etalonTarget.getPhones())) &&
                Verify.isTrue(Conditions.equals(checkedTarget.getType(), etalonTarget.getType()));
    }

    @When("I send update target request")
    public void updateTargetRequest() throws NullReturnException {
        Target createdTarget = Entities.getTargets().getLatest();
        Target updatedTarget = createdTarget;
        updatedTarget.setName(RandomStringUtils.randomAlphabetic(10));
        updatedTarget.setDescription(RandomStringUtils.randomAlphabetic(20));
        log.info("Updated target: " + JsonCoverter.toJsonString(updatedTarget));
        int responseCode = service.update(updatedTarget);
        context.put("code", responseCode);
        context.put("updatedTarget", updatedTarget);
    }

    @Then("Target updated correctly")
    public void targetUpdatedCorrectly() throws NullReturnException {
        Target updatedTarget = context.get("updatedTarget", Target.class);

        Target resultTarget = service.view(updatedTarget.getId());

        Verify.shouldBe(isTrue.element(isEqualsTargets(updatedTarget, resultTarget)));
    }

    @When("I send delete target request")
    public void deleteTargetRequest() {
        Target createdTarget = Entities.getTargets().getLatest();

        int responseCode = service.remove(createdTarget);

        context.put("deletedTarget", createdTarget);
        context.put("code", responseCode);
    }

    @Then("Target deleted correctly")
    public void targetDeletedCorrectly() throws NullReturnException {
        Target deletedTarget = context.get("deletedTarget", Target.class);

        Target resultTarget = service.view(deletedTarget.getId());

        Verify.shouldBe(isTrue.element(resultTarget.getName().contains("DELETED at")));
    }

    @When("I send upload targets request with XLS file containing $count targets without specified id")
    public void targetGeneratedXls(String count){
        List<Target> targets = getRandomTargets(Integer.valueOf(count));

        int responseCode = service.upload(targets);

        context.put("code", responseCode);
        context.put("uploadedTargets", targets);
    }

    @When("I send upload targets request with XLS file containing $count targets with existing group request")
    public void uploadTargetsWithExistingGroup(String count){
        List<TargetGroup> targetGroups = context.get("targetGroupList", List.class);
        List<Target> targets = getRandomTargets(Integer.valueOf(count));

        for (Target target : targets ) {
            int index = RandomUtils.nextInt(targetGroups.size());
            TargetGroup group = targetGroups.get(index);
            target.addGroup(group);
            Entities.getTargetGroups().addOrUpdateEntity(group);
        }

        int responseCode = service.upload(targets);

        context.put("code", responseCode);
        context.put("uploadedTargets", targets);
    }

    @When("I send search targets by $criteria and value $value")
    public void targetSearchByCriteriaAndValue(String criteria, String value) {
        log.info("Start search targets by criteria: " + criteria + ", value: " + value);
        Target target = Entities.getTargets().getLatest();

        if (criteria.toLowerCase().equals("type")) {
            value = value.equals("random") ? target.getType().toString() : value;
        } else if (criteria.toLowerCase().equals("name")) {
            value = value.equals("random") ? target.getName() : value;
        } else if (criteria.toLowerCase().equals("keywords")) {
            value = value.equals("random") ? Parser.setToString(target.getKeywords()) : value;
        } else if (criteria.toLowerCase().equals("description")) {
            value = value.equals("random") ? target.getDescription() : value;
        } else if (criteria.toLowerCase().equals("deleted")) {
            value = value.equals("random") ? String.valueOf(target.isDeleted()) : value;
        } else if (criteria.toLowerCase().equals("languages")) {
            value = value.equals("random") ? Parser.setToString(target.getLanguages()) : value;
        } else if (criteria.toLowerCase().equals("phones")) {
            value = value.equals("random") ? Parser.setToString(target.getPhones()) : value;
        } else if (criteria.toLowerCase().equals("updatedafter")) {
            value = value.equals("random") ? String.valueOf(target.getCreatedAt().getTime()-1000) : value;
        } else if (criteria.toLowerCase().equals("empty")) {
            log.debug("Search without filter..");
        } else {
            throw new AssertionError("Unknown filter type");
        }

        TargetFilter searchFilter = new TargetFilter().filterBy(criteria, value);
        EntityList<Target> targets = service.list(searchFilter);

        context.put("searchFilter", searchFilter);
        context.put("searchResult", targets);
    }

    @Then("targets search result are correct")
    public void targetSearchResultAreCorrect(){
        log.info("Checking if search targets result is correct");
        TargetFilter searchFilter = context.get("searchFilter", TargetFilter.class);
        EntityList<Target> searchResult = context.get("searchResult", EntityList.class);

        if (searchResult.size() == 0) {
            log.warn("Search result can be incorrect. There are not records in it");
        } else {
            log.info("Search result size: " + searchResult.size());
        }
        for (Target target : searchResult) {
            Assert.assertTrue(String.format("Target:%s should not match to filter %s", target, Parser.entityToString(searchFilter)),
                    searchFilter.isAppliedToEntity(target));
        }
    }

    @Then("searched target entry $criteria list")
    public void searchedTargetInList(String criteria) {
        log.info("Checking if Target entry " + criteria + " list");
        Target target = Entities.getTargets().getLatest();
        EntityList<Target> list = context.get("searchResult", EntityList.class);

        Boolean contains = list.contains(target);
        if (criteria.toLowerCase().equals("in")) {
            Verify.shouldBe(isTrue.element(contains));
        } else if (criteria.toLowerCase().equals("not in")) {
            Verify.shouldNotBe(isTrue.element(contains));
        } else {
            throw new AssertionError("Incorrect argument passed to step");
        }
    }

    @Then("uploaded target $criteria list")
    public void uploadedTargetInList(String criteria) {
        log.info("Checking if Target entry " + criteria + " list");
        Target target = Entities.getTargets().getLatest();
        EntityList<Target> list = context.get("searchResult", EntityList.class);

        Boolean contains = false;
        for (Target entity : list) {
            contains = isEqualsUploadedTargets(target, entity);
            if (contains) {
                Entities.getTargets().updateEntity(target, entity);
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

    @Then("target has auto-generated ID")
    public void targetHasID() {
        log.info("Checking if Target has ID");
        EntityList<Target> list = context.get("searchResult", EntityList.class);
        for (Target target : list) {
            Assert.assertNotNull(target.getId());
        }
    }

    @When("I send upload updated target request")
    public void uploadUpdatedTarget() {
        List<Target> targets = context.get("uploadedTargets", List.class);
        for (Target target : targets) {
            target.setName(RandomStringUtils.randomAlphabetic(10));
            target.setDescription(RandomStringUtils.randomAlphabetic(20));
        }
        service.upload(targets);
        context.put("uploadedTargets", targets);
    }

    @Given("generate XLS with $count target")
    public void generateTargets(String count) {
        List<Target> targets = getRandomTargets(Integer.valueOf(count));
        G4File file = new FileGenerator(Target.class).write(targets);

        context.put("targetsXLS", file);
    }

    @When("I send get groups list of new target request")
    public void getTargetGroupsOfNewTarget(){
        Target target = Entities.getTargets().getLatest();
        List<TargetGroup> targetGroups = service.getTargetGroups(target.getId());

        context.put("targetGroupList", targetGroups);
    }

    @When("I send upload new $count targets with new group request")
    public void uploadTargetWithNewGroup(String count) {
        List<Target> targets = getRandomTargets(Integer.valueOf(count));

        for (Target target : targets) {
            TargetGroup targetGroup = (TargetGroup)objectInitializer.randomEntity(TargetGroup.class);
            target.addGroup(targetGroup);
            Entities.getTargetGroups().addOrUpdateEntity(targetGroup);
        }

        int responseCode = service.upload(targets);

        context.put("code", responseCode);
        context.put("uploadedTargets", targets);
    }

    @Then("Upload result of $count targets is successful")
    public void checkUploadTargetsResults(String count) {
        log.info("Checking targets upload result");
        UploadResult result = context.get("uploadResult", UploadResult.class);

        int numEntries = Integer.valueOf(count);
        if ((result.getRowsAdded() + result.getRowsUpdated()) == numEntries || result.getRowsFailed() == 0) {
            List<Target> targets = context.get("uploadedTargets", ArrayList.class);
            for (Target target : targets) {
                Entities.getTargets().addOrUpdateEntity(target);
            }
        } else {
            log.error("Entry upload result is not correct!");
            log.error("Errors:" + result.getErrors());
            throw new AssertionError("Entry upload result is not correct! Errors: " + result.getErrors());
        }
    }

    @Given("$count targets with phones generated and added")
    public void targetWithPhonesExist(String count){
        int targetsCount = Integer.valueOf(count);

        List<Target> targets = getRandomTargets(targetsCount);
        int statusCode = service.upload(targets);
        Verify.shouldBe(Conditions.equals(statusCode, 200));

        GenerationMatrix generationMatrix = new GenerationMatrix(targets);
        context.put("generationMatrix", generationMatrix);
    }
    
    
    @Given("I create test entity")
    public void createTestEntitity() {
        XVoiceMetadata metadata = getRandomMetaData();
    }
    
    private List<Target> getRandomTargets(int count) {
        return objectInitializer.randomEntities(Target.class, count);
    }
    
    
    static Target getRandomTarget() {
        return objectInitializer.randomEntity(Target.class);
    }
    
    
    private XVoiceMetadata getRandomMetaData() {
        return objectInitializer.randomEntity(XVoiceMetadata.class);
    }
}
