package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;

public class Select {

    public SelenideElement getElement() {
        return element;
    }

    public void setElement(SelenideElement element) {
        this.element = element;
    }

    protected SelenideElement element;

    public Select(SelenideElement element) {
        this.element = element;
    }

    public ElementsCollection getOptions() {
        element.click();
        return element.$$("div.tagger-options-list > div.tagger-tag-option").shouldHave(sizeGreaterThan(0));
    }




    public void selectOptionByText(String option) {
        element.click();
        try {
            SelenideElement foundOption = getOptions().findBy(Condition.text(option));
            navAndClickOption(foundOption);
        }catch (IndexOutOfBoundsException e){
            throw new AssertionError(String.format("There is no available option to click with name %s!", option));
        }
            }


    public void selectOptionbyValue(String option) {
        element.click();
        try {
            SelenideElement foundOption = getOptions().findBy(Condition.attribute("value", option));
            navAndClickOption(foundOption);
        }catch (IndexOutOfBoundsException e) {
            throw new AssertionError(String.format("There is no available option to click with name %s!", option));
        }
    }

    private void navAndClickOption(SelenideElement option) {
        option.scrollTo().click();

    }
}
