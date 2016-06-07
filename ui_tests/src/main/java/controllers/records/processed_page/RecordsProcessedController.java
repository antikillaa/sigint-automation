package controllers.records.processed_page;

import controllers.PageController;
import controllers.PageControllerFactory;
import controllers.TableController;
import controllers.TableToolbarController;

public class RecordsProcessedController implements PageControllerFactory {


    @Override
    public TableController getTableController() {
        return null;
    }

    @Override
    public TableToolbarController getToolbarController() {
        return null;
    }

    @Override
    public PageController getSearchController() {
        return null;
    }
}
