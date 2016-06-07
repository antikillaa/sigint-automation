package blocks.navigation.menus;

import blocks.navigation.MainMenu;
import com.codeborne.selenide.SelenideElement;
import controllers.PageControllerFactory;
import controllers.records.all_page.RecordsAllController;
import controllers.records.processed_page.RecordsProcessedController;

public class  RecordsMenu extends MainMenu {

    public RecordsMenu(SelenideElement menuElement) {
        super(menuElement);
    }

    @Override
    protected PageControllerFactory getController(String name) {
        if (name.equalsIgnoreCase("search")) {
            return new RecordsAllController();
        } else if (name.equalsIgnoreCase("processed")) {
            return new RecordsProcessedController();
        }
        throw new AssertionError(String.format("Page %s is not found!", name));
    }

}
