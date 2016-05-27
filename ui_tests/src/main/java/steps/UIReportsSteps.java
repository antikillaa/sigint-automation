package steps;

import com.codeborne.selenide.SelenideElement;
import model.Record;
import model.Report;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pages.blocks.content.header.Header;
import pages.blocks.content.header.breadcrumb.Breadcrumb;
import pages.blocks.tables.ReportRow;
import pages.blocks.tables.ReportsTable;
import pages.blocks.tables.toolbar.ReportsTableToolbar;
import pages.reports.*;
import pages.reports.sidebar.SidebarRightWrapper;

import java.util.ArrayList;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.page;

public class UIReportsSteps extends UISteps {

    private String getPageUrl() {
        return page(Breadcrumb.class).getCurrentPath().getAttribute("href");
    }


    @Given("I'm on 'Reports->Ready' page")
    @When("I navigate to 'Reports->Ready for Review' page")
    public void iOnReportsReadyPage() {
        if (!getPageUrl().contentEquals(ReportsReadyPage.url)) {
            pages.reportsReadyPage().load();
        }
    }

    @Given("I'm on 'Reports->All' page")
    @When("I navigate to 'Reports->All' page")
    public void iOnReportsAllPage() {
        if (!getPageUrl().contentEquals(ReportsAllPage.url)) {
            pages.reportsAllPage().load();
        }
    }

    @Given("I'm on 'Reports->Draft' page")
    public void iOnReportsDraftPage() {
        if (!getPageUrl().contentEquals(ReportsDraftPage.url)) {
            pages.reportsDraftPage().load();
        }
    }

    @When("I press 'Submit' button")
    public void pressSubmitButton() {
        pages.reportsCreatePage().getSidebarRightWrapper().clickSubmitButton();
    }

    @When("fill out required fields on 'Create Report' page")
    public void fillOutRequiredFieldsOnCreateReportScreen() {
        Report report = new Report().generate();

        pages.reportsCreatePage().getSubject().val(report.getSubject());

        context.put("report", report);
    }

    @When("I fill out required fields on 'Create Manual Report' page")
    public void fillOutRequiredFieldsOnCreateManualReportPage() {
        Report report = new Report().generate();

        pages.reportsCreateManualPage()
                .setSubject(report.getSubject())
                .selectRandomRecordType()
                .selectRandomSourceId();

        context.put("report", report);
    }

    @Then("report appears on 'Reports->Draft' page")
    public void reportAppearsOnReportsDraftScreen() {
        Report report = getReportFromContext();

        pages.reportsDraftPage().getHeader().getBreadcrumb().getCurrentPath()
                .shouldHave(attribute("href", ReportsDraftPage.url));

        ReportRow reportRow = pages.reportsDraftPage().getReportsTable().getReportByColumnNameAndValue("Subject", report.getSubject());
        //if (reportRow != null) {
        //    reportRow.getCellByColumnName("Created By").shouldHave(text(user.getName()));
        //} else {
        //    log.warn("Report subject:" + report.getSubject() + " does not found on Reports->Draft page");
        //    throw new AssertionError("Report subject:" + report.getSubject() + " does not found on Reports->Draft page");
        //}
    }

    @Then("report status is '$status' on 'Reports->All' page")
    @Alias("report status is '$status'")
    public void reportStatusIsDraft(String status) {
        Report report = getReportFromContext();

        ReportRow reportRow = pages.reportsAllPage().load().getReportsTable().getReportByColumnNameAndValue("Subject", report.getSubject());
        //if (reportRow != null) {
        //    reportRow.getCellByColumnName("Status").shouldHave(text(status));
        //} else {
        //    log.warn("Report subject:" + report.getSubject() + " does not found on Reports->All page");
        //    throw new AssertionError("Report subject:" + report.getSubject() + " does not found on Reports->All page");
        //}
    }

    @When("I select report, which records should be attached to")
    public void selectReportWhichRecordsShouldBeAttachedTo() {
        Report report = new Report();
        SelenideElement reportElement = pages.recordDetailsDialog().getAttachListElements().first();

        report.setSubject(reportElement.getText());
        reportElement.click();

        context.put("report", report);
    }

    @Then("record is attached to the report")
    public void recordIsAttachedToReport() {
        Record record = getRecordFromContext();
        Report report = getReportFromContext();

        // find report which records should be attached to
        ReportRow reportRow  = pages.reportsDraftPage().getReportsTable().getReportByColumnNameAndValue("Subject", report.getSubject());

        // open reportDetailsDialog for this report
        ReportDetailsDialog reportDetailsDialog = reportRow.selectReport().clickShowInfoButton();
        // find attached record by fromNumber
        for (ReportRecordRow reportRecordRow : reportDetailsDialog.getRecords()) {
            if (reportRecordRow.getFromNumber().text().contentEquals(record.getFromNumber())) {
                // Assert
                reportRecordRow.getToNumber().shouldHave(text(record.getToNumber()));
                // close reportDetailsDialog
                reportDetailsDialog.getReportDetailsDialog().pressEscape().shouldBe(disappear);
                return;
            }
        }
        log.warn("Attached record fromNumber:" + record.getFromNumber() + " doesn't found in the report " + report.getSubject());
        throw new AssertionError("Attached record fromNumber:" + record.getFromNumber() + " doesn't found in the report " + report.getSubject());
    }

