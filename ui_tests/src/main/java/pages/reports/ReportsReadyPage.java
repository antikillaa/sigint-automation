package pages.reports;

import blocks.context.Context;
import model.AppContext;
import pages.SigintPage;
import blocks.context.tables.reports.ReportsTable;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.page;

public class ReportsReadyPage extends SigintPage {

    public static final String url = String.format("%s/#/app/reports/unassigned",
            AppContext.getContext().environment().getSigintHost());

    private ReportsTable reportsTable = page(ReportsTable.class);


    public ReportsTable getReportsTable() {
        return reportsTable;
    }

    public ReportsReadyPage load() {
        if (getSidebar().getSubMenuItemByName("Ready").isDisplayed()) {
            getSidebar().getSubMenuItemByName("Ready").click();
        } else {
            getSidebar().getSubMenuItemByName("Reports").click();
            getSidebar().getSubMenuItemByName("Ready").click();
        }
        getPageLoading().shouldBe(disappear);

        getHeader().getBreadcrumb().getCurrentPath().shouldHave(attribute("href", ReportsReadyPage.url));
        return this;
    }

    @Override
    public Context context() {
        return null;
    }
}
