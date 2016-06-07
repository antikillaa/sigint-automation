package controllers.records.all_page;

import controllers.PageControllerFactory;
import pages.Pages;
import pages.records.RecordsAllPage;

public  class RecordsAllController implements
        PageControllerFactory<RecordsTableAllController, RecordsAllToolbarController, RecordsAllSearchController > {

    private RecordsAllPage page = Pages.recordsAllPage();

    @Override
    public RecordsTableAllController getTableController() {
        return new RecordsTableAllController(page);
    }

    @Override
    public RecordsAllToolbarController getToolbarController() {
        return new RecordsAllToolbarController(page);
    }

    @Override
    public RecordsAllSearchController getSearchController() {
        return null;
    }
}
