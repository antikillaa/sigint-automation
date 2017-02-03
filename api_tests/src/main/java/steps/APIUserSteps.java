package steps;

import abs.EntityList;
import app_context.entities.Entities;
import errors.NullReturnException;
import http.JsonConverter;
import http.OperationResult;
import http.OperationsResults;
import model.RequestResult;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class APIUserSteps extends APISteps {

    private Logger log = Logger.getLogger(APIUserGroupSteps.class);
    private UserService service = new UserService();

    @When("I send create a new user with group request")
    public void createNewUserRequest() {
        List<String> userGroupIds = new ArrayList<>();
        userGroupIds.add(Entities.getGroups().getLatest().getId());
        User user = getRandomUser();
        user.setUserGroupIds(userGroupIds);
        OperationResult<User> operationResult = service.add(user);
        OperationsResults.setResult(operationResult);
        context.put("user", operationResult.getResult());
    }

    @Then("Created user is correct")
    public void createdUserIsCorrect() throws NullReturnException {
        User createdUser = Entities.getUsers().getLatest();
        User requestUser = context.get("user", User.class);

        log.debug("requested: " + JsonConverter.toJsonString(requestUser));
        log.debug("created: " + JsonConverter.toJsonString(createdUser));

        Assert.assertEquals(createdUser.getName().toLowerCase(), requestUser.getName().toLowerCase());
        Assert.assertEquals(createdUser.getDisplayName(), requestUser.getDisplayName());
        Assert.assertEquals(createdUser.getRoles(), requestUser.getRoles());
        Assert.assertEquals(createdUser.getLanguages(), requestUser.getLanguages());
        Assert.assertEquals(createdUser.getPhone(), requestUser.getPhone());
        Assert.assertEquals(createdUser.getStaffId(), requestUser.getStaffId());
        Assert.assertEquals(createdUser.getUserGroupIds(), requestUser.getUserGroupIds());
        Assert.assertEquals(createdUser.getExpandedPermissions(), requestUser.getExpandedPermissions());
        Assert.assertEquals(createdUser.getExpandedRoles(), requestUser.getExpandedRoles());
    }
    
    static User getRandomUser() {
        return objectInitializer.randomEntity(User.class);
    }

    @When("I send delete user request")
    public void deleteUser() {
        User createdUser = Entities.getUsers().getLatest();
        OperationResult<RequestResult> operationResult = service.remove(createdUser);

        OperationsResults.setResult(operationResult);
        context.put("requestResult", operationResult.getResult());
    }

    @When("I send get list of users")
    public void getListOfUsers() {
        OperationResult<EntityList<User>> operationResult = service.list();
        context.put("userEntityList", operationResult.getResult());
    }

    @Then("Users list size more than $count")
    public void userListMoreThan(String count) {
        EntityList<User> userEntityList = context.get("userEntityList", EntityList.class);

        Assert.assertFalse("Users list is empty", userEntityList.getEntities().isEmpty());
    }

    @Then("delete all users without roles and groups")
    public void cleanupUsers(){
        EntityList<User> userEntityList = context.get("userEntityList", EntityList.class);

        for (User user : userEntityList.getEntities()) {
            if (user.getRoles().isEmpty() && user.getUserGroupIds().isEmpty()) {
                OperationResult<RequestResult> operationResult = service.remove(user);
                Assert.assertEquals("success", operationResult.getResult().getMessage());
            }
        }
    }

    @When("I send update user request")
    public void updateUser() {
        User user = Entities.getUsers().getLatest();
        User updatedUser = getRandomUser();
        updatedUser.setId(user.getId());
        //updatedUser.setUserGroupIds(user.getUserGroupIds());

        OperationResult<User> result = service.update(updatedUser);

        context.put("user", result.getResult());
    }

}
