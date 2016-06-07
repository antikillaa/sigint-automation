package pages.records;

import pages.ContextPage;
import pages.SigintPage;
import blocks.context.Context;
import blocks.context.factories.RecordAllPageFactory;

public class RecordsAllPage extends SigintPage implements ContextPage {

    private Context context;

    public Context context() {
        if (context == null) {
            context = new RecordsAllContext();
        }
        return context;
    }

    private class RecordsAllContext extends Context {
        RecordsAllContext() {
            super(new RecordAllPageFactory());
        }
    }

}
