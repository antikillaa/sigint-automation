package blocks.navigation.menus;

import blocks.navigation.MainMenu;
import com.codeborne.selenide.SelenideElement;
import controllers.PageControllerFactory;

public class ReportsMenu extends MainMenu {

    public ReportsMenu(SelenideElement menuElement) {
        super(menuElement);
    }

    @Override
    protected PageControllerFactory getController(String pageName) {
        return null;
    }


}
