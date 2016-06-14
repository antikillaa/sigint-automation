package pages.reports;

import blocks.context.Context;
import blocks.context.factories.ReportsDraftPageFactory;
import pages.ContextPage;
import pages.SigintPage;

public class ReportsDraftPage extends SigintPage implements ContextPage {

    private Context context;

    @Override
    public Context context() {
        if (context == null) {
            context = new ReportsDraftContext();
        }
        return context;
    }

    private class ReportsDraftContext extends Context {
        ReportsDraftContext() {
            super(new ReportsDraftPageFactory());
        }
    }
}
