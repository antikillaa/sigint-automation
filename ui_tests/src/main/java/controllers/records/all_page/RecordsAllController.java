package controllers.records.all_page;

import blocks.context.factories.RecordsControllerFactory;
import controllers.records.RecordsToolbarController;
import pages.Pages;
import pages.records.RecordsAllPage;

public  class RecordsAllController implements RecordsControllerFactory {

    private static RecordsAllPage page = Pages.recordsAllPage();

    @Override
    public RecordsAllPage getPage() {
        return page;
    }

    @Override
    public RecordsTableAllController getTableController() {
        return new RecordsTableAllController(page);
    }

    @Override
    public RecordsToolbarController getToolbarController() {
        return new RecordsToolbarController(page);
    }

    @Override
    public RecordsAllSearchController getSearchController() {
        return new RecordsAllSearchController(page);
    }
}
