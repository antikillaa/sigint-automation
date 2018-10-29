package ae.pegasus.framework.steps.record_assessment;

import ae.pegasus.framework.assertion.Asserter;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import ae.pegasus.framework.pages.Pages;

public class UITeamRecordsSteps {
    @Then("I should see Team Records page")
    public void iShouldSeeSearchPage() {
        Asserter.getAsserter().softAssertTrue(Pages.teamRecordsPage().isPageDisplayed(),
                "Team Records page is displayed",
                "Team Records page is NOT displayed");
    }

    @When("I enter search criteria ($searchCriteria) on the Team Records page")
    public void iEnterSearchCriteria(String searchCriteria) {
        Pages.myRecordsPage().enterSearchCriteria(searchCriteria);
    }

    @When("I start search on Team Records page")
    public void iStartSearch() {
        Pages.teamRecordsPage().startSearch();
    }

    @Then("I should see search criteria ($expectedSearchCriteria) on the Team Records page")
    public void iShouldSeeSearchCriteria(String expectedSearchCriteria) {
        Asserter.getAsserter().softAssertEquals(Pages.teamRecordsPage().getCurrentSearchCriteria(),
                expectedSearchCriteria,
                "Search criteria on the My Records page");
    }

    @Given("I open Search Filter on the Team Records page")
    public void iOpenSearchFilter() {
        Pages.teamRecordsPage().openSearchFilter();
    }

    @Given("I Apply Search using Search Filter on the Team Records page")
    public void iApplySearch() {
        Pages.searchFilterPage().applySearch();
        Pages.teamRecordsPage().waitForPageLoading();
    }
}
