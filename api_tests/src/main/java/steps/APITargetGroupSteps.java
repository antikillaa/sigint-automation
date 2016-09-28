package steps;

import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import model.TargetGroup;
import org.apache.commons.lang.RandomStringUtils;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TargetGroupService;

import java.util.ArrayList;
import java.util.List;

import static conditions.Conditions.isTrue;

public class APITargetGroupSteps extends APISteps {
    
    private TargetGroupService service = new TargetGroupService();

    @When("I send create target group $with targets request")
    public void sendCreateRequest(String with){
        TargetGroup group = getRandomTargetGroup();
        if (with.toLowerCase().equals("with")) {
            List<String> targets = new ArrayList<>();
            targets.add(Entities.getTargets().random().getId());
            group.setTargets(targets);
        }
        int response = service.add(group);
        context.put("code", response);
        context.put("requestTargetGroup", group);
    }

    @Then("Created target group is correct")
    public void targetGroupCorrect() {
        TargetGroup contextTargetGroup = context.get("requestTargetGroup", TargetGroup.class);
        TargetGroup createdTargetGroup = Entities.getTargetGroups().getLatest();

        Verify.shouldBe(isTrue.element(equalsTargetGroups(createdTargetGroup, contextTargetGroup)));
    }

    private boolean equalsTargetGroups(TargetGroup checkedTargetGroup, TargetGroup etalonTargetGroup) {
        return Verify.isTrue(Conditions.equals(checkedTargetGroup.getDescription(), etalonTargetGroup.getDescription())) &&
                Verify.isTrue(Conditions.equals(checkedTargetGroup.getName(), etalonTargetGroup.getName())) &&
                Verify.isTrue(Conditions.equals(checkedTargetGroup.getTargets(), etalonTargetGroup.getTargets()));
    }

    @When("I send get target group details request")
    public void getTargetGroupRequest() {
        TargetGroup createdTargetGroup = Entities.getTargetGroups().getLatest();

        TargetGroup viewedTargetGroup = service.view(createdTargetGroup.getId());

        context.put("viewedTargetGroup", viewedTargetGroup);
    }

    @Then("Viewed target group is correct")
    public void viewedTargetGroupIsCorrect() {
        TargetGroup createdTarget = Entities.getTargetGroups().getLatest();

        TargetGroup viewedTargetGroup = context.get("viewedTargetGroup", TargetGroup.class);

        equalsTargetGroups(viewedTargetGroup, createdTarget);
    }

    @When("I send get list of target group request")
    public void getListOfTargetGroupsRequest() throws NullReturnException {
        List<TargetGroup> targetGroupList = service.view();

        context.put("targetGroupList", targetGroupList);
    }

    @Then("Created target group $criteria list")
    public void targetGroupsContainNewTargetGroup(String criteria) throws NullReturnException {
        TargetGroup targetGroup = Entities.getTargetGroups().getLatest();
        List<TargetGroup> list = context.get("targetGroupList", List.class);

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
        List<TargetGroup> list = context.get("targetGroupList", List.class);

        Boolean contains = false;
        for (TargetGroup entity : list) {
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

        int responseCode = service.remove(targetGroup);

        context.put("deletedTargetGroup", targetGroup);
        context.put("code", responseCode);
    }

    @Then("Target group deleted correctly")
    public void targetGroupDeletedCorrectly() {
        TargetGroup deletedTargetGroup = context.get("deletedTargetGroup", TargetGroup.class);

        TargetGroup resultTargetGroup = service.view(deletedTargetGroup.getId());

        Verify.shouldBe(isTrue.element(resultTargetGroup.getName().contains("DELETED at")));
    }

    @When("I send update target group request")
    public void updateTargetGroupRequest() {
        TargetGroup createdTargetGroup = Entities.getTargetGroups().getLatest();
        TargetGroup updatedTargetGroup = updateTargetGroup(createdTargetGroup);

        int responseCode = service.update(updatedTargetGroup);

        context.put("code", responseCode);
        context.put("updatedTargetGroup", updatedTargetGroup);
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
        List<TargetGroup> groups = context.get("targetGroupList", List.class);

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
        return (TargetGroup)objectInitializer.generateObject(TargetGroup.class);
    }
    
    private TargetGroup updateTargetGroup(TargetGroup targetGroup) {
        targetGroup.setName(RandomStringUtils.randomAlphabetic(10));
        targetGroup.setDescription(RandomStringUtils.randomAlphabetic(20));
        return targetGroup;
    }
}
