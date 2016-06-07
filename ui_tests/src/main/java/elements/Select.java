package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang.math.RandomUtils;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;

public class Select {

    public SelenideElement getElement() {
        return element;
    }

    public void setElement(SelenideElement element) {
        this.element = element;
    }

    private SelenideElement element;

    public Select(SelenideElement element) {
        this.element = element;
    }

    public ElementsCollection getOptions() {
        element.click();
        return element.$$("div.tagger-options-list > div.tagger-tag-option").shouldHave(sizeGreaterThan(0));
    }



    public void selectOption(String option) {
        element.click();
        try {
            SelenideElement foundOption = getOptions().findBy(Condition.text(option));
            navAndClickOption(foundOption);
        }catch (IndexOutOfBoundsException e){
            throw new AssertionError(String.format("There is no available option to click with name %s!", option));
        }
            }

    public void selectOption() {
        ElementsCollection options = getOptions();
        int index = RandomUtils.nextInt(options.size());
        SelenideElement option = options.get(index);
        navAndClickOption(option);
    }

    private void navAndClickOption(SelenideElement option) {
        option.scrollTo().click();

    }
}
