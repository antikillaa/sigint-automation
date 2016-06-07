package controllers;

import pages.SigintPage;
import pages.reports.ReportSearchPage;

public class ReportSearchController extends PageController<ReportSearchPage> {


    public ReportSearchController(SigintPage page) {
        super(page, ReportSearchPage.class);
    }

    public void findReportBySubject(String subject) {

        //ReportRow reportRow = getPage().getReportsTable().getReportByColumnNameAndValue("Subject", subject);

    }

    //}
}
