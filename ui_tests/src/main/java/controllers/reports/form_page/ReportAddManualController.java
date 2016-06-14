package controllers.reports.form_page;

import controllers.reports.ReportsFormController;
import model.Report;
import pages.Pages;
import pages.reports.ReportsCreateManualPage;

public class ReportAddManualController extends ReportsFormController<ReportsCreateManualPage> {


    public ReportAddManualController() {
        super(Pages.reportsCreateManualPage(), ReportsCreateManualPage.class);
    }

    public Report fillForm(Report report) {
        getPage()
                .selectRecordType(report.getRecordType())
                .selectSourceType(report.getSourceType());
        super.fillForm(report);
        return report;
    }
}
