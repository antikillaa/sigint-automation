package pages;

import blocks.navigation.MainMenu;
import blocks.navigation.menus.MenusFactory;
import com.codeborne.selenide.Condition;
import controllers.PageControllerFactory;
import org.jbehave.core.failures.RestartingScenarioFailure;

import java.util.HashMap;
import java.util.Map;


public class Navigator {

    private Map<String, MainMenu> menus = new HashMap<>();


    public PageControllerFactory navigate_to(String mainMenuName, String subMenuName) {
        PageControllerFactory factory;
        String menuName = mainMenuName.toLowerCase();
        if (!menus.containsKey(menuName)) {
            menus.put(menuName, MenusFactory.getMenu(mainMenuName));
        }
        MainMenu menu = menus.get(menuName);
        if (!menu.isExpanded()) {
            menu.click();
        }
        try {
            factory = menu.getSubMenuByName(subMenuName).click();
            factory.getPage().getHeader().getBreadcrumb().getCurrentPath().shouldHave(Condition.text(subMenuName));
            factory.getTableController().waitLoading();
        } catch (Exception e) {
            throw new RestartingScenarioFailure("Error on navigating to link. Will try one more", e);
        }
        return factory;
    }



}
