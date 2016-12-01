package steps;

import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import http.OperationResult;
import http.OperationsResults;
import model.Group;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.GroupService;

import java.util.ArrayList;
import java.util.List;

public class APIUserGroupSteps extends APISteps {

    private Logger log = Logger.getLogger(APIUserGroupSteps.class);
    private GroupService service = new GroupService();

    @When("I send create a new group without any roles")
    public void createGroupRequest() {
        Group group = createRandomUserGroup();

        OperationResult<Group> operationResult = service.add(group);
        OperationsResults.setResult(operationResult);
        context.put("requestGroup", operationResult.getResult());
    }

    @Then("Created group is correct")
    public void createdGroupIsCorrect() {
        Group createdGroup = Entities.getGroups().getLatest();
        Group requestGroup = context.get("requestGroup", Group.class);

        Verify.shouldBe(Conditions.equals(createdGroup, requestGroup));
    }

    @When("I send add a created role to group request")
    public void addRoleToGroupRequest() {
        List<String> roles = new ArrayList<String>();
        roles.add(Entities.getRoles().getLatest().getName());

        Group group = Entities.getGroups().getLatest().setRoles(roles);
        OperationResult<Group> operationResult = service.update(group);
        OperationsResults.setResult(operationResult);
        context.put("updatedGroup", operationResult.getResult());
    }

    @Then("Updated group is correct")
    public void updatedGroupIsCorrect() {
        Group createdGroup = Entities.getGroups().getLatest();
        Group updatedGroup = context.get("updatedGroup", Group.class);

        Verify.shouldBe(Conditions.equals(updatedGroup, createdGroup));
    }
    
    static Group createRandomUserGroup() {
        return objectInitializer.randomEntity(Group.class);
    }


}
