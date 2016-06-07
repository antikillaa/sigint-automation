package controllers.records.processed_page;

import blocks.context.tables.Row;
import controllers.TableController;
import model.Record;
import pages.SigintPage;

public class RecordsTableProcessedController extends TableController {


    public RecordsTableProcessedController(SigintPage page) {
        super(page);
    }

    @Override
    protected Record initFromRow(Row recordRow) {
        return null;
    }
}
