package pages.records;

import blocks.context.Context;
import blocks.context.factories.RecordProcessedPageFactory;
import blocks.context.tables.records.RecordsAllTable;
import model.AppContext;
import pages.ContextPage;
import pages.SigintPage;

import static com.codeborne.selenide.Selenide.page;

public class RecordsProcessedPage extends SigintPage implements ContextPage{

    public static final String url = String.format("%s/#/app/records/processed",
            AppContext.getContext().environment().getSigintHost());
    private RecordsAllTable table = page(RecordsAllTable.class);
    private Context context;

    public RecordsAllTable getTable() {
        return table;
    }

    @Override
    public Context context() {
        if (context == null) {
            context = new RecordsProcessedContext();
        }
        return context;
    }

    private class RecordsProcessedContext extends Context {
        RecordsProcessedContext() {
            super(new RecordProcessedPageFactory());
        }
    }
}
