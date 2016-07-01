package steps;

import conditions.Verify;
import errors.NullReturnException;
import json.JsonCoverter;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

import static conditions.Conditions.equals;

public class APIUserSteps extends APISteps {

    private Logger log = Logger.getLogger(APIUserGroupSteps.class);
    private UserService service = new UserService();

    @When("I send create a new user with group request")
    public void createNewUserRequest() {
        List<String> userGroupIds = new ArrayList<String>();
        userGroupIds.add(context.entities().getGroups().getLatest().getId());
        User user = new User().generate().setUserGroupIds(userGroupIds);

        int responseCode = service.add(user);

        context.put("code", responseCode);
        context.put("requestUser", user);
    }

    @Then("Created user is correct")
    public void createdUserIsCorrect() throws NullReturnException {
        User createdUser = context.entities().getUsers().getLatest();
        User requestUser = context.get("requestUser", User.class);

        log.info("requested: " + JsonCoverter.toJsonString(requestUser));
        log.info("created: " + JsonCoverter.toJsonString(createdUser));

        Verify.shouldBe(equals.elements(createdUser, requestUser));
    }



}
