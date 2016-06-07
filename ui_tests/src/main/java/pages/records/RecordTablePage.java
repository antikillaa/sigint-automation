package pages.records;

import pages.SigintPage;
import blocks.context.tables.records.RecordsAllTable;

import static com.codeborne.selenide.Selenide.page;

public abstract class RecordTablePage extends SigintPage{

    private RecordsAllTable table = page(RecordsAllTable.class);

    public RecordsAllTable getTable() {
        return table;
    }
}
