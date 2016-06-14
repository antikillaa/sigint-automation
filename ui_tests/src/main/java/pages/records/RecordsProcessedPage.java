package pages.records;

import blocks.context.Context;
import blocks.context.factories.RecordProcessedPageFactory;
import pages.ContextPage;
import pages.SigintPage;

public class RecordsProcessedPage extends SigintPage implements ContextPage{

    private Context context;

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
