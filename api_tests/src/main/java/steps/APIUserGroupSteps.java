package steps;

import conditions.Verify;
import errors.NullReturnException;
import model.Group;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.GroupService;

import java.util.ArrayList;
import java.util.List;

import static conditions.Conditions.equals;

public class APIUserGroupSteps extends APISteps {

    private Logger log = Logger.getLogger(APIUserGroupSteps.class);
    private GroupService service = new GroupService();

    @When("I send create a new group with created role request")
    public void createGroupRequest() {
        List<String> roles = new ArrayList<String>();
        roles.add(context.entities().getRoles().getLatest().getName());
        Group group = new Group().generate().setRoles(roles);

        int responseCode = service.add(group);

        context.put("code", responseCode);
        context.put("requestGroup", group);
    }

    @Then("Created group is correct")
    public void createdGroupIsCorrect() throws NullReturnException {
        Group createdGroup = context.entities().getGroups().getLatest();
        Group requestGroup = context.get("requestGroup", Group.class);

        Verify.shouldBe(equals.elements(createdGroup, requestGroup));
    }
}
