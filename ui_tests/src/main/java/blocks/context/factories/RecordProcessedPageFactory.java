package blocks.context.factories;

import blocks.context.ContextPageFactory;
import blocks.context.tables.EntityTable;
import blocks.context.tables.records.RecordsProcessedTable;
import blocks.context.toolbars.EntityToolbar;
import blocks.context.toolbars.SearchToolbar;

public class RecordProcessedPageFactory implements ContextPageFactory {

    @Override
    public EntityTable createTable() {
        return new RecordsProcessedTable();
    }

    @Override
    public EntityToolbar createToolBar() {
        return null;
    }

    @Override
    public SearchToolbar createSearchPanel() {
        return null;
    }
}
