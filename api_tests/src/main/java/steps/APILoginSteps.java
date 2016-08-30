package steps;

import app_context.RunContext;
import controllers.APILogin;
import http.ErrorResponse;
import json.JsonCoverter;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.io.IOException;


public class APILoginSteps {

    private static RunContext runContext = RunContext.get();
    private static Logger log = Logger.getRootLogger();
    private static APILogin login = new APILogin();
    private static GlobalSteps globalSteps = new GlobalSteps();


    @Given("I sign in as $role user")
    public void signInGlobal(String role) {
        signInCreds(role);
        checkResponseCode("200");
    
    }

    @When("I sent sign in request as $role user")
    public void signInCreds(String role) {
        User user = GlobalSteps.getUserByRole(role);
        login.signInAsUser(user);
    }

    @Then("I got response code $real")
    public void checkResponseCode(String real) {
        log.info("Checking response code");
        Integer actual;
        actual = runContext.get("code", Integer.class);
        Integer expected = Integer.valueOf(real);
        Assert.assertEquals("Incorrect return codes!", expected, actual);

    }

    @Then("Error message is $message")
    public void checkErrorMessage(String message) throws IOException {
        log.info("Verifying error message");
        ErrorResponse response = JsonCoverter.fromJsonToObject(runContext.get("message", String.class),
                ErrorResponse.class);
        Assert.assertTrue(response.getMessage().toLowerCase().equals(message.toLowerCase()));

    }


}
