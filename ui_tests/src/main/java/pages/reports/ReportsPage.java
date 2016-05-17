package pages.reports;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.blocks.SidebarRightWrapper;

import java.util.Random;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public abstract class ReportsPage {

    public SidebarRightWrapper getSidebarRightWrapper() {
        return page(SidebarRightWrapper.class);
    }

    public SelenideElement getForm() {
        return $(By.xpath("//div[contains(@class, 'pg-form')]"));
    }

    public SelenideElement getSubject() {
        return getForm()
                .$(By.xpath(".//input[@id='subject']"));
    }

    public SelenideElement getRecordTypeSelect() {
        return getForm()
                .$(By.xpath(".//select[@value.bind='record.type']"));
    }

    public SelenideElement getSourceIdSelect() {
        return getForm()
                .$(By.xpath(".//select[@value.bind='record.sourceId']"));
    }

    public ReportsPage clickRecordTypeSelect() {
        getRecordTypeSelect().click();
        return this;
    }

    public ReportsPage clickSourceIdSelect() {
        getSourceIdSelect().click();
        return this;
    }

    public ElementsCollection getRecordTypeOptions() {
        return getRecordTypeSelect()
                .$$(By.xpath(".//option[@value.bind='r.id']"));
    }

    public ElementsCollection getSourceIdOptions() {
        return getSourceIdSelect()
                .$$(By.xpath(".//option[@value.bind='s.id']"));
    }

    public ReportsPage setSubject(String subject) {
        getSubject().val(subject);
        return this;
    }

    public ReportsPage selectRandomRecordType() {
        if (!getRecordTypeOptions().first().isDisplayed()) {
            clickRecordTypeSelect().getRecordTypeOptions().first().shouldBe(visible);
        }
        getRecordTypeOptions().get(new Random().nextInt(getRecordTypeOptions().size())).click();

        return this;
    }

    public ReportsPage selectRandomSourceId() {
        if (!getSourceIdOptions().first().isDisplayed()) {
            clickSourceIdSelect().getSourceIdOptions().first().shouldBe(visible);
        }
        getSourceIdOptions().get(new Random().nextInt(getSourceIdOptions().size())).click();

        return this;
    }
}
