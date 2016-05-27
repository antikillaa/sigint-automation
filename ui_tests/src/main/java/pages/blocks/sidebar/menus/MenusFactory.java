package pages.blocks.sidebar.menus;

import pages.blocks.sidebar.MainMenu;
import pages.blocks.sidebar.Sidebar;

import static com.codeborne.selenide.Selenide.page;

public class MenusFactory {

    private static Sidebar sidebar = page(Sidebar.class);

    public static MainMenu getMenu(String name) {
        if (name.equalsIgnoreCase("records")) {
            return new RecordsMenu(sidebar.getMainMenuByName(name));
        } else if (name.equalsIgnoreCase("reports")) {
            return new ReportsMenu(sidebar.getMainMenuByName(name));
        }
        throw new AssertionError(String.format("Menu with name %s is not found!", name));

    }
}
