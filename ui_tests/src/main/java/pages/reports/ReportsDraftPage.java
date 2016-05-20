package pages.reports;

import model.AppContext;
import pages.SigintPage;
import pages.blocks.tables.ReportsTable;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.page;

public class ReportsDraftPage extends SigintPage {

    public static final String url = String.format("%s/#/app/reports/draft",
            AppContext.getContext().environment().getSigintHost());


    public ReportsTable getReportsTable() {
        return page(ReportsTable.class);
    }

    public ReportsDraftPage load() {
        if (getSidebar().getSubMenuItemByName("Draft").isDisplayed()) {
            getSidebar().getSubMenuItemByName("Draft").click();
        } else {
            getSidebar().getSubMenuItemByName("Reports").click();
            getSidebar().getSubMenuItemByName("Draft").click();
        }
        getPageLoading().shouldBe(disappear);

        getHeader().getBreadcrumb().getCurrentPath().shouldHave(attribute("href", ReportsReadyPage.url));
        return this;
    }

}
