package steps;

import com.codeborne.selenide.SelenideElement;
import model.Record;
import model.Report;
import org.apache.commons.lang.RandomStringUtils;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import pages.blocks.content.header.breadcrumb.Breadcrumb;
import pages.blocks.content.main.table.ReportRow;
import pages.reports.ReportDetailsDialog;
import pages.reports.ReportRecordRow;
import pages.reports.ReportsDraftPage;
import pages.reports.ReportsReadyPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.page;

public class UIReportsSteps extends UISteps {

    @Given("I'm on 'Reports->Ready' page")
    public void iOnReportsReadyPage() {
        String href = page(Breadcrumb.class).getCurrentPath().getAttribute("href");

        if (!href.contentEquals(ReportsReadyPage.url)) {
            pages.reportsReadyPage().load();
        }
    }

    @When("I press 'Submit' button")
    public void pressSubmitButton() {
        pages.reportsCreatePage().getSidebarRightWrapper().clickSubmitButton();
    }

    @When("fill out required fields on 'Create Report' page")
    public void fillOutRequiredFieldsOnCreateReportScreen() {
        Report report = new Report();

        report.setSubject("subject:" + RandomStringUtils.randomAlphabetic(4));
        pages.reportsCreatePage().getSubject().val(report.getSubject());

        context.putToRunContext("report", report);
    }

    @Then("report appears on 'Reports->Draft' page")
    public void reportAppearsOnReportsDraftScreen() {
        Report report = getReportFromContext();

        pages.reportsDraftPage().getHeader().getBreadcrumb().getCurrentPath()
                .shouldHave(attribute("href", ReportsDraftPage.url));

        ReportRow reportRow = pages.reportsDraftPage().getReportsTable().getReportByColumnNameAndValue("Subject", report.getSubject());
        if (reportRow != null) {
            reportRow.getCellByColumnName("Created By").shouldHave(text(user.getName()));
        } else {
            log.warn("Report subject:" + report.getSubject() + " does not found on Reports->Draft page");
            throw new AssertionError("Report subject:" + report.getSubject() + " does not found on Reports->Draft page");
        }
    }

    @Then("report status is '$status' on 'Reports->All' page")
    public void reportStatusIsDraft(String status) {
        Report report = getReportFromContext();

        ReportRow reportRow = pages.reportsAllPage().load().getReportsTable().getReportByColumnNameAndValue("Subject", report.getSubject());
        if (reportRow != null) {
            reportRow.getCellByColumnName("Status").shouldHave(text(status));
        } else {
            log.warn("Report subject:" + report.getSubject() + " does not found on Reports->All page");
            throw new AssertionError("Report subject:" + report.getSubject() + " does not found on Reports->All page");
        }
    }

    @When("I select report, which records should be attached to")
    public void selectReportWhichRecordsShouldBeAttachedTo() {
        Report report = new Report();
        SelenideElement reportElement = pages.recordDetailsDialog().getAttachListElements().first();

        report.setSubject(reportElement.getText());
        reportElement.click();

        context.putToRunContext("report", report);
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

}
