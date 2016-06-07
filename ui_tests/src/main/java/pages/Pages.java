package pages;

import pages.login.LoginPage;
import pages.records.RecordDetailsDialog;
import pages.records.RecordsProcessedPage;
import pages.records.RecordsAllPage;
import pages.records.RecordsCreatePage;
import pages.reports.*;

import static com.codeborne.selenide.Selenide.page;

public class Pages {

    public LoginPage loginPage() {
        return page(LoginPage.class);
    }

    public static RecordsAllPage recordsAllPage() {
        return page(RecordsAllPage.class);
    }

    public static RecordsCreatePage recordsCreatePage() {
        return page(RecordsCreatePage.class);
    }

    public static RecordsProcessedPage recordsProcessedPage() {
        return page(RecordsProcessedPage.class);
    }

    public static ReportsCreatePage reportsCreatePage() {
        return page(ReportsCreatePage.class);
    }

    public static ReportsDraftPage reportsDraftPage() {
        return page(ReportsDraftPage.class);
    }

    public static ReportsAllPage reportsAllPage() {
        return page(ReportsAllPage.class);
    }

    public static RecordDetailsDialog recordDetailsDialog() {
        return page(RecordDetailsDialog.class);
    }

    public static ReportsReadyPage reportsReadyPage() {
        return page(ReportsReadyPage.class);
    }

    public static ReportsCreateManualPage reportsCreateManualPage() {
        return page(ReportsCreateManualPage.class);
    }

    public static ReportsEditPage reportsEditPage() {
        return page(ReportsEditPage.class);
    }

}
