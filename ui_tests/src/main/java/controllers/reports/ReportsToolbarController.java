package controllers.reports;

import blocks.context.toolbars.reports.panels.ReportsActionPanel;
import controllers.ToolbarController;
import controllers.reports.form_page.ReportFormFactoryController;
import pages.SigintPage;

public class ReportsToolbarController extends ToolbarController {


    private ReportsActionPanel actionPanel;

    public ReportsToolbarController(SigintPage page) {
        super(page);
    }

    @Override
    protected void initFilters() {

    }
    @Override
    protected void initActions() {
        actionPanel = (ReportsActionPanel)getPage().context().getToolbar().getActions();
    }

    public void openCreateReportForm() {
        actionPanel.clickCreateManualReportBtn();
        context.put("controller", new ReportFormFactoryController());
    }

}