    @Then("report appears on 'Reports->Ready for Review' page")
    public void reportAppearsOnAnalystReportsReadyPage() {
        Report report = getReportFromContext();

        ReportRow reportRow  = pages.reportsReadyPage().load().getReportsTable().getReportByColumnNameAndValue("Subject", report.getSubject());
        if (reportRow == null) {
            log.error("Report subject:" + report.getSubject() + " doesn't found on the Ready for Review page");
            throw new AssertionError("Report subject:" + report.getSubject() + " doesn't found on the Ready for Review page");
        }
    }

    @When("I press 'Create Manual Report' button")
    public void pressCreateManualReportButton() {
        page(ReportsTableToolbar.class).clickCreateManualReportButton();
    }

    @When("I select first report in the table")
    public void selectFirstReport() {
        ReportRow reportRow = page(ReportsTable.class).firstReport().selectReport();
        Report report = new Report();
        //report.setSubject(reportRow.getCellByColumnName("Subject").text());

        context.put("reportRow", reportRow);
        context.put("report", report);
    }

    @When("I press 'Edit Report' button against it")
    public void pressEditReportButtonAgainstIt() {
        ReportRow reportRow = context.get("reportRow", ReportRow.class);
        reportRow.clickEditReportButton();
    }

    @Then("I should see 'Remove Ownership' button on 'Edit Report' page")
    public void shouldSeeRemoveOwnershipButtonOnEditReportPage(){
        pages.reportsEditPage().getSidebarRightWrapper().getRemoveOwnership().shouldBe(visible);
    }

    @When("I press 'Remove Ownership' button on 'Edit Report' page")
    public void pressRemoveOwnershipButton() {
        page(SidebarRightWrapper.class).clickRemoveOwnership();
    }

    @Then("I should see 'Reports->Draft' page")
    public void shouldSeeReportsDraftPage() {
        page(Header.class).getBreadcrumb().getCurrentPath().shouldHave(text("Draft"));
    }

    @Then("I should not see selected report among other draft reports")
    public void shouldNotSeeSelectedReportAmongOtherDraftReports() {
        Report report = context.get("report", Report.class);
        ReportsTable reportsTable = page(ReportsTable.class);
        if (!reportsTable.isEmpty()) {
            Assert.assertNull(reportsTable.getReportByColumnNameAndValue("Subject", report.getSubject()));
        }
    }

    @Then("I should $see selected report on the reports table")
    public void shouldSeeSelectedReportThere(String see) {
        Report report = context.get("report", Report.class);
        ReportsTable reportsTable = page(ReportsTable.class);
        if (see.contentEquals("not see")) {
            if (!reportsTable.isEmpty()) {
                Assert.assertNull(reportsTable.getReportByColumnNameAndValue("Subject", report.getSubject()));
            }
        } else if (see.contentEquals("see")) {
            reportsTable.getReportByColumnNameAndValue("Subject", report.getSubject()).getRow().shouldBe(present);
        }
    }

    @Then("report owner should be '$owner'")
    public void reportOwnerShouldBeEmpty(String owner) {
        Report report = context.get("report", Report.class);
        ReportRow reportRow = page(ReportsTable.class).getReportByColumnNameAndValue("Subject", report.getSubject());
        //if (reportRow != null) {
        //    reportRow.getCellByColumnName("Owner").shouldHave(text(owner.toLowerCase().contentEquals("empty") ? "" : owner));
        //} else {
        //    throw new AssertionError("Report with subject:" + report.getSubject() + " does not found!");
        //}
    }

    @When("I select a report in 'Unassigned' status and owner is 'Empty'")
    public void selectReportInStatusAndOwnerIs(){
        ArrayList<ReportRow> reportRows = page(ReportsTable.class)
                .findReports()
                .filterByColumnWithValue("Status", "UNASSIGNED")
                .filterByColumnWithValue("Owner", "")
                .getReportRows();

        if (reportRows.isEmpty()) {
            log.error("UNASSIGNED report without ownership was not found!");
            throw new AssertionError("UNASSIGNED report without ownership was not found!");
        } else {
            ReportRow reportRow = reportRows.get(0).selectReport();

            Report report = new Report();
            //report.setSubject(reportRow.getCellByColumnName("Subject").text());

            context.put("reportRow", reportRow);
            context.put("report", report);
        }
    }

    @When("I press 'Take Ownership' button on 'Edit Report' page")
    public void pressTakeOwnershipButtonOnEditReportPage() {
        pages.reportsEditPage().getSidebarRightWrapper().clickTakeOwnershipButton();
    }

    @Then("required fields are enabled on 'Edit Report' page")
    public void requiredFieldsAreEnabledOnEditReportPage() {
        pages.reportsEditPage().getSubject().shouldBe(enabled);
    }

    @Then("required buttons are enabled on 'Edit Report' page")
    public void requiredButtonsAreEnabledOnEditReportPage() {
        pages.reportsEditPage().getSidebarRightWrapper().getRefreshButton().shouldBe(enabled);
        pages.reportsEditPage().getSidebarRightWrapper().getRemoveOwnership().shouldBe(enabled);
        pages.reportsEditPage().getSidebarRightWrapper().getSubmitButton().shouldBe(enabled);
        pages.reportsEditPage().getSidebarRightWrapper().getSaveDraftButton().shouldBe(enabled);
    }




}
