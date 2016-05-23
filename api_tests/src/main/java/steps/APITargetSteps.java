package steps;

import conditions.EqualCondition;
import model.AppContext;
import model.Target;
import model.TargetGroup;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.TargetService;
import verifier.Verify;

import java.util.ArrayList;
import java.util.List;

public class APITargetSteps {
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

        int response = service.addNew(target);
        context.putToRunContext("code", response);
        context.putToRunContext("requestTarget", target);

    }

    @Then("Created target is correct")
    public void createdTargetCorrect() {
        Target contextTarget = context.getFromRunContext("requestTarget", Target.class);
        Target createdTarget = context.entities().getTargets().getLatest();
        Assert.assertEquals(contextTarget.getDescription(), createdTarget.getDescription());
        Assert.assertEquals(contextTarget.getName(), createdTarget.getName());
        Assert.assertEquals(contextTarget.getKeywords(), createdTarget.getKeywords());
        Assert.assertEquals(contextTarget.getPhones(), createdTarget.getPhones());
        //Assert.assertEquals(contextTarget.getLanguages(), createdTarget.getLanguages());
        Verify.shouldBe(new EqualCondition(contextTarget.getLanguages(), createdTarget.getLanguages()));
        Assert.assertEquals(contextTarget.getGroups(), createdTarget.getGroups());
        Assert.assertEquals(contextTarget.getType(), createdTarget.getType());
        Assert.assertTrue(createdTarget.getId() != null);

    }
}
