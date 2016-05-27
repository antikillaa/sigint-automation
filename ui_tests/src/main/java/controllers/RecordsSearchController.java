package controllers;

import conditions.UIConditions;
import conditions.Verify;
import model.AppContext;
import model.Record;
import pages.blocks.tables.RecordRow;
import pages.records.RecordsCreatePage;
import pages.records.RecordsSearchPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RecordsSearchController {

    private static AppContext context = AppContext.getContext();


    private RecordsSearchPage getPage() {
        return context.get("page", RecordsSearchPage.class);
    }


    public void openCreateForm() {
        RecordsCreatePage page = getPage().getTable().getTableToolbar().clickNewRecordButton();
        Verify.shouldBe(UIConditions.present.element(page.getForm()));
        context.put("page", page);
    }

    public Boolean isOnPage() {
        Verify.shouldBe(UIConditions.attribute("href", RecordsSearchPage.url));
        return true;

    }

    public Record findRecord(Record record) {
        RecordRow recordRow = getPage().getTable().getRecordByColumnNameAndValue("Record ID", record.getRecordID());
        String sDate = new SimpleDateFormat("d MMM, HH:mm").format(record.getDateAndTime()) + " GMT";
        Record newRecord = new Record();
        try {
            record.setDateAndTime(new SimpleDateFormat("d MMM, HH:mm z").parse(recordRow.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newRecord.setSource(recordRow.getSource());
        newRecord.setTMSI(recordRow.getTMSI());
        newRecord.setIMSI(recordRow.getIMSI());
        newRecord.setLanguage(recordRow.getLanguage());
        newRecord.setToNumber(recordRow.getToNumber());
        newRecord.setFromNumber(recordRow.getFromNumber());
        newRecord.setFromCountry(recordRow.getFromCountry());
        newRecord.setToCountry(recordRow.getToCountry());

        return newRecord;
    }

}
