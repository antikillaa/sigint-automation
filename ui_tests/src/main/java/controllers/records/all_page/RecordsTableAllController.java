package controllers.records.all_page;

import blocks.context.tables.Row;
import blocks.context.tables.records.RecordAllRow;
import controllers.TableController;
import model.Record;
import pages.SigintPage;
import utils.Parser;
import utils.TeelaDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RecordsTableAllController extends TableController {


    public RecordsTableAllController(SigintPage page) {
        super(page);
    }

    public Record findRecordById(String recordId) {
        Row recordRow = table.getRecordByColumnNameAndValue("Record ID", recordId);
        return initFromRow(recordRow);
    }

    public void selectRecordbyStatus(String status) {
        Row recordRow = table.getRecordByColumnNameAndValue("Status", status);
        selectRecordinTable(recordRow);
    }

    @Override
    protected Record initFromRow(Row recordRow) {
        RecordAllRow row = (RecordAllRow)recordRow;
        Record newRecord = new Record();
        try {
            TeelaDate date = new TeelaDate(new SimpleDateFormat("d MMM, HH:mm yyyy").parse(row.getDate().replace("GMT","")+"2016"));
            newRecord.setDateAndTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new AssertionError();
        }
        newRecord.setSource(row.getSource());
        newRecord.setTMSI(row.getTMSI());
        newRecord.setIMSI(row.getIMSI());
        newRecord.setLanguage(row.getLanguage());
        newRecord.setToNumber(row.getToNumber());
        newRecord.setFromNumber(row.getFromNumber());
        newRecord.setFromCountry(row.getFromCountry());
        newRecord.setToCountry(row.getToCountry());
        newRecord.setProcessedStatus(row.getStatus());
        newRecord.setRecordID(row.getRecordID());
        newRecord.setType(row.getType());
        if (row.getType().equalsIgnoreCase("voice")) {
            newRecord.setDuration(Parser.getDurationFromString(row.getDetails()));
        } else {
            newRecord.setSMSText(row.getDetails());
        }
        return newRecord;
    }


}
