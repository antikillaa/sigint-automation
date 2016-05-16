package pages.records;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class RecordDetailsDialog {

    public SelenideElement getRecordDetailsDialog() {
        return $(By.xpath("//div[@id='recordDetailsDialog']"));
    }

    public SelenideElement getAttachButton() {
        return getRecordDetailsDialog()
                .$(By.xpath(".//attach-to-report/div/button"));
    }

    public RecordDetailsDialog clickAttachButton() {
        getAttachButton().click();
        return this;
    }

    public ElementsCollection getAttachListElements() {
        return getRecordDetailsDialog()
                .$$(By.xpath(".//attach-to-report//li//div[contains(@textcontent.bind, 'r.subject')]"));
    }


}
