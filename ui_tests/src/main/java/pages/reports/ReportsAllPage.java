package pages.reports;

import model.AppContext;
import pages.SigintPage;
import pages.blocks.content.main.table.ReportsTable;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.page;

public class ReportsAllPage extends SigintPage {

    public static final String url = String.format("%s/#/app/reports/all",
            AppContext.getContext().environment().getSigintHost());

    private ReportsTable reportsTable = page(ReportsTable.class);


    public ReportsTable getReportsTable() {
        return reportsTable;
    }

    public ReportsAllPage load() {
        if (getSidebar().getSubMenuItemByName("Reports", "All").isDisplayed()) {
            getSidebar().getSubMenuItemByName("Reports", "All").click();
        } else {
            getSidebar().getSubMenuItemByName("Reports").click();
            getSidebar().getSubMenuItemByName("Reports", "All").click();
        }
        getHeader().getBreadcrumb().getCurrentPath().shouldHave(attribute("href", ReportsAllPage.url));
        return this;
    }

}
