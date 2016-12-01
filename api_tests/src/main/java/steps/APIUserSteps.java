package steps;

import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import http.JsonConverter;
import http.OperationResult;
import http.OperationsResults;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

public class APIUserSteps extends APISteps {

    private Logger log = Logger.getLogger(APIUserGroupSteps.class);
    private UserService service = new UserService();

    @When("I send create a new user with group request")
    public void createNewUserRequest() {
        List<String> userGroupIds = new ArrayList<String>();
        userGroupIds.add(Entities.getGroups().getLatest().getId());
        User user = getRandomUser();
        user.setUserGroupIds(userGroupIds);
        OperationResult<User> operationResult = service.add(user);
        OperationsResults.setResult(operationResult);
        context.put("requestUser", operationResult.getResult());
    }

    @Then("Created user is correct")
    public void createdUserIsCorrect() throws NullReturnException {
        User createdUser = Entities.getUsers().getLatest();
        User requestUser = context.get("requestUser", User.class);

        log.debug("requested: " + JsonConverter.toJsonString(requestUser));
        log.debug("created: " + JsonConverter.toJsonString(createdUser));

        Verify.shouldBe(Conditions.equals(createdUser.getName(), requestUser.getName()));
    }
    
    static User getRandomUser() {
        return objectInitializer.randomEntity(User.class);
    }



}
