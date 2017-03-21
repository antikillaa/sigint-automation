package steps;

import abs.EntityList;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import http.OperationResult;
import http.OperationsResults;
import model.Record;
import model.Report;
import model.ReportCategory;
import model.Source;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.RecordEntityService;
import services.ReportCategoryService;
import services.ReportCreateService;
import services.ReportService;
import utils.RandomGenerator;

import java.util.List;

public class APIReportSteps extends APISteps {

    private static final Logger log = Logger.getLogger(APIReportSteps.class);
    private ReportService reportService = new ReportService();
    private ReportCategoryService reportCategoryService = new ReportCategoryService();
    
    @When("I send create manual report")
    public void createManualReport() {
        Report report = context.get("report", Report.class);
        OperationResult<Report> operationResult = reportService.add(report);
        OperationsResults.setResult(operationResult);
        context.put("requestReport", operationResult.getEntity());
    }

    @When("Generate new report with logged user as owner")
    public void generateNewReport(){
        log.info("Generate new report...");
        Report report = getRandomReport();
        context.put("report", report);
    }

    @When("Add categories to report")
    public void addCategoriesToReport() {
        OperationResult<EntityList<ReportCategory>> operationResult = reportCategoryService.list();
        EntityList<ReportCategory> categories = operationResult.getEntity();
        for (ReportCategory reportCategory : categories) {
            reportCategory.setCurrentValue("--");
        }
        Report report = context.get("report", Report.class);
        log.info("Set categories to report...");
        report.setCategories(categories.getEntities());

        context.put("report", report);
    }

    @When("Add new random record to report")
    public void addRecordToReport() {
        log.info("Generate new random record...");
        Source source = RandomGenerator.getRandomItemFromList(appContext.getDictionary().getSources());
        RecordEntityService recordEntityService = new RecordEntityService();
        Record record = recordEntityService.createReportRecord();
        record.setSourceId(source.getId());
        record.setSourceName(source.getName());
        record.setSourceType(source.getType());
        record.setSourceLetterCode(source.getType().toLetterCode());
        record.setSourceLocation(source.getLocation());

        Report report = context.get("report", Report.class);

        log.info("Add record to report...");
        List<Record> recordList = report.getReportRecords();
        recordList.add(record);
        report.setReportRecords(recordList);

        context.put("report", report);
    }

    @Then("Created report is correct")
    public void createdReportIsCorrect() throws NullReturnException {
        Report requestReport = context.get("requestReport", Report.class);
        Report createdReport = Entities.getReports().getLatest();

        Verify.shouldBe(Conditions.equals(createdReport.getOwner().getRole(), requestReport.getOwner().getRole()));
        Verify.shouldBe(Conditions.equals(createdReport.getOwner().getUser().getId(), requestReport.getOwner().getUser().getId()));
        Verify.shouldBe(Conditions.equals(createdReport.getOwner().getUser().getName(), requestReport.getOwner().getUser().getName()));

        Verify.shouldBe(Conditions.equals(createdReport.getStatus(), requestReport.getStatus()));
        Verify.shouldBe(Conditions.equals(createdReport.getAuthorId(), requestReport.getAuthorId()));
        Verify.shouldBe(Conditions.equals(createdReport.getAuthorName(), requestReport.getAuthorName()));
        Verify.shouldBe(Conditions.equals(createdReport.getSubject(), requestReport.getSubject()));
        Verify.shouldBe(Conditions.equals(createdReport.getReportRecords(), requestReport.getReportRecords()));
        Verify.shouldBe(Conditions.equals(createdReport.getCategories(), requestReport.getCategories()));
    }
    
    static Report getRandomReport() {
        return ReportCreateService.generateReport();
    }

}
