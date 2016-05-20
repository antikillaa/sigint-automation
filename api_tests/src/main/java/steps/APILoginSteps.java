package steps;
import http.requests.ErrorResponse;
import json.JsonCoverter;
import model.AppContext;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.UserService;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by dm on 3/25/16.
 */
public class APILoginSteps {

    private UserService userService = new UserService();
    private AppContext context = AppContext.getContext();
    Logger log = Logger.getRootLogger();

    @Given("I sign in as $role user")
    public void signInGlobal(String role) {
        setUserToContext(role);
        signInWithCrendentials("correct");
        checkResponseCode("200");

        try {
            checkAndPutToken();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Given("as $role user")
    public void setUserToContext(String role) {
        log.info("Getting user with role " + role);
        User user = GlobalSteps.getUserByRole(role);
        context.putToRunContext("user", user);
    }

    @When("I sent sign in request with $validness credentials")
    public void signInWithCrendentials(String validness) {
        log.info("Signing in...");
        Response response;
        User user = context.getFromRunContext("user", User.class);
        String password;
        if (validness.toLowerCase().equals("incorrect")) {
            password = "incorrect";
        } else {
            password = user.getPassword();
        }
        response = userService.signIn(user.getName(), password);
        context.putToRunContext("code", response.getStatus());
        context.putToRunContext("message", response.readEntity(String.class));
    }


    @Then("I got response code $real")
    public void checkResponseCode(String real) {
        log.info("Checking response code");
        Integer actual;
        actual = context.getFromRunContext("code", Integer.class);
        Integer expected = Integer.valueOf(real);
        Assert.assertEquals("Incorrect return codes!", expected, actual);

    }

    @Then("I got token in response")
    public void checkAndPutToken() throws IOException {
        log.info("Verifying if token is received");
        Token token = JsonCoverter.fromJsonToObject(context.getFromRunContext("message", String.class), Token.class);
        context.environment().setToken(token);
    }

    @Then("Error message is $message")
    public void checkErrorMessage(String message) throws IOException {
        log.info("Verifying error message");
        ErrorResponse response = JsonCoverter.fromJsonToObject(context.getFromRunContext("message", String.class),
                    ErrorResponse.class);
        Assert.assertTrue(response.getMessage().toLowerCase().equals(message.toLowerCase()));

    }
}
