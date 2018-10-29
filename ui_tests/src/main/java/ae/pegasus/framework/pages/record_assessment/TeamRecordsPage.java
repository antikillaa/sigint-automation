package ae.pegasus.framework.pages.record_assessment;

import ae.pegasus.framework.pages.basic_pages.api.BasePageWithSearch;

public class TeamRecordsPage extends BasePageWithSearch {

    @Override
    public boolean isPageDisplayed() {
        return getPageTitle().equalsIgnoreCase("Team Records")
                && super.isPageDisplayed();
    }
}
