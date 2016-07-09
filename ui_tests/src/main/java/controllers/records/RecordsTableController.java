package controllers.records;

import blocks.context.tables.Row;
import blocks.context.tables.records.RecordRow;
import controllers.TableController;
import errors.NotFoundException;
import model.Record;
import model.UIRecord;
import org.apache.log4j.Logger;
import pages.SigintPage;
import utils.Parser;
import utils.TeelaDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class RecordsTableController extends TableController {

    private Logger log = Logger.getLogger(RecordsTableController.class);

    public RecordsTableController(SigintPage page) {
        super(page);
    }

    public Record findRecordById(String recordId) throws NotFoundException {
        Row recordRow = table.getRecordByColumnNameAndValue("Record ID", recordId);
        return initFromRow(recordRow);
    }

    public Record selectRecordbyStatus(String status) {
        Row recordRow;
        try {
            recordRow = table.getRecordByColumnNameAndValue("Status", status);
        } catch (NotFoundException e) {
            log.trace(e.getMessage(), e);
            throw new AssertionError(e.getMessage());
        }
        selectRecordInTable(recordRow);
        return initFromRow(recordRow);
    }


    public void openCreateReportForm(String recordId) {
        RecordRow recordRow;
        try {
            recordRow = (RecordRow)table.getRecordByColumnNameAndValue("Record ID", recordId);
        } catch (NotFoundException e) {
            log.trace(e.getMessage(), e);
            throw new AssertionError(e.getMessage());
        }
        recordRow.clickCreateReportButton();
    }

    protected Record  initFromRow(Row recordRow) {
        RecordRow row = (RecordRow) recordRow;
        Record newRecord = new UIRecord();
        try {
            TeelaDate date = new TeelaDate(new SimpleDateFormat("d MMM, HH:mm yyyy").parse(row.getDate().replace("GMT", "") + "2016"));
            newRecord.setDateAndTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new AssertionError("Was unable to set teela date from ui Row date");
        }
        newRecord.setTMSI(row.getTMSI());
        newRecord.setIMSI(row.getIMSI());
        newRecord.setLanguage(row.getLanguage());
        newRecord.setProcessedStatus(row.getStatus());
        newRecord.setRecordID(row.getRecordID());
        if (row.getType().equalsIgnoreCase("voice")) {
            newRecord.setDuration(Parser.getDurationFromString(row.getDetails()));
        } else {
            newRecord.setText(row.getDetails());
        }
        return newRecord;
    }

}
