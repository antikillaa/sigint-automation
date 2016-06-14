package blocks.common.header;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import blocks.common.header.breadcrumb.Breadcrumb;
import pages.login.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Header {

    private Breadcrumb breadcrumb = page(Breadcrumb.class);

    public SelenideElement get() {
        return $(By.xpath(".//div[@class='pg-common']/div[@class='pg-header']"));
    }

    public SelenideElement getUserProfile() {
        return $(By.xpath("//*[@id='userprofile']"));
    }

    public Header clickUserProfile() {
        getUserProfile().click();
        return this;
    }

    public SelenideElement getUserProfileDropdownMenu() {
        return $(By.xpath("//*[@aria-labelledby='userprofile']"));
    }

    public SelenideElement getSignOut() {
        return $(By.xpath("//a[@click.trigger='logout()']"));
    }

    public LoginPage clickSignOut() {
        getSignOut().click();
        return page(LoginPage.class);
    }

    public Breadcrumb getBreadcrumb() {
        return breadcrumb;
    }

}
