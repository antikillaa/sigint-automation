package steps;

import app_context.entities.Entities;
import conditions.Verify;
import controllers.APILogin;
import http.OperationResult;
import http.OperationsResults;
import model.RequestResult;
import model.Token;
import model.User;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;

import static conditions.Conditions.isTrue;


public class APILoginSteps extends APISteps {

    private static Logger log = Logger.getLogger(APILoginSteps.class);
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

    @Then("Error message is $message")
    public void checkErrorMessage(String message) throws IOException {
        log.info("Verifying error message");
        OperationResult operationResult = OperationsResults.getResult();
        Verify.shouldBe(isTrue.element(operationResult.getMessage().toLowerCase().contains(message.toLowerCase())));
    }

    @When("I send sing out request")
    public void singOut() {
        OperationResult<RequestResult> operationResult = login.singOut();
        OperationsResults.setResult(operationResult);
    }

    @When("I sign in as new created user")
    public void singInAsNewCreatedUser() {
        User user = Entities.getUsers().getLatest();

        OperationResult<Token> operationResult = login.signInAsUser(user);
        OperationsResults.setResult(operationResult);
    }
}
