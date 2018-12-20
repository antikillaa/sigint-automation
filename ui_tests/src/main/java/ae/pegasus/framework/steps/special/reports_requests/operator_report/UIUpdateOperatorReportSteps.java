package ae.pegasus.framework.steps.special.reports_requests.operator_report;

import ae.pegasus.framework.utils.ParametersHelper;
import ae.pegasus.framework.constants.special.modal_dialog.ModalDialogButton;
import ae.pegasus.framework.constants.special.reports_requests.ReportAndRequestAction;
import ae.pegasus.framework.constants.special.reports_requests.ReportAndRequestField;
import ae.pegasus.framework.constants.special.reports_requests.operator_report.OperatorMasterReportField;
import ae.pegasus.framework.context.Context;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import ae.pegasus.framework.pages.Pages;

import static ae.pegasus.framework.constants.special.modal_dialog.ModalDialogButton.CANCEL;
import static ae.pegasus.framework.constants.special.modal_dialog.ModalDialogButton.OK;
import static ae.pegasus.framework.constants.special.modal_dialog.ModalDialogField.COMMENTS;

public class UIUpdateOperatorReportSteps {
    @Given("I save Report number for the operator report in the context")
    public void iSaveRequestNumber() {
        Context.getContext().setReportNumber(Pages.updateOperatorReportPage().getReportOrRequestNumber());
    }

    @When("I set Classification ($classification) in operator report")
    public void iSetClassification(String classification) {
        Pages.updateOperatorReportPage().setSingleValueData(ReportAndRequestField.CLASSIFICATION, classification);
    }

    @When("I set Request Number ($requestNumber) in operator report")
    public void iSetRequestNumber(String requestNumber) {
        Pages.updateOperatorReportPage().setSingleValueData(OperatorMasterReportField.REQUEST_NUMBER, requestNumber);
    }

    @When("I set Created For ($createdFor) in operator report")
    public void iSetCreatedFor(String createdFor) {
        Pages.updateOperatorReportPage().setSingleValueData(OperatorMasterReportField.CREATED_FOR, createdFor);
    }

    @When("I set File Name/Case Name ($fileOrCaseNames) in operator report")
    public void iSetFileOrCaseName(ExamplesTable fileOrCaseNames) {
        Pages.updateOperatorReportPage().setMultiValueData(OperatorMasterReportField.FILE_OR_CASE_NAME, ParametersHelper.processExampleTable(fileOrCaseNames));
    }

    @When("I set Organization Units ($orgUnits) in operator report")
    public void iSetOrgUnits(ExamplesTable orgUnits) {
        Pages.updateOperatorReportPage().setMultiValueData(OperatorMasterReportField.ORGANIZATION_UNITS, ParametersHelper.processExampleTable(orgUnits));
    }

    @When("I set Subject ($subject) in operator report")
    public void iSetSubject(String subject) {
        Pages.updateOperatorReportPage().setSingleValueData(ReportAndRequestField.SUBJECT, subject);
    }

    @When("I set Description ($descriptionLines) in operator report")
    public void iSetDescription(ExamplesTable descriptionLines) {
        Pages.updateOperatorReportPage().setMultiValueData(OperatorMasterReportField.DESCRIPTION, ParametersHelper.processExampleTable(descriptionLines));
    }

    @When("I set Considerations ($considerationLines) in operator report")
    public void iSetConsiderations(ExamplesTable considerationLines) {
        Pages.updateOperatorReportPage().setMultiValueData(OperatorMasterReportField.CONSIDERATIONS, ParametersHelper.processExampleTable(considerationLines));
    }

    @When("I set Recommendations ($recommendationLines) in operator report")
    public void iSetRecommendations(ExamplesTable recommendationLines) {
        Pages.updateOperatorReportPage().setMultiValueData(OperatorMasterReportField.RECOMMENDATIONS, ParametersHelper.processExampleTable(recommendationLines));
    }

    @When("I set Notes ($notesLines) in operator report")
    public void iSetNotes(ExamplesTable notesLines) {
        Pages.updateOperatorReportPage().setMultiValueData(ReportAndRequestField.NOTES, ParametersHelper.processExampleTable(notesLines));
    }

    @When("I set Related Files/Cases ($relatedCasesOrFiles) in operator report")
    public void iSetRelatedCaseOrFile(ExamplesTable relatedCasesOrFiles) {
        Pages.updateOperatorReportPage().setMultiValueData(ReportAndRequestField.RELATED_CASES_OR_FILES, ParametersHelper.processExampleTable(relatedCasesOrFiles));
    }

    @When("I save currently opened operator report as draft")
    public void iSaveReportAsDraft() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.SAVE_AS_DRAFT);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Pages.updateOperatorReportPage().waitForPageLoading();
       // Pages.searchResultDetailsPage().closeDialog();
    }

    @Then("I close the operator report")
    public void iCloseReport() {
         Pages.searchResultDetailsPage().closeDialog();
}

    @When("I delete operator report which is currently opened")
    public void iDeleteReport() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.DELETE_REPORT);
        Pages.modalDialogPage().clickButton(ModalDialogButton.YES);
        Pages.updateOperatorReportPage().waitForPageLoading();
    }

    @When("I start edit of operator report which is currently opened")
    public void iStartEditReport() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.EDIT);
        Pages.updateOperatorReportPage().waitForPageLoading();
    }

    @When("I Submit For Review the operator report which is currently opened")
    public void iSubmitForReviewReport() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.SUBMIT_FOR_REVIEW);
        Pages.updateOperatorReportPage().waitForPageLoading();

    }
    @When("I Take Ownership of the operator report which is currently opened")
    public void iTakeOwnershipOfReport() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.TAKE_OWNERSHIP);
        Pages.updateOperatorReportPage().waitForPageLoading();
    }

    @When("I Unassign the operator report which is currently opened")
    public void iUnassignTheReport() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.UNASSIGN);
    }

    @When("I Return to Author the operator report which is currently opened")
    public void iReturnReport() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.RETURN_TO_AUTHOR);
    }


    @When("I Approve the operator report which is currently opened")
    public void iApproveReport() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.APPROVE);
        Pages.updateOperatorReportPage().waitForPageLoading();
    }

    @When("I Cancel the operator report which is currently opened")
    public void iCancelReport() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.CANCEL);
    }


    @When("I Reject the operator report which is currently opened")
    public void iRejectReport() {
        Pages.updateOperatorReportPage().performAction(ReportAndRequestAction.REJECT);
    }


    @When("I enter routing ($comment) for the operator report")
    public void iEnterRoutingComment(String comment) {
        Pages.modalDialogPage().setDataToField(COMMENTS, comment);
    }

    @When("I enter submititng routing ($comment) for the operator report")
    public void iEnterSubmittingRoutingComment(String comment) {
        Pages.modalDialogPage().setDataToField(COMMENTS, comment);
    }

    @When("I route the operator report")
    public void iRouteReport() {
        Pages.modalDialogPage().clickButton(OK);
        Pages.updateOperatorReportPage().waitForPageLoading();
    }

    @When("I cancel the routing of the operator report")
    public void iCancelReportRouting() {
        Pages.modalDialogPage().clickButton(CANCEL);
    }
}

