package steps;

import conditions.Conditions;
import conditions.Verify;
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

public class APITargetSteps extends APISteps {
    private Logger log = Logger.getLogger(APITargetGroupSteps.class);
    private AppContext context = AppContext.getContext();
    private TargetService service = new TargetService();

    @When("I send create target $with targets group request")
    public void sendCreateRequest(String with){
        Target target = new Target().generate();
        if (with.toLowerCase().equals("with")) {
            List<TargetGroup> groups = new ArrayList<TargetGroup>();
            groups.add(context.entities().getTargetGroups().random());
            target.setGroups(groups);
        }

        int response = service.add(target);
        context.put("code", response);
        context.put("requestTarget", target);
    }

    @Then("Created target is correct")
    public void createdTargetCorrect() {
        Target contextTarget = context.get("requestTarget", Target.class);
        Target createdTarget = context.entities().getTargets().getLatest();
        Verify.shouldBe(Conditions.equals.elements(contextTarget.getDescription(), createdTarget.getDescription()));
        Verify.shouldBe(Conditions.equals.elements(contextTarget.getName(), createdTarget.getName()));
        Verify.shouldBe(Conditions.equals.elements(contextTarget.getKeywords(), createdTarget.getKeywords()));
        Verify.shouldBe(Conditions.equals.elements(contextTarget.getPhones(), createdTarget.getPhones()));
        Verify.shouldBe(Conditions.equals.elements(contextTarget.getLanguages(), createdTarget.getLanguages()));
        Verify.shouldBe(Conditions.equals.elements(contextTarget.getGroups(), createdTarget.getGroups()));
        Verify.shouldBe(Conditions.equals.elements(contextTarget.getType(), createdTarget.getType()));

        Assert.assertTrue(createdTarget.getId() != null);
    }
}
