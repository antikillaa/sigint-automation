package steps;

import conditions.Verify;
import errors.NullReturnException;
import model.AppContext;
import model.Target;
import model.TargetGroup;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TargetGroupService;

import java.util.ArrayList;
import java.util.List;

import static conditions.Conditions.equals;
import static conditions.Conditions.isTrue;

public class APITargetGroupSteps extends APISteps {

    private Logger log = Logger.getLogger(APITargetGroupSteps.class);
    private AppContext context = AppContext.getContext();
    private TargetGroupService service = new TargetGroupService();

    @When("I send create target group $with targets request")
    public void sendCreateRequest(String with){
        TargetGroup group = new TargetGroup().generate();
        if (with.toLowerCase().equals("with")) {
            List<Target> targets = new ArrayList<Target>();
            targets.add(context.entities().getTargets().random());
            group.setTargets(targets);
        }
        int response = service.add(group);
        context.put("code", response);
        context.put("requestTargetGroup", group);
    }

    @Then("Created target group is correct")
    public void targetGroupCorrect() {
        TargetGroup contextTargetGroup = context.get("requestTargetGroup", TargetGroup.class);
        TargetGroup createdTargetGroup = context.entities().getTargetGroups().getLatest();

        Verify.shouldBe(isTrue.element(equalsTargetGroups(createdTargetGroup, contextTargetGroup)));
    }

    private boolean equalsTargetGroups(TargetGroup checkedTargetGroup, TargetGroup etalonTargetGroup) {
        Assert.assertTrue(checkedTargetGroup.getId() != null);
        return Verify.isTrue(equals.elements(checkedTargetGroup.getDescription(), etalonTargetGroup.getDescription())) &&
                Verify.isTrue(equals.elements(checkedTargetGroup.getName(), etalonTargetGroup.getName())) &&
                Verify.isTrue(equals.elements(checkedTargetGroup.getTargets(), etalonTargetGroup.getTargets()));
    }

    @When("I send get target group details request")
    public void getTargetGroupRequest() {
        TargetGroup createdTargetGroup = context.entities().getTargetGroups().getLatest();

        TargetGroup viewedTargetGroup = service.view(createdTargetGroup.getId());

        context.put("viewedTargetGroup", viewedTargetGroup);
    }

    @Then("Viewed target group is correct")
    public void viewedTargetGroupIsCorrect() {
        TargetGroup createdTarget = context.entities().getTargetGroups().getLatest();

        TargetGroup viewedTargetGroup = context.get("viewedTargetGroup", TargetGroup.class);

        equalsTargetGroups(viewedTargetGroup, createdTarget);
    }

    @When("I send get list of target group request")
    public void getListOfTargetGroupsRequest() throws NullReturnException {
        List<TargetGroup> listTargetGroups = service.view();

        context.put("listTargetGroup", listTargetGroups);
    }

    @Then("Created target group $criteria list")
    public void targetGroupsContainNewTArgetGroup(String criteria) throws NullReturnException {
        TargetGroup targetGroup = context.entities().getTargetGroups().getLatest();
        List<TargetGroup> list = context.get("listTargetGroup", List.class);

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

}
