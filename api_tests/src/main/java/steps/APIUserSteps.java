package steps;

import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import json.JsonConverter;
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

        int responseCode = service.add(user);

        context.put("code", responseCode);
        context.put("requestUser", user);
    }

    @Then("Created user is correct")
    public void createdUserIsCorrect() throws NullReturnException {
        User createdUser = Entities.getUsers().getLatest();
        User requestUser = context.get("requestUser", User.class);

        log.info("requested: " + JsonConverter.toJsonString(requestUser));
        log.info("created: " + JsonConverter.toJsonString(createdUser));

        Verify.shouldBe(Conditions.equals(createdUser, requestUser));
    }
    
    static User getRandomUser() {
        return objectInitializer.randomEntity(User.class);
    }



}
