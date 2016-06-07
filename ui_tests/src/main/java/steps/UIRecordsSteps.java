package steps;

import conditions.Conditions;
import conditions.Verify;
import controllers.records.RecordAddController;
import controllers.records.all_page.RecordsAllController;
import model.Record;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import blocks.context.tables.records.RecordAllRow;
import utils.RandomGenerator;

public class UIRecordsSteps extends UISteps {

    private RecordsAllController allController = new RecordsAllController();
    private RecordAddController addController = new RecordAddController();
    private static Logger log = Logger.getLogger(UIRecordsSteps.class);

    @When("I filter records by current date")
    public void filterByDate() {
        String dateInterval = RandomGenerator.todayDateInterval();
        allController.getToolbarController().searchByDate(dateInterval);

    }

    @When("I open create record form")
    public void openCreateForm() {
        allController.getToolbarController().openCreateRecordForm();

    }


    private void selectRecord(RecordAllRow recordRow) {
        context.put("recordRow", recordRow);
        recordRow.selectRecord();
        Record record = new Record();
        context.put("record", record);
    }

    @When("I fill the form for $recordType record")
    public void iFillTheForm(String recordType) {
        Record record = new Record();
        record.setType(recordType);
        record.setSource(RandomGenerator.getRandomItemFromList(context.getDictionary().getSources()).getName());
        record.setFromCountry(RandomGenerator.getRandomCountry());
        record.setToCountry(RandomGenerator.getRandomCountry());
        record.setLanguage(RandomGenerator.getRandomLanguage());
        record.generate();
        addController.fillForm(record);
        context.put("record", record);
    }



    @Then("I should see new record on the table")
    public void iShouldSeeNewSMSRecordOnTheTable() {
        // Arrange
        Record record = getRecordFromContext();
        Record foundRecord = allController.getTableController().findRecordById(record.getRecordID());
        Verify.shouldBe(Conditions.equals.elements(record, foundRecord));

    }


    @When("I checked checkbox on record with $status status")
    public void iCheckFirstRecordCheckbox(String status) {
        //if (status.equalsIgnoreCase("non-reported")) {
        //    searchController.selectRecordbyStatus("");
        //} else {
        //    searchController.selectRecordbyStatus(status);
        //}
    }



    @When("I press 'Create Report' button")
    public void iPressCreateReportButton() {
        allController.getToolbarController().openCreateReportForm();
    }



    @Then("record status is '$status' on 'Records->Processed' page")
    public void chekcRecordStatusOnRecordsProcessedPage(String status) {
        //pages.recordsProcessedPage().load();

        //RecordAllRow recordRow = pages.recordsProcessedPage().getTable().getRecordByColumnNameAndValue("Record ID", getRecordFromContext().getRecordID());
        //if (recordRow != null) {
         //   recordRow.getStatus().shouldHave(attribute("title", status));
        //} else {
         //   log.warn("Record ID : " + getRecordFromContext().getRecordID() + " does not found on Records->Processed page");
         //   throw new AssertionError("Record ID : " + getRecordFromContext().getRecordID() + " does not found on Records->Processed page");
       // }
    }


    @Then("record status is '$status' on 'Records->Search' page")
    public void checkRecordStatusOnRecordsSearchPage(String status) {

        //RecordAllRow recordRow = pages.recordsSearchPage().getTable().getRecordByColumnNameAndValue("Record ID", getRecordFromContext().getRecordID());
        //if (recordRow != null) {
        //    recordRow.getStatus().shouldHave(attribute("title", status));
        //} else {
        //    log.warn("Record ID : " + getRecordFromContext().getRecordID() + " does not found on Records->Search page");
        //    throw new AssertionError("Record ID : " + getRecordFromContext().getRecordID() + " does not found on Records->Search page");
        //}
    }


    @When("I press 'Create report' button against it")
    public void pressCreateReportButtonAgainstIt() {
        RecordAllRow recordRow = context.get("recordRow", RecordAllRow.class);
        recordRow.clickCreateReportButton();

    }


    @When("I press 'Attach to report' button on 'Record Details' modal dialog")
    public void pressAttachToReportButton() {
        pages.recordDetailsDialog().clickAttachButton();
    }


    @When("I select first record in the table")
    public void selectFirstRecord() {
        //selectRecord(page(RecordsAllTable.class).firstRecord());
    }

}
