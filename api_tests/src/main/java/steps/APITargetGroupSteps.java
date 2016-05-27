package steps;

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

public class APITargetGroupSteps {

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
        int response = service.addNew(group);
        context.put("code", response);
        context.put("requestTargetGroup", group);

    }

    @Then("Created target group is correct")
    public void targetGroupCorrect() {
        TargetGroup contextTargetGroup = context.get("requestTargetGroup", TargetGroup.class);
        TargetGroup createdTargetGroup = context.entities().getTargetGroups().getLatest();
        Assert.assertEquals(contextTargetGroup.getDescription(), createdTargetGroup.getDescription());
        Assert.assertEquals(contextTargetGroup.getName(), createdTargetGroup.getName());
        Assert.assertEquals(contextTargetGroup.getTargets(), createdTargetGroup.getTargets());
        Assert.assertTrue(createdTargetGroup.getId()!=null);
    }

}
