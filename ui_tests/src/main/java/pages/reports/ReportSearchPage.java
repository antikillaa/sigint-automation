package pages.reports;

import pages.SigintPage;
import blocks.context.tables.reports.ReportsTable;

import static com.codeborne.selenide.Selenide.page;

public abstract class ReportSearchPage extends SigintPage {

    private ReportsTable reportsTable = page(ReportsTable.class);


    public ReportsTable getReportsTable() {
        return reportsTable;
    }
}
