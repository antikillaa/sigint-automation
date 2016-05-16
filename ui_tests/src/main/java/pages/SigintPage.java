package pages;

import pages.blocks.content.header.Header;
import pages.blocks.content.main.Main;
import pages.blocks.sidebar.Sidebar;

import static com.codeborne.selenide.Selenide.page;

public abstract class SigintPage extends BasePage {

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
