package controllers.reports.draft_page;

import blocks.context.tables.Row;
import controllers.reports.ReportsTableController;
import model.Report;
import pages.SigintPage;

public class ReportsTableDraftController extends ReportsTableController {


    public ReportsTableDraftController(SigintPage page) {
        super(page);
    }

    @Override
    public Report initFromRow(Row reportRow) {
        return super.initFromRow(reportRow);
    }
}
