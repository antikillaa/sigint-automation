package steps;

import errors.NullReturnException;
import model.Record;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import pages.blocks.SidebarRightWrapper;
import pages.blocks.content.main.table.RecordRow;
import pages.blocks.content.main.table.RecordsTable;
import pages.records.RecordsProcessedPage;
import pages.records.RecordsSearchPage;

import java.text.SimpleDateFormat;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.page;

public class UIRecordsSteps extends UISteps {

    @When("I press 'New Record' button")
    public void iClickNewRecordButton() {
        pages.recordsSearchPage()
                .getTable().getTableToolbar().clickNewRecordButton();
    }


    @Then("I should see 'Create a New Record' page")
    public void iShouldSeeCreateANewRecordPage() {
        pages.recordsCreatePage().getForm().shouldBe(present);
    }


    private void fillForm() {
        // Arrange
        Record record = new Record().generate();

        // Act
        pages.recordsCreatePage()
                .selectOptionByIndex(
                        new Random().nextInt(
                                pages.recordsCreatePage()
                                        .clickSource()
                                        .getOptionsList().size()))
                .selectOptionByIndex(
                        new Random().nextInt(
                                pages.recordsCreatePage()
                                        .clickFromCountry()
                                        .getOptionsList().size()))
                .selectOptionByIndex(
                        new Random().nextInt(
                                pages.recordsCreatePage()
                                        .clickToCountry()
                                        .getOptionsList().size()))
                .selectOptionByIndex(
                        new Random().nextInt(
                                pages.recordsCreatePage()
                                        .clickLanguage()
                                        .getOptionsList().size()))
                .typeDataAndTime(record.getDateAndTime())
                .typeFromNumber(record.getFromNumber())
                .typeTmsi(record.getTMSI())
                .typeImsi(record.getIMSI())
                .typeRecordId(record.getRecordID())
                .typeToNumber(record.getToNumber());

        context.putToRunContext("record", record);
    }

    private void updateRecord(String type) {
        Record record = getRecordFromContext();

        record
                .setType(type)
                .setSource(pages.recordsCreatePage().getSource().getText())
                .setFromCountry(pages.recordsCreatePage().getFromCountry().getText())
                .setToCountry(pages.recordsCreatePage().getToCountry().getText())
                .setLanguage(pages.recordsCreatePage().getLanguage().getText());

        context.putToRunContext("record", record);
    }

    private void selectRecord(RecordRow recordRow) {
        context.putToRunContext("recordRow", recordRow);
        recordRow.selectRecord();

        Record record = new Record();
        record
                .setRecordID(recordRow.getCellByColumnName("Record ID").getText())
                .setFromNumber(recordRow.getFromNumber().getText())
                .setToNumber(recordRow.getToNumber().getText());

        context.putToRunContext("record", record);
    }

    @When("I fill the form for SMS record")
    public void iFillTheFormForSMSRecord() {
        fillForm();

        pages.recordsCreatePage()
                .clickSMSRecordType()
                .typeSMSText(getRecordFromContext().getSMSText());

        updateRecord("SMS");
    }


    @When("I fill the form for Call record")
    public void iFillTheFormForCallRecord() {
        fillForm();

        pages.recordsCreatePage()
                .clickCallRecordType()
                .typeCallDuration(getRecordFromContext().getDuration());

        updateRecord("Voice");
    }


    @When("I press 'Create Record' button")
    public void iPressCreateRecordButton() {
        pages.recordsCreatePage().clickCreateRecordButton();
    }


    @Then("I should see 'Records->Search' page")
    public void iShouldSeeRecordSearchPage() {
        pages.recordsSearchPage()
                .getHeader().getBreadcrumb().getCurrentPath()
                .shouldHave(attribute("href", RecordsSearchPage.url));
    }


    @Then("I should see new $type record on the table")
    public void iShouldSeeNewSMSRecordOnTheTable(String type) {
        // Arrange
        Record record = getRecordFromContext();
        page(RecordsTable.class).getLoading().shouldBe(disappear);

        for (RecordRow recordRow : page(RecordsTable.class).getRecords()) {
            if (record.getRecordID().equals(recordRow.getCellByColumnName("Record ID").getText())) {
                // Assert
                String sDate = new SimpleDateFormat("d MMM, HH:mm").format(record.getDateAndTime()) + " GMT";
                recordRow.getCellByColumnName("Date").shouldHave(text(sDate));
                recordRow.getCellByColumnName("Source").shouldHave(text(record.getSource()));
                recordRow.getCellByColumnName("TMSI").shouldHave(text(record.getTMSI()));
                recordRow.getCellByColumnName("IMSI").shouldHave(text(record.getIMSI()));
                recordRow.getCellByColumnName("Language").shouldHave(text(record.getLanguage()));
                recordRow.getCellByColumnName("Description").shouldHave(text(record.getFromNumber() + "\n" + record.getToNumber()));
                recordRow.getCellByColumnName("Status").shouldHave(attribute("title", ""));
                if (record.getType().equals("SMS")) {
                    recordRow.getCellByColumnName("Details").shouldHave(text(record.getSMSText()));
                    recordRow.getTypeSMS().shouldBe(present);
                } else {
                    recordRow.getCellByColumnName("Details").shouldHave(text(record.durationToString()));
                    recordRow.getTypeVoice().shouldBe(present);
                }
                // TODO check fromCountry
                // TODO check toCountry
                return;
            }
        }
        throw new AssertionError("Record with recordID:" + record.getRecordID() + " does not found");
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
        try {
            RecordRow recordRow = context.getFromRunContext("recordRow", RecordRow.class);
            recordRow.clickCreateReportButton();
        } catch (NullReturnException e) {
            e.printStackTrace();
            log.warn("Record doesn't exist!");
            throw new AssertionError("Record doesn't exist");
        }
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
