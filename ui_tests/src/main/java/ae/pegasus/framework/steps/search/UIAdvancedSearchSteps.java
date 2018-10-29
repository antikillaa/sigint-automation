package ae.pegasus.framework.steps.search;

import ae.pegasus.framework.assertion.Asserter;
import org.jbehave.core.annotations.Then;
import ae.pegasus.framework.pages.Pages;

public class UIAdvancedSearchSteps {
    @Then("I should see Advanced Search page")
    public void iShouldSeeAdvancedSearchPage() {
        Asserter.getAsserter().softAssertTrue(Pages.advancedSearchPage().isPageDisplayed(),
                "Advanced Search page is displayed",
                "Advanced Search page is NOT displayed");
    }
}
