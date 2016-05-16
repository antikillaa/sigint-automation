package pages.reports;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class ReportRecordRow {

    private SelenideElement reportRecordRow;


    public ReportRecordRow(SelenideElement reportRecordRow)  {
        this.reportRecordRow = reportRecordRow;
    }

    public SelenideElement getReportRecordRow() {
        return reportRecordRow;
    }

    public SelenideElement getToNumber() {
        return getReportRecordRow()
                .$(By.xpath(".//div[@textcontent.bind='record.toNumber']"));
    }

    public SelenideElement getFromNumber() {
        return getReportRecordRow()
                .$(By.xpath(".//div[@textcontent.bind='record.fromNumber']"));
    }

}
