package controllers.reports.draft_page;

import blocks.context.factories.ReportsControllerFactory;
import controllers.PageController;
import controllers.reports.ReportsTableController;
import controllers.reports.ReportsToolbarController;
import pages.Pages;
import pages.reports.ReportsDraftPage;

public class ReportsDraftController implements ReportsControllerFactory {

    ReportsDraftPage page = Pages.reportsDraftPage();

    @Override
    public ReportsDraftPage getPage() {
        return page;
    }

    @Override
    public ReportsTableController getTableController() {
        return new ReportsTableDraftController(page);
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
