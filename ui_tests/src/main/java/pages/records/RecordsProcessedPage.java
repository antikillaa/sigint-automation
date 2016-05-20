package pages.records;

import model.AppContext;
import pages.SigintPage;
import pages.blocks.tables.RecordsTable;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.page;

public class RecordsProcessedPage extends SigintPage {

    public static final String url = String.format("%s/#/app/records/processed",
            AppContext.getContext().environment().getSigintHost());

    private RecordsTable table = page(RecordsTable.class);


    public RecordsTable getTable() {
        return table;
    }

    public RecordsProcessedPage load() {
        if (getSidebar().getSubMenuItemByName("Processed").isDisplayed()) {
            getSidebar().getSubMenuItemByName("Processed").click();
        } else {
            getSidebar().getSubMenuItemByName("Records").click();
            getSidebar().getSubMenuItemByName("Processed").click();
        }
        getHeader().getBreadcrumb().getCurrentPath().shouldHave(attribute("href", RecordsProcessedPage.url));
        return this;
    }
}
