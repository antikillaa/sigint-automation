package controllers.reports.all_page;

import blocks.context.factories.ReportsControllerFactory;
import controllers.PageController;
import controllers.reports.ReportsTableController;
import controllers.reports.ReportsToolbarController;
import pages.Pages;
import pages.reports.ReportsAllPage;

public class ReportsAllController implements ReportsControllerFactory {

    ReportsAllPage allPage = Pages.reportsAllPage();

    @Override
    public ReportsAllPage getPage() {
        return allPage;
    }

    @Override
    public ReportsTableController getTableController() {
        return new ReportsTableAllController(allPage);
    }

    @Override
    public ReportsToolbarController getToolbarController() {
        return new ReportsToolbarController(allPage);
    }

    @Override
    public PageController getSearchController() {
        return null;
    }
}
