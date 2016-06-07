package controllers;

import model.AppContext;
import model.Report;
import pages.Pages;
import pages.reports.ReportsCreatePage;

public class ReportAddController  {

    public ReportsCreatePage getPage() {
        return AppContext.getContext().get("page", ReportsCreatePage.class);
    }


    public Report fillForm(Report report) {

        getPage()
                .setSubject(report.getSubject())
                .selectRecordType(report.getRecordType())
                .selectSourceType(report.getSourceType());
        return report;
    }

    public void saveAsDraft() {
        getPage().getActionBar().clickSaveDraft();
        AppContext.getContext().put("page", Pages.reportsDraftPage());
    }
}
