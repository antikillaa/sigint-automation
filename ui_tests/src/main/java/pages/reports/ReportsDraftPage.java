package pages.reports;

import blocks.context.Context;
import model.AppContext;

public class ReportsDraftPage extends ReportSearchPage {

    public static final String url = String.format("%s/#/app/reports/draft",
            AppContext.getContext().environment().getSigintHost());


    @Override
    public Context context() {
        return null;
    }
}
