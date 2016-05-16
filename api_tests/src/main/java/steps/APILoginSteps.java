package steps;
import errors.NullReturnException;
import model.AppContext;
import http.requests.ErrorResponse;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import rs.client.JsonCoverter;
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
        User user = null;
        try {
            user = context.getEntitiesList(UsersList.class).getEntity(role);
        }  catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }
        context.putToRunContext("user", user);
    }

    @When("I sent sign in request with $validness credentials")
    public void signInWithCrendentials(String validness) {
        log.info("Signing in...");
        Response response = null;
        User user;
        try {
            user = context.getFromRunContext("user", User.class);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }
        if (validness.toLowerCase().equals("incorrect")) {
            user.setPassword("incorrect");
        }
        response = userService.signIn(user);
        context.putToRunContext("code", response.getStatus());
        context.putToRunContext("message", response.readEntity(String.class));
    }


    @Then("I got response code $real")
    public void checkResponseCode(String real) {
        log.info("Checking response code");
        Integer actual;
        try {
            actual = context.getFromRunContext("code", Integer.class);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("Return code wasn't received");
        }
        Integer expected = Integer.valueOf(real);
        Assert.assertEquals("Actual code:"+actual+" Expected code:"+real, actual, expected);

    }

    @Then("I got token in response")
    public void checkAndPutToken() throws IOException {
        log.info("Verifying if token is received");
        Token token;
        try {
            token = JsonCoverter.fromJsonToObject(context.getFromRunContext("message", String.class), Token.class);
        } catch (NullReturnException e) {
            log.error("Cannot get token!");
            throw new AssertionError();
        }
        context.setToken(token);
    }

    @Then("Error message is $message")
    public void checkErrorMessage(String message) throws IOException {
        log.info("Verifying error message");
        ErrorResponse response;
        try {
            response = JsonCoverter.fromJsonToObject(context.getFromRunContext("message", String.class),
                    ErrorResponse.class);
            Assert.assertTrue(response.getMessage().toLowerCase().equals(message.toLowerCase()));
        } catch (NullReturnException e) {
            log.error("Message is not received!");
            log.error(e.getMessage());
            throw new AssertionError("Error message wasn't not received!");
        }
    }
}
