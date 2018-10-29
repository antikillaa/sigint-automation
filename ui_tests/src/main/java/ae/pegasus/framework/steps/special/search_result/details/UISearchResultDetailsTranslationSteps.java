package ae.pegasus.framework.steps.special.search_result.details;

import ae.pegasus.framework.assertion.Asserter;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import ae.pegasus.framework.pages.Pages;
import ae.pegasus.framework.utils.LanguageUtils;

import java.io.IOException;
import java.util.Locale;

import static ae.pegasus.framework.constants.special.results_details.SearchResultsDetailsAction.TRANSLATE_TO_ENGLISH;
import static ae.pegasus.framework.constants.special.results_details.SearchResultsTranslationType.UNOFFICIAL_TRANSLATION;

public class UISearchResultDetailsTranslationSteps {

    @When("I perform translation to English on the search result Details page")
    public void iPerformTranslationToEnglish() {
        Pages.searchResultDetailsPage().performAction(TRANSLATE_TO_ENGLISH);
    }

    @Then("I should see unofficial translation in English on the search result Details page")
    public void iShouldSeeUnofficialTranslationInEnglish() throws IOException {
        String translation = Pages.searchResultDetailsPage().getTranslation(UNOFFICIAL_TRANSLATION);
        if (translation == null || translation.isEmpty()) {
            Asserter.getAsserter().softAssertTrue(false, "", UNOFFICIAL_TRANSLATION.getTranslationType() + " is NOT displayed");
        } else {
            Asserter.getAsserter().softAssertEquals(
                    LanguageUtils.detectLanguage(translation).getLanguage(),
                    Locale.ENGLISH.getLanguage(),
                    UNOFFICIAL_TRANSLATION.getTranslationType() + " Language");
        }
    }

    @Given("I remove unofficial translation on the search result Details page")
    public void iRemoveUnofficialTranslation() {
        Pages.searchResultDetailsPage().clearTranslation(UNOFFICIAL_TRANSLATION);
    }

    @Given("I close search result Details page")
    public void iCloseDetailsPage() {
        Pages.searchResultDetailsPage().closeDialog();
    }

    @When("I save unofficial translation on the search result Details page")
    public void iSaveUnofficialTranslation() {
        Pages.searchResultDetailsPage().saveTranslation(UNOFFICIAL_TRANSLATION);
    }

    @When("I set unofficial translation on the search result Details page to ($translation)")
    public void iSetUnofficialTranslation(String translation) {
        Pages.searchResultDetailsPage().setTranslation(UNOFFICIAL_TRANSLATION, translation);
    }

    @Then("I should see ($translation) as unofficial translation on the search result Details page")
    public void iShouldSeeTextAsUnofficialTranslation(String translation) {
        Asserter.getAsserter().softAssertEquals(
                Pages.searchResultDetailsPage().getTranslation(UNOFFICIAL_TRANSLATION),
                translation,
                UNOFFICIAL_TRANSLATION.getTranslationType());
    }
}
