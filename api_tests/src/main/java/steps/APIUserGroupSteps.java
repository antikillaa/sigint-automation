package steps;

import conditions.Verify;
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

    @When("I send create a new group without any roles")
    public void createGroupRequest() {
        Group group = new Group().generate();

        int responseCode = service.add(group);

        context.put("code", responseCode);
        context.put("requestGroup", group);
    }

    @Then("Created group is correct")
    public void createdGroupIsCorrect() {
        Group createdGroup = context.entities().getGroups().getLatest();
        Group requestGroup = context.get("requestGroup", Group.class);

        Verify.shouldBe(equals.elements(createdGroup, requestGroup));
    }

    @When("I send add a created role to group request")
    public void addRoleToGroupRequest() {
        List<String> roles = new ArrayList<String>();
        roles.add(context.entities().getRoles().getLatest().getName());

        Group group = context.entities().getGroups().getLatest().setRoles(roles);
        int responseCode = service.update(group);

        context.put("code", responseCode);
        context.put("updatedGroup", group);
    }

    @Then("Updated group is correct")
    public void updatedGroupIsCorrect() {
        Group createdGroup = context.entities().getGroups().getLatest();
        Group updatedGroup = context.get("updatedGroup", Group.class);

        Verify.shouldBe(equals.elements(updatedGroup, createdGroup));
    }


}
