package ae.pegasus.framework.steps;

import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.services.ReportService;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class APIReportSteps extends APISteps {

    public static ReportService serviceReport = new ReportService();

    private static Logger log = Logger.getLogger(APIFinderFileSteps.class);

    static Report getRandomReport() {
        return objectInitializer.randomEntity(Report.class);
    }

    @When("I send create a report request")
    public void sendReportRequest() {
        Report report = new Report();
        Result reportNo = context.get("reportNo", Result.class);
        List<SearchRecord> entities = context.get("searchResults", List.class);
        serviceReport.buildReport(report, reportNo, entities);
        context.put("report", report);
        serviceReport.add(report);
    }

    @When("I send send generate report number request")
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
        serviceReport.approve(report);
    }

    @Then("Report is created")
    public void reportIsCreated() {
        Report lastreport = Entities.getReports().getLatest();
        Report createdReport = context.get("report", Report.class);
        assertEquals(lastreport.getClassification(), createdReport.getClassification());
        assertEquals(lastreport.getReportNo(), createdReport.getReportNo());
        assertEquals(lastreport.getDescription(), createdReport.getDescription());
    }

    @Then("Report is submitted")
    public void reportIsSubmitted() {
        Report lastreport = Entities.getReports().getLatest();
        assertEquals(lastreport.getState(), "Awaiting Review");
        assertEquals(lastreport.getStateId(), "2");
    }

    @Then("Report is took ownership")
    public void reportIsTookOwnerShip() {
        Report lastreport = Entities.getReports().getLatest();
        assertEquals(lastreport.getState(), "Under Review");
        assertEquals(lastreport.getStateId(), "3");
    }

    @Then("Report is approved")
    public void reportIsApproved() {
        Report lastreport = Entities.getReports().getLatest();
        assertEquals(lastreport.getState(), "Approved");
        assertEquals(lastreport.getStateId(), "5");
    }

}
