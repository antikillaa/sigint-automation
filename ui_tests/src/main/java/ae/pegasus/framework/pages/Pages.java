package ae.pegasus.framework.pages;

import ae.pegasus.framework.pages.login.LoginPage;
import ae.pegasus.framework.pages.records.RecordDetailsDialog;
import ae.pegasus.framework.pages.records.RecordsAllPage;
import ae.pegasus.framework.pages.records.RecordsCreatePage;
import ae.pegasus.framework.pages.records.RecordsProcessedPage;
import ae.pegasus.framework.pages.reports.*;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.page;

public class Pages {

    public static SigintPage sigintPage() {
        return Selenide.page(SigintPage.class);
    }

    public static LoginPage loginPage() {
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

    public static ReportDetailsDialog reportDetailsDialog() {
        return page(ReportDetailsDialog.class);
    }

}
