package pages;

import com.codeborne.selenide.SelenideElement;
import conditions.UIConditions;
import conditions.Verify;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public abstract class BaseSection {

    private String baseSelect;

    public BaseSection(String baseURL) {
        this.baseSelect = baseURL;
    }

    public SelenideElement get() {
        Verify.shouldNotBe(UIConditions.present.element(getLoading()));
        return $(baseSelect);

    }

    public SelenideElement getLoading() {
        return $(By.xpath(baseSelect+ "//span[@class='inline-block' and contains(.,'Loading…')]"));
    }

}
