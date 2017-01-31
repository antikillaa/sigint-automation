package steps;

import abs.EntityList;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import http.OperationResult;
import http.OperationsResults;
import model.Group;
import model.RequestResult;
import model.Role;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.GroupService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class APIUserGroupSteps extends APISteps {

    private Logger log = Logger.getLogger(APIUserGroupSteps.class);
    private GroupService service = new GroupService();

    @When("I send create a new group without any roles")
    public void createGroupRequest() {
        Group group = createRandomUserGroup();

        OperationResult<Group> operationResult = service.add(group);
        OperationsResults.setResult(operationResult);
        context.put("group", operationResult.getResult());
    }

    @Then("Created group is correct")
    public void createdGroupIsCorrect() {
        Group createdGroup = Entities.getGroups().getLatest();
        Group requestGroup = context.get("group", Group.class);

        Verify.shouldBe(Conditions.equals(createdGroup, requestGroup));
    }

    @When("I send add a created role to group request")
    public void addRoleToGroupRequest() {
        List<String> roles = new ArrayList<>();
        roles.add(Entities.getRoles().getLatest().getName());

        Group group = Entities.getGroups().getLatest().setRoles(roles);
        OperationResult<Group> operationResult = service.update(group);
        OperationsResults.setResult(operationResult);
        context.put("group", operationResult.getResult());
    }

    @Then("Updated group is correct")
    public void updatedGroupIsCorrect() {
        Group createdGroup = Entities.getGroups().getLatest();
        Group updatedGroup = context.get("group", Group.class);

        Verify.shouldBe(Conditions.equals(updatedGroup, createdGroup));
    }

    static Group createRandomUserGroup() {
        return objectInitializer.randomEntity(Group.class);
    }

    @When("I send delete group request")
    public void deleteGroup() {
        Group group = context.get("group", Group.class);
        OperationResult<RequestResult> operationResult = service.remove(group);

        context.put("requestResult", operationResult.getResult());
    }

    @When("I send get list of users group")
    public void getListOfGroups() {
        OperationResult<EntityList<Group>> operationResult = service.list(null);
        context.put("groupEntityList", operationResult.getResult());
    }

    @Then("Users group list size more than 0")
    public void groupListIsNotEmpty() {
        EntityList<Group> groupEntityList = context.get("groupEntityList", EntityList.class);

        Assert.assertFalse(groupEntityList.getEntities().isEmpty());
    }

    @Then("delete all groups without roles and users")
    public void deleteAllOldGroups() {
        EntityList<Group> groupEntityList = context.get("groupEntityList", EntityList.class);
        for (Group group : groupEntityList.getEntities()) {
            if (group.getRoles().isEmpty() && group.getUsers() == null) {
                OperationResult<RequestResult> operationResult = service.remove(group);
                Assert.assertEquals( "success", operationResult.getResult().getMessage());
            }
        }
    }

    @Then("delete from groups phantom roles")
    public void deleteFromGroupsPhantomRoles(){

        EntityList<Group> groupEntityList = context.get("groupEntityList", EntityList.class);
        EntityList<Role> roleEntityList = context.get("roles", EntityList.class);

        for (Group group : groupEntityList.getEntities()) {

            List<String> groupRoles = group.getRoles();
            boolean updated = false;

            Iterator<String> iterator = groupRoles.iterator();
            while (iterator.hasNext()) {
                boolean exist = false;
                String groupRoleName = iterator.next();

                for (Role role : roleEntityList.getEntities()) {
                    // if role exist
                    if (Objects.equals(groupRoleName, role.getName())) {
                        exist = true;
                        break;
                    }
                }

                if (!exist) {
                    iterator.remove();
                    updated = true;
                }
            }
            if (updated) {
                OperationResult<Group> operationResult = service.update(group);

                Assert.assertTrue(operationResult.isSuccess());
            }
        }
    }
}
