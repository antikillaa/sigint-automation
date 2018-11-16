package ae.pegasus.framework.steps;

import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.services.ReportService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertEquals;

public class APIReportSteps extends APISteps {

    public static ReportService serviceReport = new ReportService();

    @When("I send create a report request")
    public void sendReportRequest() {
        Report report = new Report();
        Result reportNo = context.get("reportNo", Result.class);
        List<SearchRecord> entities = context.get("searchEntities", List.class);
        serviceReport.buildReport(report, reportNo, entities);
        context.put("report", report);
        serviceReport.add(report);
    }

    @When("I send generate report number request")
    public void sendGenerateReportNumberRequest() {
        Result reportNo = new Result();
        OperationResult<Result> operationResult = serviceReport.generateNumber();
        reportNo.setResult(operationResult.getEntity().getResult());
        context.put("reportNo", reportNo);
    }

    @When("I send delete a report request")
    public void sendDeleteReportRequest() {
        Report lastReport = Entities.getReports().getLatest();
        serviceReport.remove(lastReport);
    }

    @When("I send view a report request")
    public void sendViewReportRequest() {
        Report lastReport = Entities.getReports().getLatest();
        String id = lastReport.getId();
        serviceReport.view(id);
    }

    @When("I send submit a report request")
    public void sendSubmitReportRequest() {
        Report lastreport = Entities.getReports().getLatest();
        List<CurrentOwner> currentOwner = context.get("currentOwner", List.class);
        List<NextOwners> allOwners = new ArrayList<>();
        serviceReport.setNextOwners(currentOwner, allOwners);
        lastreport.setNextOwners(allOwners);
        serviceReport.submit(lastreport);
    }

    @When("I send get owners a report request")
    public void sendGetOwnersReportRequest() {
        Report lastreport = Entities.getReports().getLatest();
        OperationResult<List<CurrentOwner>> currentOwner = serviceReport.possibleOwners(lastreport);
        context.put("currentOwner", currentOwner.getEntity());
    }

    @When("I send take ownership a report request")
    public void sendTakeOwnershipReportRequest() {
        Report report = Entities.getReports().getLatest();
        serviceReport.takeOwnership(report);
    }

    @When("I send approve a report request")
    public void sendApproveReportRequest() {
        Report report = Entities.getReports().getLatest();
        serviceReport.approveReport(report);
    }

    @When("I send return to author a report request")
    public void sendReturnAuthorReportRequest() {
        Report report = Entities.getReports().getLatest();
        report.setComment("qe_" + RandomStringUtils.randomAlphabetic(5));
        serviceReport.returnAuthor(report);
    }

    @When("I send reject a report request")
    public void sendRejectReportRequest() {
        Report report = Entities.getReports().getLatest();
        report.setComment("qe_" + RandomStringUtils.randomAlphabetic(5));
        serviceReport.rejectReport(report);
    }

    @When("I send cancel a report request with owner")
    public void sendCancelReportRequestWithOwner() {
        Report report = Entities.getReports().getLatest();
        report.setComment("qe_auto_" + RandomStringUtils.randomAlphabetic(5));
        serviceReport.cancelReportOwned(report);
    }

    @When("I send cancel a report request without owner")
    public void sendCancelReportRequestWithoutOwner() {
        Report report = Entities.getReports().getLatest();
        report.setComment("qe_auto_" + RandomStringUtils.randomAlphabetic(5));
        serviceReport.cancelReportNotOwned(report);
    }

    @When("I send edit a report request")
    public void sendEditReportRequest() {
        Report report = Entities.getReports().getLatest();
        report.setSubject("qe_" + RandomStringUtils.randomAlphabetic(10));
        report.setDescription("qe_" + RandomStringUtils.randomAlphabetic(10));
        context.put("report", Report.class);
        serviceReport.update(report);
    }

    @When("I send add a comment to report request")
    public void sendAddACommentRequest() {
        Report report = Entities.getReports().getLatest();
        String comment = "qe_" + RandomStringUtils.randomAlphabetic(10);
        context.put("reportComment", comment);
        report.setComment(comment);
        serviceReport.addComment(report);
    }

    @When("I send export with sources:$sources and without creator:$creator a report request")
    public void sendExportReportRequest(Boolean sources, Boolean creator) {
        String dirName = "reportExport";
        context.put("dirName", dirName);
        createDir(dirName);
        Report lastReport = Entities.getReports().getLatest();
        String id = lastReport.getId();
        String reportFileName = "Operator Report #" + lastReport.getReportNo() + ".zip";
        String reportName = "Operator Report #" + lastReport.getReportNo();
        context.put("reportName", reportName);
        serviceReport.export(id, reportFileName, sources, creator);
    }


