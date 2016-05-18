package pages.records;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import model.AppContext;
import org.openqa.selenium.By;
import pages.SigintPage;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RecordsCreatePage extends SigintPage {

    public static final String url = String.format("%s/#/app/records/create",
            AppContext.getContext().environment().getSigintHost());


    public SelenideElement getForm() {
        return $(By.xpath("//form"));
    }

    public SelenideElement getColumnByNumber(int numberCol) {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6'][" + numberCol + "]"));
    }

    public SelenideElement getHeaderTitle() {
        return $(By.xpath("//div[@class='pg-header']" +
                "//div[@class='pg-header__col pg-header__title']"));
    }

    public ElementsCollection getLabels() {
        return getForm()
                .$$(By.xpath(".//div[@class='col-xs-6']" +
                "//div[contains(@class, 'form-group')]" +
                "//label"));
    }

    public SelenideElement getLabelByText(String text) {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6']" +
                "//div[contains(@class, 'form-group')]" +
                "//label[contains(., '" + text + "')]"));
    }

    public SelenideElement getSource() {
        return getForm()
                .$(By.xpath(".//text-tagger" +
                        "//div[@click.delegate='enableEditableMode()']"));
    }

    public RecordsCreatePage clickSource() {
        getSource().click();
        return this;
    }

    public ElementsCollection getOptionsList() {
        return $$(By.xpath(".//div[@class='tagger-options-list au-target']" +
                "/div[@click.delegate='$parent.toggleTag()']"));
    }

    public SelenideElement getFromNumber() {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6']" +
                        "//div[contains(@class, 'form-group')]" +
                        "//input[@value.two-way='item.fromNumber']"));
    }

    public RecordsCreatePage typeFromNumber(String fromNumber) {
        getFromNumber().val(fromNumber);
        return this;
    }

    public SelenideElement getFromCountry() {
        return getForm()
                .$(By.xpath(".//country-tagger[@selected.two-way='item.fromCountry']" +
                        "//div[@click.delegate='enableEditableMode()']"));
    }

    public RecordsCreatePage clickFromCountry() {
        getFromCountry().click();
        return this;
    }

    public RecordsCreatePage selectOptionByIndex(int index) {
        getOptionsList().get(index).scrollTo().click();
        return this;
    }

    public SelenideElement getLanguage() {
        return getForm()
                .$(By.xpath(".//language-tagger[@selected.two-way='item.language']" +
                        "//div[@click.delegate='enableEditableMode()']"));
    }

    public RecordsCreatePage clickLanguage() {
        getLanguage().click();
        return this;
    }

    public SelenideElement getTmsi() {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6']" +
                "//div[contains(@class, 'form-group')]" +
                "//input[@id='tmsi']"));
    }

    public RecordsCreatePage typeTmsi(String text) {
        getTmsi().val(text);
        return this;
    }

    public SelenideElement getSMSText() {
        return getForm()
                .$(By.xpath(".//textarea[@value.bind='value']"));
    }

    public RecordsCreatePage typeSMSText(String text) {
        getSMSText().val(text);
        return this;
    }

    public SelenideElement getCreateRecordButton() {
        return getForm()
                .$(By.xpath(".//button[contains(., 'Create record')]"));
    }

    public void clickCreateRecordButton() {
        getCreateRecordButton().click();
    }

    public SelenideElement getImsi() {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6']" +
                "//div[contains(@class, 'form-group')]" +
                "//input[@id='imsi']"));
    }

    public RecordsCreatePage typeImsi(String text) {
        getImsi().val(text);
        return this;
    }

    public SelenideElement getRecordId() {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6']" +
                "//div[contains(@class, 'form-group')]" +
                "//input[@id='originalId']"));
    }

    public RecordsCreatePage typeRecordId(String text) {
        getRecordId().val(text);
        return this;
    }

    public SelenideElement getToNumber() {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6']" +
                "//div[contains(@class, 'form-group')]" +
                "//input[@value.two-way='item.toNumber']"));
    }

    public RecordsCreatePage typeToNumber(String toNumber) {
        getToNumber().val(toNumber);
        return this;
    }

    public SelenideElement getToCountry() {
        return getForm()
                .$(By.xpath(".//country-tagger[@selected.two-way='item.toCountry']" +
                        "//div[@click.delegate='enableEditableMode()']"));
    }

    public RecordsCreatePage clickToCountry() {
        getToCountry().click();
        return this;
    }

    public SelenideElement getSMSRecordType() {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6']" +
                "//div[contains(@class, 'form-group')]" +
                "//label[contains(@class.bind, 'SMS')]"));
    }

    public RecordsCreatePage clickSMSRecordType() {
        getSMSRecordType().click();
        return this;
    }

    public SelenideElement getCallRecordType() {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6']" +
                "//div[contains(@class, 'form-group')]" +
                "//label[contains(@class.bind, 'Voice')]"));
    }

    public RecordsCreatePage clickCallRecordType() {
        getCallRecordType().click();
        return this;
    }

    public SelenideElement getCallDuration() {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6']" +
                "//div[contains(@class, 'form-group')]" +
                "//input[@id='duration']"));
    }

    public SelenideElement getDataAndTime() {
        return getForm()
                .$(By.xpath(".//div[@class='col-xs-6'][2]" +
                "//div[contains(@class, 'form-group')][1]" +
                "//input"));
    }

    public RecordsCreatePage clickCalendarApplyButton() {
        $$(By.xpath("//div[@class='daterangepicker dropdown-menu single opensright show-calendar']" +
                "//button[contains(.,'Apply')]"))
                .filter(visible)
                .get(0)
                .click();
        return this;
    }

    public RecordsCreatePage typeDataAndTime(Date date) {
        getDataAndTime().val(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date));
        clickCalendarApplyButton();
        return this;
    }

    public RecordsCreatePage typeCallDuration(int duration) {
        getCallDuration().val(Integer.toString(duration));
        return this;
    }

}
