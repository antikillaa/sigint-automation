package controllers.records;

import controllers.PageController;
import model.Record;
import model.RecordType;
import pages.Pages;
import pages.records.RecordsCreatePage;

public class RecordAddController extends PageController<RecordsCreatePage> {

    public RecordAddController() {
        super(Pages.recordsCreatePage(), RecordsCreatePage.class);
    }

    private Record fillSMS(Record record) {
        getPage()
                .clickSMSRecordType()
                .typeSMSText(record.getText());

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
                .selectSource(record.getSourceName())
                .selectFromCountry(record.getFromCountry())
                .selectToCountry(record.getToCountry())
                .selectLanguage(record.getLanguage())
                .typeDataAndTime(record.getDateAndTime())
                .typeFromNumber(record.getFromNumber())
                .typeTmsi(record.getTmsi())
                .typeImsi(record.getImsi())
                .typeRecordId(record.getOriginalId())
                .typeToNumber(record.getToNumber());

        RecordType recordType = record.getType();
        if (recordType.equals(RecordType.SMS)) {
            fillSMS(record);
        } else if (recordType.equals(RecordType.Voice)) {
            fillCall(record);
        }
        else {throw new AssertionError(String.format("Incorrect record type %s", recordType));}
        getPage().clickCreateRecordButton();
        return record;
    }
}
