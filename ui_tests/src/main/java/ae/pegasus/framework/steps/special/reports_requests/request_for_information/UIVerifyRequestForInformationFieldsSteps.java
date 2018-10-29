package ae.pegasus.framework.steps.special.reports_requests.request_for_information;

import ae.pegasus.framework.assertion.Asserter;
import ae.pegasus.framework.utils.ParametersHelper;
import ae.pegasus.framework.constants.special.reports_requests.ReportAndRequestField;
import ae.pegasus.framework.constants.special.reports_requests.request_for_information.RequestForInformationField;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;
import ae.pegasus.framework.pages.Pages;
import ae.pegasus.framework.utils.AssertUtils;

import java.util.Arrays;
import java.util.List;

public class UIVerifyRequestForInformationFieldsSteps {
    private void checkListValue(RequestForInformationField field, List<String> expectedValues, String verifiedParameter) {
        AssertUtils.checkLists(
                Pages.verifyRequestForInformationPage().getListOfValues(field),
                expectedValues,
                verifiedParameter);
    }

    private void checkSingleValue(RequestForInformationField field, String expectedValue, String verifiedParameter) {
        Asserter.getAsserter().softAssertEquals(
                Pages.verifyRequestForInformationPage().getSingleValue(field),
                (expectedValue.isEmpty() ? "-" : expectedValue),
                verifiedParameter);
    }

    private void checkListValue(ReportAndRequestField field, List<String> expectedValues, String verifiedParameter) {
        AssertUtils.checkLists(
                Pages.verifyRequestForInformationPage().getListOfValues(field),
                expectedValues,
                verifiedParameter);
    }

    private void checkSingleValue(ReportAndRequestField field, String expectedValue, String verifiedParameter) {
        Asserter.getAsserter().softAssertEquals(
                Pages.verifyRequestForInformationPage().getSingleValue(field),
                expectedValue,
                verifiedParameter);
    }

    @Then("I should see that currently opened request for information has status ($requestStatus)")
    public void iShouldSeeReportStatus(String requestStatus) {
        Asserter.getAsserter().softAssertEquals(Pages.verifyRequestForInformationPage().getReportOrRequestStatus(),
                requestStatus,
                "Status of currently opened request for information");
    }

    @Then("I should see Classification ($classification) in request for information")
    public void iShouldSeeClassification(String classification) {
        checkSingleValue(ReportAndRequestField.CLASSIFICATION, classification, "Classification in request for information");
    }

    @Then("I should see Priority ($priority) in request for information")
    public void iShouldSeePriority(String priority) {
        checkSingleValue(RequestForInformationField.PRIORITY, priority, "Priority in request for information");
    }

    @Then("I should see File Name/Case Name(s) ($fileOrCaseNames) in request for information")
    public void iShouldSeeFileOrCaseNames(ExamplesTable fileOrCaseNames) {
        checkListValue(RequestForInformationField.FILE_OR_CASE_NAME,
                Arrays.asList(ParametersHelper.processExampleTable(fileOrCaseNames)),
                "File Name/Case Name(s) in request for information");
    }

    @Then("I should see Organization Units ($orgUnits) in request for information")
    public void iShouldSeeOrgUnits(ExamplesTable orgUnits) {
        checkListValue(RequestForInformationField.ORGANIZATION_UNITS,
                Arrays.asList(ParametersHelper.processExampleTable(orgUnits)),
                "Organization Units in request for information");
    }

    @Then("I should see Manual RFI number ($manualRFINumber) in request for information")
    public void iShouldSeeManualRFINumber(String manualRFINumber) {
        checkSingleValue(RequestForInformationField.MANUAL_RFI_NUMBER, manualRFINumber, "Manual RFI number in request for information");
    }

    @Then("I should see RFI Type ($rfiType) in request for information")
    public void iShouldSeeRFIType(String rfiType) {
        checkSingleValue(RequestForInformationField.RFI_TYPE, rfiType, "RFI Type in request for information");
    }

    @Then("I should see Subject ($subject) in request for information")
    public void iShouldSeeSubject(String subject) {
        checkSingleValue(ReportAndRequestField.SUBJECT, subject, "Subject in request for information");
    }

    private String convertToString (ExamplesTable toConvert) {
        return String.join("\n", ParametersHelper.processExampleTable(toConvert));
    }

    @Then("I should see Required ($requiredLines) in request for information")
    public void iShouldSeeRequired(ExamplesTable requiredLines) {
        checkSingleValue(RequestForInformationField.REQUIRED, convertToString(requiredLines), "Required in request for information");
    }

    @Then("I should see Notes ($notesLines) in request for information")
    public void iShouldSeeNotes(ExamplesTable notesLines) {
        checkSingleValue(ReportAndRequestField.NOTES, convertToString(notesLines), "Notes in request for information");
    }

    @Then("I should see Related Files/Cases ($relatedCasesOrFiles) in request for information")
    public void iShouldSeeRelatedCaseOrFile(ExamplesTable relatedCasesOrFiles) {
        checkListValue(ReportAndRequestField.RELATED_CASES_OR_FILES,
                Arrays.asList(ParametersHelper.processExampleTable(relatedCasesOrFiles)),
                "Related Files/Cases in request for information");
    }

    @Then("I should see Related RFIs ($relatedRFIs) in request for information")
    public void iShouldSeeRelatedRFIs(ExamplesTable relatedRFIs) {
        checkListValue(RequestForInformationField.RELATED_RFIS,
                Arrays.asList(ParametersHelper.processExampleTable(relatedRFIs)),
                "Related RFIs in request for information");
    }

    @Then("I should see Related Profiles ($relatedProfiles) in request for information")
    public void iShouldSeeRelatedProfiles(ExamplesTable relatedProfiles) {
        checkListValue(RequestForInformationField.RELATED_PROFILES,
                Arrays.asList(ParametersHelper.processExampleTable(relatedProfiles)),
                "Related Profiles in request for information");
    }
}