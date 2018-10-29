package ae.pegasus.framework.pages.search;

import com.codeborne.selenide.SelenideElement;
import ae.pegasus.framework.constants.search.advanced.TypeOfAdvancedSearch;
import ae.pegasus.framework.pages.basic_pages.api.BasePage;

import static com.codeborne.selenide.Selenide.$$x;
import static ae.pegasus.framework.constants.search.advanced.TypeOfAdvancedSearch.*;

public class AdvancedSearchPage extends BasePage {

    @Override
    public boolean isPageDisplayed() {
        return getPageTitle().equalsIgnoreCase("Advanced Search")
                && getSearchTypeTab(KEYWORDS).isDisplayed()
                && getSearchTypeTab(FUZZY).isDisplayed()
                && getSearchTypeTab(PROXIMITY).isDisplayed()
                && getSearchTypeTab(BOOST_KEYWORD).isDisplayed()
                && getSearchTypeTab(FIELD_SEARCH).isDisplayed();
    }

    private SelenideElement getSearchTypeTab(TypeOfAdvancedSearch searchType) {
        for (SelenideElement tab : $$x("//div[@class='pg-nav-tabs__tabs']/div")) {
            if (tab.getText().trim().equalsIgnoreCase(searchType.getDisplayName())) {
                return tab;
            }
        }
        throw new IllegalArgumentException("Tab '" + searchType.getDisplayName() + "' was not found");
    }
}
