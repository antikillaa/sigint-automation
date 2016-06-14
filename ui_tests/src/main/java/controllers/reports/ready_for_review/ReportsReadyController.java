package controllers.reports.ready_for_review;

import blocks.context.factories.ReportsControllerFactory;
import controllers.PageController;
import controllers.reports.ReportsTableController;
import controllers.reports.ReportsToolbarController;
import pages.Pages;
import pages.reports.ReportsReadyPage;

public class ReportsReadyController implements ReportsControllerFactory {

    ReportsReadyPage page = Pages.reportsReadyPage();

    @Override
    public ReportsReadyPage getPage() {
        return page;
    }

    @Override
    public ReportsTableController getTableController() {
        return new ReportsTableController(page);
    }

    @Override
    public ReportsToolbarController getToolbarController() {
        return new ReportsToolbarController(page);
    }

    @Override
    public PageController getSearchController() {
        return null;
    }
}
