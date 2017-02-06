package steps;

import conditions.Verify;
import controllers.APILogin;
import http.OperationResult;
import http.OperationsResults;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;

import static conditions.Conditions.isTrue;


public class APILoginSteps extends APISteps {

    private static Logger LOGGER = Logger.getLogger(APILoginSteps.class);
    private static APILogin login = new APILogin();

    @Given("I sign in as $role user")
    public void signInGlobal(String role) {
        OperationResult operationResult = signInCreds(role);
        OperationsResults.setResult(operationResult);
        checkResultSuccess();
    }
    
    private OperationResult<Token> signInCreds(String role) {
        User user = GlobalSteps.getUserByRole(role);
        return login.signInAsUser(user);
    }
    
    @When("I sent sign in request as $role user with correct credentials")
    public void signInasCorrectUser(String role) {
        signInCreds(role);
    }
    
    @When("I sent sign in request as $role user with incorrect credentials")
    public void signInAsIncorrectUser(String role) {
        User user = new User();
        user.setName("test");
        user.setPassword("test");
        login.signInAsUser(user);
    }
    /**
    @Then("I got response code $real")
    public void checkResponseCode(String real) {
        LOGGER.info("Checking response code");
        Integer actual;
        actual = runContext.get("code", Integer.class);
        Integer expected = Integer.valueOf(real);
        Assert.assertEquals("Incorrect return codes!", expected, actual);

    }
     **/

    @Then("Error message is $message")
    public void checkErrorMessage(String message) throws IOException {
        LOGGER.info("Verifying error message");
        OperationResult operationResult = OperationsResults.getResult();
        Verify.shouldBe(isTrue.element(operationResult.getMessage().toLowerCase().contains(message.toLowerCase())));
    }


}
