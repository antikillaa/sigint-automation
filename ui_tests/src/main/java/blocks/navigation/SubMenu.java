package blocks.navigation;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import controllers.PageControllerFactory;
import pages.BasePage;

public class SubMenu  {

    private SelenideElement element;
    private PageControllerFactory controller;

    public SubMenu(SelenideElement element, PageControllerFactory controller) {
        this.controller = controller;
        this.element = element;
    }

    public PageControllerFactory click() {
        element.click();
        BasePage.getPageLoading().shouldBe(Condition.disappear);
        return controller;

    }
}
