package elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang.math.RandomUtils;
import org.openqa.selenium.By;

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
        return element.$$(By.xpath(".//div[@class='tagger-options-list au-target']" +
                "/div[@click.delegate='$parent.toggleTag()']"));
    }

    public void selectOption(String option) {
        ElementsCollection options = getOptions();
        for (SelenideElement curOption: options) {
            if(curOption.getText().equalsIgnoreCase(option)) {
                navAndClickOption(curOption);
            }
        }

    }
    public void selectOption() {
        ElementsCollection options = getOptions();
        int index = RandomUtils.nextInt(options.size());
        SelenideElement option = options.get(index);
        navAndClickOption(option);
    }

    private void navAndClickOption(SelenideElement option) {
        element.click();
        option.scrollTo().click();
    }
}
