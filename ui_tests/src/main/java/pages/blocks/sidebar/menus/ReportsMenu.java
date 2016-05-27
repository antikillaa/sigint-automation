package pages.blocks.sidebar.menus;

import com.codeborne.selenide.SelenideElement;
import pages.SigintPage;
import pages.blocks.sidebar.MainMenu;

public class ReportsMenu extends MainMenu {

    public ReportsMenu(SelenideElement menuElement) {
        super(menuElement);
    }

    @Override
    protected  SigintPage getPage(String pageName) {
        return null;
    }


}
