package steps;

import abs.EntityList;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import data_generator.DataGenerator;
import errors.NullReturnException;
import http.OperationResult;
import http.OperationsResults;
import model.*;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.RecordEntityService;
import services.ReportCategoryService;
import services.ReportCreateService;
import services.ReportService;
import utils.RandomGenerator;

import java.util.List;

public class APIReportSteps extends APISteps {

    private Logger log = Logger.getLogger(APIReportSteps.class);
    private ReportService service = new ReportService();
    private ReportCategoryService reportCategoryService = new ReportCategoryService();
    
    
    @When("I send create manual report")
    public void createManualReport() {
        Report report = context.get("report", Report.class);
        OperationResult<Report> operationResult = service.add(report);
        OperationsResults.setResult(operationResult);
        context.put("requestReport", operationResult.getResult());
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
        EntityList<ReportCategory> categories = operationResult.getResult();
        for (ReportCategory reportCategory : categories) {
            reportCategory.setCurrentValue("--");
        }
        Report report = context.get("report", Report.class);
        log.info("Set categories to report...");
        report.setCategories(categories.getEntities());

        context.put("report", report);
    }

    @When("I create new report category")
    public void createReportCategory() {
        ReportCategory reportCategory = objectInitializer.randomEntity(ReportCategory.class);

        OperationResult<ReportCategory> operationResult = reportCategoryService.add(reportCategory);
        OperationsResults.setResult(operationResult);

        context.put("reportCategory", operationResult.getResult());
    }

    @Then("Report category is correct")
    public void reportCategoryIsCorrect() {
        ReportCategory savedCategory = Entities.getReportCategories().getLatest();
        ReportCategory responcedCategory = context.get("reportCategory", ReportCategory.class);

        Assert.assertEquals("hidden mismatch", savedCategory.getHidden(), responcedCategory.getHidden());
        Assert.assertEquals("option values mismatch", savedCategory.getValues(), responcedCategory.getValues());
        Assert.assertEquals("category name mismatch", savedCategory.getName(), responcedCategory.getName());
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
