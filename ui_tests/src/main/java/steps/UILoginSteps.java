package steps;

import com.codeborne.selenide.WebDriverRunner;
import org.jbehave.core.annotations.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.sleep;

public class UILoginSteps extends UISteps {


    private void login(String username, String password) {
        pages.loginPage().getUsernameField().val(username);
        pages.loginPage().getPasswordField().val(password);
        pages.loginPage().clickSignInButton();
    }


    private void iShouldSeeSigintPage() {
        pages.recordsSearchPage().getMain().get().shouldBe(present);
        pages.recordsSearchPage().getHeader().get().shouldBe(present);
        pages.recordsSearchPage().getSidebar().getSidebar().shouldBe(present);
    }


    @Given("I as <role> try sign in with incorrect credentials")
    public void userAsRoleSignInWithIncorrectCredentials(@Named("role") String role) {
        user = getUserByRole(role);
        pages.loginPage().load();

        login(
                user.getName(),
                user.getPassword() + "qwe"
        );
    }


    @Then("I should see $message error")
    public void userDoesNotSignIn(String message) {
        pages.recordsSearchPage().getMain().get().shouldNotBe(present);
        pages.recordsSearchPage().getHeader().get().shouldNotBe(present);
        pages.recordsSearchPage().getSidebar().getSidebar().shouldNotBe(present);

        pages.loginPage().getErrorMessage().shouldHave(text(message));
    }


    private static void WaitForReady() {
        WebDriver driver = WebDriverRunner.getWebDriver();

        new WebDriverWait(driver, 30).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                return (Boolean) js.executeScript("return jQuery.active == 0");
            }
        });
    }


    @When("I sign out")
    public void userSignOut() {
        WaitForReady();
        sleep(5000); //TODO

        pages.recordsSearchPage()
                .getHeader().clickUserProfile()
                .clickSignOut();
    }


    @Then("I should see Login page")
    public void userSeeLoginPage() {
        pages.loginPage().getFormSignin().shouldBe(appear);
    }


    @Given("I logged in as $role")
    @Then("I logged in as $role")
    public void iLoggedInAsOperator(@Named("role") String role) {
        // Arrange
        user = getUserByRole(role);
        pages.loginPage().load();

        // Act
        login(
                user.getName(),
                user.getPassword()
        );

        // Assert
        iShouldSeeSigintPage();
    }
}
