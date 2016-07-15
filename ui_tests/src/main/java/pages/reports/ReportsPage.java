package pages.reports;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
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
        return $(By.xpath("//div[contains(@class, 'pg-form')]")).should(Condition.exist);
    }

    public SelenideElement getSubject() {
        return getForm()
                .$(By.xpath(".//input[@id='subject']"));
    }


    public ReportsPage setSubject(String subject) {
        getSubject().val(subject);
        return this;
    }

}
