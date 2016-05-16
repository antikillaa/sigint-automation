package pages;

import pages.login.LoginPage;
import pages.records.RecordDetailsDialog;
import pages.records.RecordsProcessedPage;
import pages.records.RecordsSearchPage;
import pages.records.RecordsCreatePage;
import pages.reports.ReportsAllPage;
import pages.reports.ReportsCreatePage;
import pages.reports.ReportsDraftPage;
import pages.reports.ReportsReadyPage;

import static com.codeborne.selenide.Selenide.page;

public class Pages {

    public LoginPage loginPage() {
        return page(LoginPage.class);
    }

    public RecordsSearchPage recordsSearchPage() {
        return page(RecordsSearchPage.class);
    }

    public RecordsCreatePage recordsCreatePage() {
        return page(RecordsCreatePage.class);
    }

    public RecordsProcessedPage recordsProcessedPage() {
        return page(RecordsProcessedPage.class);
    }

    public ReportsCreatePage reportsCreatePage() {
        return page(ReportsCreatePage.class);
    }

    public ReportsDraftPage reportsDraftPage() {
        return page(ReportsDraftPage.class);
    }

    public ReportsAllPage reportsAllPage() {
        return page(ReportsAllPage.class);
    }

    public RecordDetailsDialog recordDetailsDialog() {
        return page(RecordDetailsDialog.class);
    }

    public ReportsReadyPage reportsReadyPage() {
        return page(ReportsReadyPage.class);
    }
}
