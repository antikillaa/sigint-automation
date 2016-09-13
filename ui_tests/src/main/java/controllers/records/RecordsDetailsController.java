package controllers.records;

import controllers.PageController;
import controllers.reports.form_page.ReportFormFactoryController;
import errors.NullReturnException;
import app_context.AppContext;
import org.apache.log4j.Logger;
import pages.Pages;
import pages.records.RecordDetailsDialog;

public class RecordsDetailsController extends PageController<RecordDetailsDialog> {

    private Logger log = Logger.getLogger(RecordsDetailsController.class);


    public RecordsDetailsController() {
        super(Pages.recordDetailsDialog(), RecordDetailsDialog.class);
    }


    public void attachRecordToReport(String reportSubject) {
        getPage().clickAttachButton();
        try {
            getPage().selectReportInAttatchmentsList(reportSubject);
            AppContext.getContext().put("controller", new ReportFormFactoryController());
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("Cannot find report with subject "+ reportSubject);
        }
    }



}