    @Then("Check content of archive")
    public void checkArchive() throws IOException {
        String reportName = context.get("reportName", String.class);
        String dirName = context.get("dirName", String.class);
        String reportNameInArchive = reportName + "/" + reportName + ".pdf";
        ZipFile zipFile = new ZipFile(dirName + "/" + reportName + ".zip");
        for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            if (!entry.isDirectory()) {
                if (FilenameUtils.getExtension(entry.getName()).equals("pdf")) {
                    Assert.assertEquals(reportNameInArchive, entry.getName());
                }
            }
            if (entry.isDirectory()) {
                Assert.assertEquals(FilenameUtils.getName(entry.getName()), "attachments");
            }
        }
    }

    @Then("Delete exported reports")
    public void deleteExportedReport() {
        String reportName = context.get("reportName", String.class);
        String dirName = context.get("dirName", String.class);
        File file = new File(dirName + "/" + reportName + ".zip");
        if (file.delete()) {
            log.info(reportName + " report export is deleted");
        } else log.error(reportName + " report export file doesn't exists");
    }

    @Then("Delete created directory")
    public void deleteDirectory() {
        String dirName = context.get("dirName", String.class);
        File dir = new File(dirName);

        if (dir.isDirectory() == false) {
            log.info("Not a directory. Do nothing");
            return;
        }
        File[] listFiles = dir.listFiles();
        for (File file : listFiles) {
            log.info("Deleting " + file.getName());
            file.delete();
        }
        log.info("Deleting Directory. Success = " + dir.delete());
    }


    @Then("Report is created")
    public void reportIsCreated() {
        Report lastreport = Entities.getReports().getLatest();
        Report createdReport = context.get("report", Report.class);
        assertEquals(lastreport.getClassification(), createdReport.getClassification());
        assertEquals(lastreport.getReportNo(), createdReport.getReportNo());
        assertEquals(lastreport.getDescription(), createdReport.getDescription());
        assertEquals(lastreport.getState(), "Initial Draft");
        assertEquals(lastreport.getStateId(), "1");
        assertEquals(lastreport.getStateType(), "INITIAL");
    }

    @Then("Report is submitted")
    public void reportIsSubmitted() {
        Report lastreport = Entities.getReports().getLatest();
        assertEquals(lastreport.getState(), "Awaiting Review");
        assertEquals(lastreport.getStateId(), "2");
        assertEquals(lastreport.getStateType(), "INITIAL");
    }

    @Then("Report is took ownership")
    public void reportIsTookOwnerShip() {
        Report lastreport = Entities.getReports().getLatest();
        assertEquals(lastreport.getState(), "Under Review");
        assertEquals(lastreport.getStateId(), "3");
        assertEquals(lastreport.getStateType(), "IN_PROGRESS");
    }

    @Then("Report is approved")
    public void reportIsApproved() {
        Report lastreport = Entities.getReports().getLatest();
        assertEquals(lastreport.getState(), "Approved");
        assertEquals(lastreport.getStateId(), "5");
        assertEquals(lastreport.getStateType(), "FINAL");
    }

    @Then("Report is returned to author")
    public void reportIsReturned() {
        Report lastreport = Entities.getReports().getLatest();
        assertEquals(lastreport.getState(), "Returned for Revision");
        assertEquals(lastreport.getStateId(), "4");
        assertEquals(lastreport.getStateType(), "IN_PROGRESS");
    }

    @Then("Report is rejected")
    public void reportIsRejected() {
        Report lastreport = Entities.getReports().getLatest();
        assertEquals(lastreport.getState(), "Rejected");
        assertEquals(lastreport.getStateId(), "7");
        assertEquals(lastreport.getStateType(), "FINAL");
    }

    @Then("Report is canceled")
    public void reportIsCanceled() {
        Report lastreport = Entities.getReports().getLatest();
        assertEquals(lastreport.getState(), "Cancelled");
        assertEquals(lastreport.getStateId(), "6");
        assertEquals(lastreport.getStateType(), "FINAL");
    }

    @Then("Report is updated")
    public void reportIsUpdated() {
        Report updatedReport = Entities.getReports().getLatest();
        Report contextReport = context.get("report", Report.class);
        reportCheck(updatedReport, contextReport);
    }

    @Then("Comment is added")
    public void reportCommentIsAdded() {
        Report actualComment = Entities.getReports().getLatest();
        String expectComment = context.get("reportComment", String.class);
        assertEquals(actualComment.getComment(), expectComment);
    }

    private void reportCheck(Report updatedReport, Report contextReport) {
        assertEquals(updatedReport.getSubject(), contextReport.getSubject());
        assertEquals(updatedReport.getDescription(), contextReport.getDescription());

    }

    private void createDir(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            System.out.println("creating directory: " + dir.getName());
            boolean result = false;

            try {
                dir.mkdir();
                result = true;
            } catch (SecurityException se) {
                throw new AssertionError("Not Able create a directory");

            }
            if (result) {
                log.info("DIR created");
            }
        }
    }
}
