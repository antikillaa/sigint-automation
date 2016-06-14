package steps;

import controllers.APILogin;
import http.ErrorResponse;
import json.JsonCoverter;
import model.AppContext;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.io.IOException;


public class APILoginSteps {

    private static AppContext context = AppContext.getContext();
    private static Logger log = Logger.getRootLogger();
    private static APILogin login = new APILogin();
    private static GlobalSteps globalSteps = new GlobalSteps();


    @Given("I sign in as $role user")
    public void signInGlobal(String role) {
        setUser(role);
        signInCreds("correct");
        checkResponseCode("200");

    }

    @Given("as $role user")
    public void setUser(String role) {
        globalSteps.setUserToContext(role);
    }

    @When("I sent sign in request with $validness credentials")
    public void signInCreds(String validness) {
        login.signInWithCrendentials(validness);
    }

    @Then("I got response code $real")
    public void checkResponseCode(String real) {
        log.info("Checking response code");
        Integer actual;
        actual = context.get("code", Integer.class);
        Integer expected = Integer.valueOf(real);
        Assert.assertEquals("Incorrect return codes!", expected, actual);

    }

    @Then("Error message is $message")
    public void checkErrorMessage(String message) throws IOException {
        log.info("Verifying error message");
        ErrorResponse response = JsonCoverter.fromJsonToObject(context.get("message", String.class),
                ErrorResponse.class);
        Assert.assertTrue(response.getMessage().toLowerCase().equals(message.toLowerCase()));

    }


}
