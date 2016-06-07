package controllers.records.all_page;

import blocks.context.toolbars.records.panels.RecordsActionPanel;
import blocks.context.toolbars.records.panels.RecordsFilterPanel;
import controllers.TableToolbarController;
import pages.SigintPage;
import pages.records.RecordsCreatePage;
import pages.reports.ReportsCreateManualPage;

import static com.codeborne.selenide.Selenide.page;

public class RecordsAllToolbarController extends
        TableToolbarController<RecordsFilterPanel, RecordsActionPanel> {


    public RecordsAllToolbarController(SigintPage page) {
        super(page, RecordsFilterPanel.class, RecordsActionPanel.class);

    }

    public void searchByDate(String date) {
        filterPanel.setDate(date);
        filterPanel.applySearch();
    }

    public void openCreateRecordForm() {
        actionPanel.clickCreateRecord();
        context.put("page", page(RecordsCreatePage.class) );
    }

    public void openCreateReportForm() {
        actionPanel.clickCreateReport();
        context.put("page", page(ReportsCreateManualPage.class));
    }

}
