package pages.reports;

import com.codeborne.selenide.SelenideElement;
import elements.Select;
import org.openqa.selenium.By;
import pages.SigintPage;
import pages.reports.sidebar.ReportActionBar;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public abstract class ReportsPage extends SigintPage {

    public ReportActionBar getActionBar() {
        return page(ReportActionBar.class);
    }

    public SelenideElement getForm() {
        return $(By.xpath("//div[contains(@class, 'pg-form')]"));
    }

    public SelenideElement getSubject() {
        return getForm()
                .$(By.xpath(".//input[@id='subject']"));
    }

    public Select getRecordTypeSelect() {
        return new Select(getForm()
                .$(By.xpath(".//select[@value.bind='record.type']")));
    }

    public Select getSourceSelect() {
        return new Select(getForm()
                .$(By.xpath(".//select[@value.bind='record.sourceId']")));
    }

    public ReportsPage selectRecordType(String recordType) {
        getRecordTypeSelect().selectOption(recordType);
        return this;
    }

    public ReportsPage selectSourceType(String sourceType) {
        getSourceSelect().selectOption(sourceType);
        return this;
    }

    public ReportsPage setSubject(String subject) {
        getSubject().val(subject);
        return this;
    }

}
