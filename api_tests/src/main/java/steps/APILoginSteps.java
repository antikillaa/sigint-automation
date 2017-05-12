package steps;

import model.entities.Entities;
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
import utils.CollectionUtils;

import java.io.IOException;

import static conditions.Conditions.isTrue;


public class APILoginSteps extends APISteps {

    private static Logger log = Logger.getLogger(APILoginSteps.class);
    private static APILogin login = new APILogin();

    @Given("I sign in as $role user")
    public void signInGlobal(String role) {
        signInCreds(role);
        checkResultSuccess();
    }

    @Given("I sign in as user with permissions $permissions")
    public void signInWithPermissions(String permString) {
        String[] permissions = CollectionUtils.trimSpaces(permString.split(","));
        User user = GlobalSteps.getUserWithPermissions(permissions);
        signInCreds(user);
        checkResultSuccess();
    }

    private OperationResult<Token> signInCreds(String role) {
        User user = GlobalSteps.getUserByRole(role);
        return login.signInAsUser(user);
    }
    
    private OperationResult<Token> signInCreds(User user) {
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

    @When("I send sign out request")
    public void signOut() {
        login.signOut();
    }

    @When("I sign in as new created user")
    public void signInAsNewCreatedUser() {
        User user = Entities.getUsers().getLatest();
        login.signInAsUser(user);
    }
}
