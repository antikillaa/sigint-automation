package ae.pegasus.framework.steps.special.reports_requests.master_report;

import ae.pegasus.framework.constants.special.modal_dialog.ModalDialogButton;
import ae.pegasus.framework.constants.special.reports_requests.ReportAndRequestAction;
import ae.pegasus.framework.constants.special.reports_requests.ReportAndRequestField;
import ae.pegasus.framework.constants.special.reports_requests.operator_report.OperatorMasterReportField;
import ae.pegasus.framework.context.Context;
import ae.pegasus.framework.pages.Pages;
import ae.pegasus.framework.utils.ParametersHelper;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import static ae.pegasus.framework.constants.special.modal_dialog.ModalDialogButton.CANCEL;
import static ae.pegasus.framework.constants.special.modal_dialog.ModalDialogButton.OK;
import static ae.pegasus.framework.constants.special.modal_dialog.ModalDialogField.COMMENTS;

public class UIUpdateMasterReportSteps {
    @Given("I save Report number for the master report in the context")
    public void iSaveRequestNumber() {
        Context.getContext().setReportNumber(Pages.updateMasterrReportPage().getReportOrRequestNumber());
    }

    @When("I set Classification ($classification) in master report")
    public void iSetClassification(String classification) {
        Pages.updateMasterrReportPage().setSingleValueData(ReportAndRequestField.CLASSIFICATION, classification);
    }

    @When("I set Request Number ($requestNumber) in master report")
    public void iSetRequestNumber(String requestNumber) {
        Pages.updateMasterrReportPage().setSingleValueData(OperatorMasterReportField.REQUEST_NUMBER, requestNumber);
    }

    @When("I set Created For ($createdFor) in master report")
    public void iSetCreatedFor(String createdFor) {
        Pages.updateMasterrReportPage().setSingleValueData(OperatorMasterReportField.CREATED_FOR, createdFor);
    }

    @When("I set File Name/Case Name ($fileOrCaseNames) in master report")
    public void iSetFileOrCaseName(ExamplesTable fileOrCaseNames) {
        Pages.updateMasterrReportPage().setMultiValueData(OperatorMasterReportField.FILE_OR_CASE_NAME, ParametersHelper.processExampleTable(fileOrCaseNames));
    }

    @When("I set Organization Units ($orgUnits) in master report")
    public void iSetOrgUnits(ExamplesTable orgUnits) {
        Pages.updateMasterrReportPage().setMultiValueData(OperatorMasterReportField.ORGANIZATION_UNITS, ParametersHelper.processExampleTable(orgUnits));
    }

    @When("I set Subject ($subject) in master report")
    public void iSetSubject(String subject) {
        Pages.updateMasterrReportPage().setSingleValueData(ReportAndRequestField.SUBJECT, subject);
    }

    @When("I set Overview ($overviewLines) in master report")
    public void iSetOverview(ExamplesTable overviewLines) {
        Pages.updateMasterrReportPage().setMultiValueData(OperatorMasterReportField.OVERVIEW, ParametersHelper.processExampleTable(overviewLines));
    }

    @When("I set Considerations ($considerationLines) in master report")
    public void iSetConsiderations(ExamplesTable considerationLines) {
        Pages.updateMasterrReportPage().setMultiValueData(OperatorMasterReportField.CONSIDERATIONS, ParametersHelper.processExampleTable(considerationLines));
    }

    @When("I set Recommendations ($recommendationLines) in master report")
    public void iSetRecommendations(ExamplesTable recommendationLines) {
        Pages.updateMasterrReportPage().setMultiValueData(OperatorMasterReportField.RECOMMENDATIONS, ParametersHelper.processExampleTable(recommendationLines));
    }

    @When("I set Result ($resultLines) in master report")
    public void iSetResult(ExamplesTable resultLines) {
        Pages.updateMasterrReportPage().setMultiValueData(OperatorMasterReportField.RESULT, ParametersHelper.processExampleTable(resultLines));
    }

    @When("I set Related Files/Cases ($relatedCasesOrFiles) in master report")
    public void iSetRelatedCaseOrFile(ExamplesTable relatedCasesOrFiles) {
        Pages.updateMasterrReportPage().setMultiValueData(ReportAndRequestField.RELATED_CASES_OR_FILES, ParametersHelper.processExampleTable(relatedCasesOrFiles));
    }

    @When("I save currently opened master report as draft")
    public void iSaveReportAsDraft() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.SAVE_AS_DRAFT);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Pages.updateMasterReportPage().waitForPageLoading();
       // Pages.searchResultDetailsPage().closeDialog();
    }

    @Then("I close the master report")
    public void iCloseReport() {
         Pages.searchResultDetailsPage().closeDialog();
}

    @When("I delete master report which is currently opened")
    public void iDeleteReport() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.DELETE_REPORT);
        Pages.modalDialogPage().clickButton(ModalDialogButton.YES);
        Pages.updateMasterrReportPage().waitForPageLoading();
    }

    @When("I start edit of master report which is currently opened")
    public void iStartEditReport() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.EDIT);
        Pages.updateMasterrReportPage().waitForPageLoading();
    }

    @When("I Submit For Review the master report which is currently opened")
    public void iSubmitForReviewReport() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.SUBMIT_FOR_REVIEW);
        Pages.updateMasterrReportPage().waitForPageLoading();

    }
    @When("I Take Ownership of the master report which is currently opened")
    public void iTakeOwnershipOfReport() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.TAKE_OWNERSHIP);
        Pages.updateMasterrReportPage().waitForPageLoading();
    }

    @When("I Unassign the master report which is currently opened")
    public void iUnassignTheReport() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.UNASSIGN);
    }

    @When("I Return to Author the master report which is currently opened")
    public void iReturnReport() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.RETURN_TO_AUTHOR);
    }


    @When("I Approve the master report which is currently opened")
    public void iApproveReport() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.APPROVE);
        Pages.updateMasterrReportPage().waitForPageLoading();
    }

    @When("I Cancel the master report which is currently opened")
    public void iCancelReport() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.CANCEL);
    }


    @When("I Reject the master report which is currently opened")
    public void iRejectReport() {
        Pages.updateMasterrReportPage().performAction(ReportAndRequestAction.REJECT);
    }


    @When("I enter routing ($comment) for the master report")
    public void iEnterRoutingComment(String comment) {
        Pages.modalDialogPage().setDataToField(COMMENTS, comment);
    }

    @When("I enter submititng routing ($comment) for the master report")
    public void iEnterSubmittingRoutingComment(String comment) {
        Pages.modalDialogPage().setDataToField(COMMENTS, comment);
    }

    @When("I route the master report")
    public void iRouteReport() {
        Pages.modalDialogPage().clickButton(OK);
        Pages.updateMasterrReportPage().waitForPageLoading();
    }

    @When("I cancel the routing of the master report")
    public void iCancelReportRouting() {
        Pages.modalDialogPage().clickButton(CANCEL);
    }
}

