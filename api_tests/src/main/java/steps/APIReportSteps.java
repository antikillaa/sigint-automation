package steps;

import conditions.Verify;
import errors.NullReturnException;
import model.*;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.CategoryService;
import services.ReportService;
import utils.RandomGenerator;

import java.util.List;

import static conditions.Conditions.equals;

public class APIReportSteps extends APISteps {

    private Logger log = Logger.getLogger(APIReportSteps.class);
    private ReportService service = new ReportService();
    private CategoryService categoryService = new CategoryService();

    @When("I send create manual report")
    public void createManualReport() {
        Report report = context.get("report", Report.class);

        int responseCode = service.add(report);

        context.put("code", responseCode);
        context.put("requestReport", report);
    }

    @When("Generate new report with current user as owner")
    public void generateNewReport(){
        log.info("Generate new report...");
        Report report = new Report().generate();
        report = service.setOwner(report);

        context.put("report", report);
    }

    @When("Add categories to report")
    public void addCategoriesToReport() {
        List<ReportCategory> categories = categoryService.list();
        for (ReportCategory reportCategory : categories) {
            reportCategory.setCurrentValue("--");
        }

        Report report = context.get("report", Report.class);
        log.info("Set categories to report...");
        report.setCategories(categories);

        context.put("report", report);
    }

    @When("Add new random record to report")
    public void addRecordToReport() {
        log.info("Generate new random record...");
        Source source = RandomGenerator.getRandomItemFromList(context.getDictionary().getSources());
        RecordType recordType = RecordType.getRandom();

        ReportRecord reportRecord = new ReportRecord()
                .generate()
                .setSourceId(source.getId())
                .setSourceName(source.getName())
                .setSourceType(source.getType())
                .setType(recordType)
                .setSourceLetterCode(source.getType().toLetterCode())
                .setTypeEnglishName(recordType.toEnglishName())
                .setTypeArabicName(recordType.toArabicName());

        Report report = context.get("report", Report.class);

        log.info("Add record to report...");
        List<ReportRecord> recordList = report.getReportRecords();
        recordList.add(reportRecord);
        report.setReportRecords(recordList);

        context.put("report", report);
    }

    @Then("Created report is correct")
    public void createdReportIsCorrect() throws NullReturnException {
        Report requestReport = context.get("requestReport", Report.class);
        Report createdReport = context.entities().getReports().getLatest();

        Verify.shouldBe(equals.elements(createdReport.getOwner().getUser(), requestReport.getOwner().getUser()));
        createdReport.getOwner().setUser(null);
        requestReport.getOwner().setUser(null);
        Verify.shouldBe(equals.elements(createdReport.getOwner(), requestReport.getOwner()));
        Verify.shouldBe(equals.elements(createdReport.getStatus(), requestReport.getStatus()));
        Verify.shouldBe(equals.elements(createdReport.getAuthorId(), requestReport.getAuthorId()));
        Verify.shouldBe(equals.elements(createdReport.getAuthorName(), requestReport.getAuthorName()));
        Verify.shouldBe(equals.elements(createdReport.getSubject(), requestReport.getSubject()));
        Verify.shouldBe(equals.elements(createdReport.getReportRecords(), requestReport.getReportRecords()));
        Verify.shouldBe(equals.elements(createdReport.getCategories(), requestReport.getCategories()));
    }

}
