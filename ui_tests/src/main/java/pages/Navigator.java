package pages;

import pages.blocks.sidebar.MainMenu;
import pages.blocks.sidebar.menus.MenusFactory;

import java.util.HashMap;
import java.util.Map;


public class Navigator {

    private Map<String, MainMenu> menus = new HashMap<>();


    public SigintPage navigate_to(String mainMenuName, String subMenuName) {
        String menuName = mainMenuName.toLowerCase();
        if (!menus.containsKey(menuName)) {
            menus.put(menuName, MenusFactory.getMenu(mainMenuName));
        }
        MainMenu menu = menus.get(menuName);
        if (!menu.isExpanded()) {
            menu.click();
        }
        return menu.getSubMenuByName(subMenuName).click();
    }



}
