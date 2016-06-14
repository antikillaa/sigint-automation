package controllers.records.processed_page;

import blocks.context.factories.RecordsControllerFactory;
import pages.Pages;
import pages.records.RecordsProcessedPage;

public class RecordsProcessedController implements RecordsControllerFactory
         {

    private RecordsProcessedPage page = Pages.recordsProcessedPage();

             @Override
             public RecordsProcessedPage getPage() {
                 return page;
             }

             @Override
    public RecordsTableProcessedController getTableController() {
        return new RecordsTableProcessedController(page);
    }

    @Override
    public RecordsProcessedToolbarController getToolbarController() {
        return new RecordsProcessedToolbarController(page);
    }

    @Override
    public RecordsProcessedSearchController getSearchController() {
        return new RecordsProcessedSearchController(page);
    }
}
