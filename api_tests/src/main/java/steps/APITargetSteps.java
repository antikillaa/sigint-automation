package steps;

import conditions.Verify;
import errors.NullReturnException;
import json.JsonCoverter;
import model.AppContext;
import model.Target;
import model.TargetGroup;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TargetService;

import java.util.ArrayList;
import java.util.List;

import static conditions.Conditions.equals;
import static conditions.Conditions.isTrue;

public class APITargetSteps extends APISteps {
    private Logger log = Logger.getLogger(APITargetGroupSteps.class);
    private AppContext context = AppContext.getContext();
    private TargetService service = new TargetService();

    @When("I send create target $with targets group request")
    public void sendCreateRequest(String with) throws NullReturnException {
        Target target = new Target().generate();
        if (with.toLowerCase().equals("with")) {
            List<TargetGroup> groups = new ArrayList<TargetGroup>();
            groups.add(context.entities().getTargetGroups().random());
            target.setGroups(groups);
        }

        log.info("Target: " + JsonCoverter.toJsonString(target));
        int response = service.add(target);

        context.put("code", response);
        context.put("requestTarget", target);
    }

    @Then("Created target is correct")
    public void createdTargetCorrect() {
        Target contextTarget = context.get("requestTarget", Target.class);
        Target createdTarget = context.entities().getTargets().getLatest();

        Assert.assertTrue(createdTarget.getId() != null);
        equalsTargets(createdTarget, contextTarget);
    }

    @When("I send get target details request")
    public void getTargetDetails() {
        Target createdTarget = context.entities().getTargets().getLatest();

        Target viewedTarget = service.view(createdTarget.getId());

        context.put("viewedTarget", viewedTarget);
    }

    @Then("Viewed target is correct")
    public void viewedTargetIsCorrect() throws NullReturnException {
        Target createdTarget = context.entities().getTargets().getLatest();

        Target viewedTarget = context.get("viewedTarget", Target.class);
        log.info("Target: " + JsonCoverter.toJsonString(viewedTarget));

        equalsTargets(viewedTarget, createdTarget);
    }

    private void equalsTargets(Target checkedTarget, Target etalonTarget) {
        Verify.shouldBe(equals.elements(checkedTarget.getDescription(), etalonTarget.getDescription()));
        Verify.shouldBe(equals.elements(checkedTarget.getName(), etalonTarget.getName()));
        Verify.shouldBe(equals.elements(checkedTarget.getKeywords(), etalonTarget.getKeywords()));
        Verify.shouldBe(equals.elements(checkedTarget.getPhones(), etalonTarget.getPhones()));
        Verify.shouldBe(equals.elements(checkedTarget.getLanguages(), etalonTarget.getLanguages()));
        Verify.shouldBe(equals.elements(checkedTarget.getGroups(), etalonTarget.getGroups()));
        Verify.shouldBe(equals.elements(checkedTarget.getType(), etalonTarget.getType()));
    }

    @When("I send update target request")
    public void updateTargetRequest() throws NullReturnException {
        Target createdTarget = context.entities().getTargets().getLatest();
        Target updatedTarget = createdTarget.generate();

        log.info("Updated target: " + JsonCoverter.toJsonString(updatedTarget));
        int responseCode = service.update(updatedTarget);

        context.put("code", responseCode);
        context.put("updatedTarget", updatedTarget);
    }

    @Then("Target updated correctly")
    public void targetUpdatedCorrectly() throws NullReturnException {
        Target updatedTarget = context.get("updatedTarget", Target.class);

        Target resultTarget = service.view(updatedTarget.getId());

        equalsTargets(updatedTarget, resultTarget);
    }

    @When("I send delete target request")
    public void deleteTargetRequest() {
        Target createdTarget = context.entities().getTargets().getLatest();

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
}
