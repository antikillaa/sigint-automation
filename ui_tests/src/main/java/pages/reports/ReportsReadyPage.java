package pages.reports;

import blocks.context.Context;
import blocks.context.factories.ReportsReadyPageFactory;
import pages.ContextPage;
import pages.SigintPage;

public class ReportsReadyPage extends SigintPage implements ContextPage {

    private Context context;

    @Override
    public Context context() {
        if (context == null) {
            context = new ReportsReadyContext();
        }
        return context;
    }

    private class ReportsReadyContext extends Context {
        public ReportsReadyContext() {
            super(new ReportsReadyPageFactory());
        }
    }
}
