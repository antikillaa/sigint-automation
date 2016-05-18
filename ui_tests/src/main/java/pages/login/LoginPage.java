package pages.login;

import com.codeborne.selenide.SelenideElement;
import model.AppContext;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.present;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginPage {

    private final String url = String.format("%s/#/login", AppContext.getContext().environment().getSigintHost());

    public SelenideElement getUsernameField() {
        return $(By.id("inputEmail")).shouldBe(present);
    }

    public SelenideElement getPageLoading() {
        return $(By.xpath("//div[@class='page-loading text-center']"));
    }

    public SelenideElement getPasswordField() {
        return $(By.id("inputPassword"));
    }

    public SelenideElement getSignInButton() {
        return $(By.xpath("//button[@type='submit']"));
    }

    public SelenideElement getFormSignin() {
        return $(By.xpath("//form[@class='form-signin au-target']"));
    }

    /**
     * Load SigInt Login page
     *
     * @return pageObject
     */
    public LoginPage load() {
        System.out.println(url);
        open(url);

        getPageLoading().shouldBe(hidden);

        String currentURL = getWebDriver().getCurrentUrl();
        if (!currentURL.equals(url)) {
            throw new Error("[ERROR] Current Login page url: " + currentURL + ", expected: " + url);
        }

        return this;
    }

    public String getUrl() {
        return url;
    }

    public LoginPage typeUsername(String username) {
        getUsernameField().val(username);
        return this;
    }

    public LoginPage typePassword(String password) {
        getPasswordField().val(password);
        return this;
    }

    public void clickSignInButton() {
        getSignInButton().click();
    }

    public SelenideElement getErrorMessage() {
        return $(By.xpath("//form[@class='form-signin au-target']" +
                "//span[@class='text-danger m-b-sm']"));
    }

}
