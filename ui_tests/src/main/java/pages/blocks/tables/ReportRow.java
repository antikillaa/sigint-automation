package pages.blocks.tables;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import pages.reports.ReportDetailsDialog;
import pages.reports.ReportsEditPage;

import static com.codeborne.selenide.Selenide.page;

public class ReportRow extends Row {

    public ReportRow(SelenideElement row) {
        super(row);
    }

    public SelenideElement getShowInfoButton() {
        return getRow()
                .$(By.xpath(".//button[contains(@class, 'btn-info')]"));
    }

    public ReportDetailsDialog clickShowInfoButton() {
        getShowInfoButton().click();
        return page(ReportDetailsDialog.class);
    }

    public ReportRow selectReport() {
        if (!getCheckBox().isSelected()) {
            getCheckBox().click();
        }
        return this;
    }

    public SelenideElement getActionsCell() {
        return getRow()
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
}
