package pages.blocks.sidebar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import pages.SigintPage;

import java.util.HashMap;
import java.util.Map;


public abstract class MainMenu {

    private SelenideElement element;
    private Logger log = Logger.getLogger(MainMenu.class);
    private Map<String, SubMenu> submenues = new HashMap<>();


    public MainMenu(SelenideElement menuElement) {
        this.element = menuElement;

    }

    public void click() {
        element.click();
    }

    protected abstract SigintPage getPage(String pageName);

    public Boolean isExpanded() {
        try {
            element.$(".pg-sidebar__item").shouldNotHave(Condition.cssClass("pg-sidebar__item--collapsed"));
        } catch (Exception e) {
            log.debug(String.format("Menu %s expanded", element.getText()));
            return true;
        }
        return false;
    }

    public SubMenu getSubMenuByName(String name) {
        if (!submenues.containsKey(name)) {
            submenues.put(name, new SubMenu(element.$(new Selectors.ByText(name)), getPage(name)));
        }
        return submenues.get(name);
    }
}
