package blocks.context.tables.reports;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import blocks.context.tables.Row;
import pages.reports.ReportDetailsDialog;
import pages.reports.ReportsEditPage;

import static com.codeborne.selenide.Selenide.page;

public class ReportRow extends Row {

    public ReportRow(SelenideElement row) {
        super(row);
    }

    public SelenideElement getShowInfoButton() {
        return row
                .$(By.xpath(".//button[contains(@class, 'btn-info')]"));
    }

    public ReportDetailsDialog clickShowInfoButton() {
        getShowInfoButton().hover().click();
        return page(ReportDetailsDialog.class);
    }


    public SelenideElement getActionsCell() {
        return row
                .$(By.xpath(".//div[contains(@class, 'pg-actions-cell')]"));
    }

    public SelenideElement getEditReportButton() {
        return getActionsCell()
                .$(By.xpath(".//a[contains(@route-href, 'ROUTE_REPORT_EDIT')]"));
    }

    public ReportsEditPage clickEditReportButton() {
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        actions.moveToElement(getEditReportButton()).click().build().perform();
        return page(ReportsEditPage.class);
    }


    public String getSubject() {
        return getCellByColumnName("Subject").text();
    }

    public String getReportID() {
        return getCellByColumnName("Report ID").text();
    }

    public String getOwner() {
        return getCellByColumnName("Owner").text();
    }
}
