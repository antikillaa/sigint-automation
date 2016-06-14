package controllers.records.all_page;

import blocks.context.tables.Row;
import blocks.context.tables.records.RecordAllRow;
import controllers.records.RecordsTableController;
import model.Record;
import pages.SigintPage;

public class RecordsTableAllController extends RecordsTableController {

    public RecordsTableAllController(SigintPage page) {
        super(page);
    }


    @Override
    protected Record initFromRow(Row recordRow) {
        RecordAllRow row = (RecordAllRow)recordRow;
        Record newRecord = super.initFromRow(recordRow);
        newRecord.setSource(row.getSource());
        return newRecord;
    }


}
