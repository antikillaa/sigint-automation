package controllers;

import model.AppContext;
import model.Record;
import pages.records.RecordsCreatePage;

public class RecordAddNewController {

    private static AppContext context = AppContext.getContext();


    private RecordsCreatePage getPage() {
        return context.get("page", RecordsCreatePage.class);

    }

    private Record fillSMS(Record record) {
        getPage()
                .clickSMSRecordType()
                .typeSMSText(record.getSMSText());

        return record;
    }

    private Record fillCall(Record record) {

        getPage()
                .clickCallRecordType()
                .typeCallDuration(record.getDuration());
        return record;
    }


    public Record fillForm(Record record) {

            getPage()
                .selectSource(record.getSource())
                .selectFromCountry(record.getFromCountry())
                .selectToCountry(record.getToCountry())
                .selectLanguage(record.getLanguage())
                .typeDataAndTime(record.getDateAndTime())
                .typeFromNumber(record.getFromNumber())
                .typeTmsi(record.getTMSI())
                .typeImsi(record.getIMSI())
                .typeRecordId(record.getRecordID())
                .typeToNumber(record.getToNumber());

        String recordType = record.getType();
        if (recordType.equalsIgnoreCase("sms")) {
            fillSMS(record);
        } else if (recordType.equalsIgnoreCase("voice")) {
            fillCall(record);
        }
        else {throw new AssertionError(String.format("Incorrect record type %s", recordType));}
        getPage().clickCreateRecordButton();

        return record;
    }
}
