package steps;

import conditions.Conditions;
import conditions.Verify;
import controllers.RecordAddNewController;
import controllers.RecordsSearchController;
import model.Record;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import pages.blocks.tables.RecordRow;
import pages.blocks.tables.RecordsTable;
import pages.records.RecordsProcessedPage;
import pages.records.RecordsSearchPage;
import pages.reports.sidebar.SidebarRightWrapper;
import utils.RandomGenerator;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.page;

public class UIRecordsSteps extends UISteps {

    private RecordsSearchController searchController = new RecordsSearchController();
    private RecordAddNewController addController = new RecordAddNewController();


    @When("I open create record form")
    public void openCreateForm() {
        searchController.openCreateForm();

    }


    private void selectRecord(RecordRow recordRow) {
        context.put("recordRow", recordRow);
        recordRow.selectRecord();
        Record record = new Record();
        context.put("record", record);
    }

    @When("I fill the form for $recordType record")
    public void iFillTheForm(String recordType) {
        Record record = new Record().generate();
        record.setType(recordType);
        record.setSource(RandomGenerator.getRandomItemFromList(context.getDictionary().getSources()).getName());
        record.setFromCountry(RandomGenerator.getRandomCountry());
        record.setToCountry(RandomGenerator.getRandomCountry());
        record.setLanguage(RandomGenerator.getRandomLanguage());
        addController.fillForm(record);
        context.put("record", record);
    }



    @Then("I should see 'Records->Search' page")
    public void iShouldSeeRecordSearchPage() {
        searchController.isOnPage();
    }


    @Then("I should see new $type record on the table")
    public void iShouldSeeNewSMSRecordOnTheTable(String type) {
        // Arrange
        Record record = getRecordFromContext();
        Record foundRecord = searchController.findRecord(record);
        Verify.shouldBe(Conditions.equals.elements(record, foundRecord));

    }




    @Given("I'm on 'Records->Processing' page")
    public void iAmOnRecordProcessingScreen() {
        String href = pages.recordsProcessedPage().getHeader().getBreadcrumb().getCurrentPath().getAttribute("href");

        if (!href.contentEquals(RecordsProcessedPage.url)) {
            pages.recordsProcessedPage().load();
        }
    }


    @Given("I'm on 'Records->Search' page")
    public void iAmOnRecordSearchScreen() {
        String href = pages.recordsSearchPage().getHeader().getBreadcrumb().getCurrentPath().getAttribute("href");

        if (!href.contentEquals(RecordsSearchPage.url)) {
            pages.recordsSearchPage().load();
        }
    }


    @When("I checked checkbox on the first non-REPORTED record in table")
    public void iCheckFirstRecordCheckbox() {
        for (RecordRow recordRow : page(RecordsTable.class).getRecords()) {
            if (recordRow.getStatus().getAttribute("title").contentEquals("")) {
                selectRecord(recordRow);
                return;
            }
        }
        log.warn("Not found any non-REPORTED record");
        throw new AssertionError("Not found any non-REPORTED record");
    }


    @When("I press 'Create Report' button")
    public void iPressCreateReportButton() {
        pages.recordsProcessedPage().getTable().getTableToolbar().clickCreateReportButton();
    }


    @When("I press 'Save As Draft' button")
    public void iPressSaveAsDraftButton() {
        page(SidebarRightWrapper.class).clickSaveDraft();
    }


    @Then("record status is '$status' on 'Records->Processed' page")
    public void chekcRecordStatusOnRecordsProcessedPage(String status) {
        pages.recordsProcessedPage().load();

        RecordRow recordRow = pages.recordsProcessedPage().getTable().getRecordByColumnNameAndValue("Record ID", getRecordFromContext().getRecordID());
        if (recordRow != null) {
            recordRow.getStatus().shouldHave(attribute("title", status));
        } else {
            log.warn("Record ID : " + getRecordFromContext().getRecordID() + " does not found on Records->Processed page");
            throw new AssertionError("Record ID : " + getRecordFromContext().getRecordID() + " does not found on Records->Processed page");
        }
    }


    @Then("record status is '$status' on 'Records->Search' page")
    public void checkRecordStatusOnRecordsSearchPage(String status) {
        pages.recordsSearchPage().load();

        RecordRow recordRow = pages.recordsSearchPage().getTable().getRecordByColumnNameAndValue("Record ID", getRecordFromContext().getRecordID());
        if (recordRow != null) {
            recordRow.getStatus().shouldHave(attribute("title", status));
        } else {
            log.warn("Record ID : " + getRecordFromContext().getRecordID() + " does not found on Records->Search page");
            throw new AssertionError("Record ID : " + getRecordFromContext().getRecordID() + " does not found on Records->Search page");
        }
    }


    @When("I press 'Create report' button against it")
    public void pressCreateReportButtonAgainstIt() {
        RecordRow recordRow = context.get("recordRow", RecordRow.class);
        recordRow.clickCreateReportButton();

    }


    @When("I press 'Attach to report' button on 'Record Details' modal dialog")
    public void pressAttachToReportButton() {
        pages.recordDetailsDialog().clickAttachButton();
    }


    @When("I select first record in the table")
    public void selectFirstRecord() {
        selectRecord(page(RecordsTable.class).firstRecord());
    }

}
