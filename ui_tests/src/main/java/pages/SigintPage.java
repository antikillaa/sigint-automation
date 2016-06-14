package pages;

import blocks.common.header.Header;
import blocks.common.main.Main;
import blocks.navigation.Sidebar;

import static com.codeborne.selenide.Selenide.page;

public abstract class SigintPage extends BasePage implements ContextPage {

    private Header header = page(Header.class);
    private Main main = page(Main.class);
    private Sidebar sidebar = page(Sidebar.class);


    public Header getHeader() {
        return header;
    }

    public Main getMain() {
        return main;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

}
