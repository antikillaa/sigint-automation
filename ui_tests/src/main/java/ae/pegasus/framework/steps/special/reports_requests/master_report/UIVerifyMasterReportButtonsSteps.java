package ae.pegasus.framework.steps.special.reports_requests.master_report;

import ae.pegasus.framework.assertion.Asserter;
import ae.pegasus.framework.constants.special.reports_requests.ReportAndRequestAction;
import ae.pegasus.framework.pages.Pages;
import org.jbehave.core.annotations.Then;


public class UIVerifyMasterReportButtonsSteps {
    private void checkReportActionButtonIsNotPresent(ReportAndRequestAction action) {
        Asserter.getAsserter().softAssertFalse(
                Pages.verifyMasterReportPage().checkVisibilityOfReportAction(action),
                "Report action button is not present",
                "Report action button is present without permission ");
    }

    private void checkReportActionButtonVisibleAndEnable(ReportAndRequestAction action) {
        Asserter.getAsserter().softAssertTrue(
                Pages.verifyMasterReportPage().checkVisibilityOfReportAction(action),
                "Report action button is present and enabled",
                "Report action button is not enable or not present");
    }

    private void checkVisibilityOnReportCreation(ReportAndRequestAction action) {
        Asserter.getAsserter().softAssertTrue(
                Pages.verifyMasterReportPage().checkVisibilityOnReportCreation(action),
                "Report action button is present and enabled",
                "Report action button is not enable or not present");
    }

    @Then("I should see Cancel button in master report")
    public void iShouldSeeCancelButton() {
        checkReportActionButtonVisibleAndEnable(ReportAndRequestAction.CANCEL);
    }

    @Then("I should see Submit for Review button")
    public void iSeeSubmitForReviewButton() {
        checkVisibilityOnReportCreation(ReportAndRequestAction.SUBMIT_FOR_REVIEW);
    }

    @Then("I should see Reject button in master report")
    public void iShouldSeeRejectButton() {
        checkReportActionButtonVisibleAndEnable(ReportAndRequestAction.REJECT);
    }

    @Then("I should see Return to Author button in master report")
    public void iShouldSeeReturnToAuthorButton() {
        checkReportActionButtonVisibleAndEnable(ReportAndRequestAction.RETURN_TO_AUTHOR);
    }

    @Then("I should see Approve button in master report")
    public void iShouldSeeApproveButton() {
        checkReportActionButtonVisibleAndEnable(ReportAndRequestAction.APPROVE);
    }

    @Then("I should see Unassign button in master report")
    public void iShouldSeeUnassignButton() {
        checkReportActionButtonVisibleAndEnable(ReportAndRequestAction.UNASSIGN);
    }

    @Then("I should see Edit button in master report")
    public void iShouldSeeEditButton() {
        checkReportActionButtonVisibleAndEnable(ReportAndRequestAction.EDIT);
    }

}
