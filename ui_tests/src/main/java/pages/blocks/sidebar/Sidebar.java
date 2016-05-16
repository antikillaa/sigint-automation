package pages.blocks.sidebar;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class Sidebar {

    private Logger log = Logger.getRootLogger();

    public SelenideElement getSidebar() {
        return $(By.xpath(".//div[@class='pg-sidebar au-target pg-sidebar-expanded']"));
    }

    public ElementsCollection getSubMenus() {
        return getSidebar()
                .$$(By.xpath(".//div[contains(@class, 'pg-sidebar__sub-menu')]"));
    }

    public SelenideElement getSubMenuByName(String subMenuName) {
        for (SelenideElement submenu : getSubMenus()) {
            SelenideElement element = submenu.$(By.xpath(".//div[@class='pg-sidebar__item__name']"));
            if (element.getText().contentEquals(subMenuName)) {
                return submenu;
            }
        }
        log.error("Sidebar subMenu by name: " + subMenuName + " doesn't found");
        throw new AssertionError("Sidebar subMenu by name: " + subMenuName + " doesn't found");
    }

    public ElementsCollection getSubMenuItems(String subMenuName) {
        return getSubMenuByName(subMenuName)
                .$$(By.xpath(".//div[@class='pg-sidebar__sub-items']/a"));
    }

    public SelenideElement getSubMenuItemByName(String subMenuName, String itemName) {
        return getSubMenuByName(subMenuName)
                .$(new Selectors.ByText(itemName));
    }

    public SelenideElement getSubMenuItemByName(String itemName) {
        return getSidebar()
                .$(new Selectors.ByText(itemName));
    }
}
