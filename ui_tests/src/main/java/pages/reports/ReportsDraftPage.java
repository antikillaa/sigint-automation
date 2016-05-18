package pages.reports;

import model.AppContext;
import pages.SigintPage;
import pages.blocks.content.main.table.ReportsTable;

import static com.codeborne.selenide.Selenide.page;

public class ReportsDraftPage extends SigintPage {

    public static final String url = String.format("%s/#/app/reports/draft",
            AppContext.getContext().environment().getSigintHost());

    private ReportsTable reportsTable = page(ReportsTable.class);


    public ReportsTable getReportsTable() {
        return reportsTable;
    }

}
