package pages.reports;

import blocks.context.Context;
import blocks.context.factories.ReportsAllPageFactory;
import pages.ContextPage;
import pages.SigintPage;

public class ReportsAllPage extends SigintPage implements ContextPage {

    private Context context;


    @Override
    public Context context() {
        if (context == null) {
            context = new ReportsAllContext();
        }
        return context;
    }


    private class ReportsAllContext extends Context {
        public ReportsAllContext() {
            super(new ReportsAllPageFactory());
        }
    }
}
