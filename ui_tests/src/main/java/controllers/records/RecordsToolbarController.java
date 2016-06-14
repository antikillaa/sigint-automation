package controllers.records;

import blocks.context.toolbars.records.panels.RecordsActionPanel;
import blocks.context.toolbars.records.panels.RecordsFilterPanel;
import controllers.ToolbarController;
import controllers.reports.form_page.ReportFormFactoryController;
import pages.SigintPage;

public class RecordsToolbarController<F extends RecordsFilterPanel, A extends RecordsActionPanel>

extends ToolbarController {

    private F filterPanel;
    private A actionPanel;

    public RecordsToolbarController(SigintPage page) {
        super(page);
    }

    @Override
    protected void initFilters() {
        filterPanel = (F)this.getPage().context().getToolbar().getFilters();

    }

    @Override
    protected void initActions() {
        actionPanel = (A)this.getPage().context().getToolbar().getActions();

    }

    public void searchByDate(String date) {
        filterPanel.setDate(date);
        filterPanel.applySearch();
    }

    public void openCreateRecordForm() {
        actionPanel.clickCreateRecord();
        context.put("controller", new RecordAddController());
    }

    public void openCreateReportForm() {
        actionPanel.clickCreateReport();
        context.put("controller", new ReportFormFactoryController());
    }
}
