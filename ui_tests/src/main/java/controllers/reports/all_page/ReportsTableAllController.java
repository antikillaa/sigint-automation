package controllers.reports.all_page;

import blocks.context.tables.Row;
import blocks.context.tables.reports.ReportAllRow;
import controllers.reports.ReportsTableController;
import model.Report;
import pages.SigintPage;

public class ReportsTableAllController extends ReportsTableController {

    public ReportsTableAllController(SigintPage page) {
        super(page);
    }

    @Override
    public Report initFromRow(Row reportRow) {
        Report report = super.initFromRow(reportRow);
        ReportAllRow row = (ReportAllRow)reportRow;
        report.setStatus(row.getStatus());
        report.setOwner(row.getOwner());
        return report;
    }


}
