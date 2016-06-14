package controllers.reports.form_page;

import controllers.reports.ReportsFormController;
import pages.Pages;
import pages.reports.ReportsEditPage;

import static com.codeborne.selenide.Condition.enabled;

public class ReportDetailsController extends ReportsFormController<ReportsEditPage> {
    public ReportDetailsController() {
        super(Pages.reportsEditPage(), ReportsEditPage.class);
    }


    public boolean isEnabled() {
        try {
            getPage().getSubject().shouldBe(enabled);
            getPage().getActionBar().getRefreshButton().shouldBe(enabled);
            getPage().getActionBar().getSaveDraftButton().shouldBe(enabled);
            getPage().getActionBar().getSubmitButton().shouldBe(enabled);
            getPage().getActionBar().getRemoveOwnership().shouldBe(enabled);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
