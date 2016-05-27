package pages.blocks.sidebar.menus;

import com.codeborne.selenide.SelenideElement;
import pages.SigintPage;
import pages.blocks.sidebar.MainMenu;
import pages.records.RecordsProcessedPage;
import pages.records.RecordsSearchPage;

public class  RecordsMenu extends MainMenu {

    public RecordsMenu(SelenideElement menuElement) {
        super(menuElement);
    }

    @Override
    protected SigintPage getPage(String pageName) {
        if (pageName.equalsIgnoreCase("search")) {
            return new RecordsSearchPage();
        } else if (pageName.equalsIgnoreCase("processing")) {
            return new RecordsProcessedPage();
        }
        throw new AssertionError(String.format("Page %s is not found!", pageName));


    }
}
