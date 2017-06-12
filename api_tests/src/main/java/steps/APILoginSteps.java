package steps;

import controllers.APILogin;
import model.User;
import model.entities.Entities;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;


public class APILoginSteps extends APISteps {

    private static Logger log = Logger.getLogger(APILoginSteps.class);
    private static APILogin login = new APILogin();

    @Given("I sign in as $role user")
    public void signInGlobal(String role) {
        login.signInAsUser(role);
        checkResultSuccess();
    }
    
    @When("I sent sign in request as $role user with correct credentials")
    public void signInasCorrectUser(String role) {
        login.signInAsUser(role);
    }
    
    @When("I sent sign in request as $role user with incorrect credentials")
    public void signInAsIncorrectUser(String role) {
        User user = new User();
        user.setName("test");
        user.setPassword("test");
        login.signInAsUser(user);
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
