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

    public String getToNumber() {
        return getReportRecordRow()
                .$(By.xpath(".//div[@textcontent.bind='record.toNumber']")).text();
    }

    public String getFromNumber() {
        return getReportRecordRow()
                .$(By.xpath(".//div[@textcontent.bind='record.fromNumber']")).text();
    }

}
