package ae.pegasus.framework.steps;

import ae.pegasus.framework.app_context.properties.G4Properties;
import com.codeborne.selenide.WebDriverRunner;
import org.jbehave.core.annotations.Given;
import org.openqa.selenium.logging.LogType;
import ae.pegasus.framework.pages.Pages;

import java.util.logging.Level;

public class UISearchAuthorizationSteps {
    @Given("I setup Search Authorization")
    public void iSetupSearchAuthorization() {
        Pages.searchAuthorizationPage().enterDataForSearchAuthorization();
    }

    @Given("I setup Search Authorization for failed query")
    public void iSetupSearchAuthorizationfailedquery() {
        Pages.searchAuthorizationPage().enterDataForSearchAuthorization();
        if(G4Properties.getRunProperties().isSuppressKnownIssues()) {
            WebDriverRunner.getWebDriver().manage().logs().get(LogType.BROWSER).filter(Level.SEVERE);
        }
    }
}
