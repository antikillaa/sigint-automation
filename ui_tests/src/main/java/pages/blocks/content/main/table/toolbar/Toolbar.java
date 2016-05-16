package pages.blocks.content.main.table.toolbar;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.records.RecordsCreatePage;
import pages.reports.ReportsCreatePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Toolbar {

    public SelenideElement getToolbar() {
        return $(By.xpath(".//div[@class='pg-table base-padding']" +
                "/div[@class='pg-toolbar']"));
    }

    public SelenideElement getNewRecordButton() {
        return getToolbar()
                .$(By.xpath("//button[@click.trigger='createNewRecord()']"));
    }

    public RecordsCreatePage clickNewRecordButton() {
        getNewRecordButton().click();
        return page(RecordsCreatePage.class);
    }

    public SelenideElement getCreateReportButton() {
        return getToolbar()
                .$(By.xpath(".//button[@click.trigger='createReportFromMultipleRecords()']"));
    }

    public ReportsCreatePage clickCreateReportButton() {
        getCreateReportButton().click();
        return page(ReportsCreatePage.class);
    }


}
