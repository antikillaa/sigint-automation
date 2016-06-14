package blocks.context;

import pages.BaseSection;
import blocks.context.tables.EntityTable;
import blocks.context.toolbars.EntityToolbar;
import blocks.context.toolbars.SearchToolbar;

import static com.codeborne.selenide.Selenide.page;

public abstract class Context extends BaseSection {

    public static final String baseSelector = "div.pg-table";

    public ContextPageFactory getPageFactory() {
        return pageFactory;
    }

    public void setPageFactory(ContextPageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    protected ContextPageFactory pageFactory;

    public Context(ContextPageFactory factory) {
        super(baseSelector);
        this.pageFactory = factory;
    }

    public EntityToolbar getToolbar() {return page(pageFactory.createToolBar());}

    public SearchToolbar getSearchPanel() {return page(pageFactory.createSearchPanel());}

    public EntityTable getTable() {return page(pageFactory.createTable());}

}