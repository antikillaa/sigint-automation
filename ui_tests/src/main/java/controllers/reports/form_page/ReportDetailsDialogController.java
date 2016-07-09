package controllers.reports.form_page;

import controllers.PageController;
import pages.Pages;
import pages.reports.ReportDetailsDialog;
import pages.reports.ReportRecordRow;

import java.util.List;

public class ReportDetailsDialogController extends PageController<ReportDetailsDialog> {
    public ReportDetailsDialogController() {
        super(Pages.reportDetailsDialog(), ReportDetailsDialog.class);
    }

    public Boolean isRecordAttached(String text) {
        List<ReportRecordRow> records = getPage().getRecords();
        for (ReportRecordRow row: records) {
            if (row.getDetails().equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }
}
