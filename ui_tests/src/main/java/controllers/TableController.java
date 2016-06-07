package controllers;

import blocks.context.tables.EntityTable;
import blocks.context.tables.Row;
import model.Record;
import pages.SigintPage;

import java.util.ArrayList;
import java.util.List;

public abstract class TableController extends PageController {

    protected EntityTable table;

    public TableController(SigintPage page)  {
        super(page);
        this.table = this.getPage().context().getTable();
    }



    protected abstract Record initFromRow(Row recordRow);



    public void selectRecordinTable(Row recordRow) {
        recordRow.getCheckBox().click();
    }

    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        List<Row> recordRows = table.getEntities();
        for (Row recordRow: recordRows) {
            records.add(initFromRow(recordRow));
        }
        return records;
    }



}
