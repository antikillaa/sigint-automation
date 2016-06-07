package pages.reports;

import blocks.context.Context;
import model.AppContext;

public class ReportsCreatePage extends ReportsPage {

    public static final String url = String.format("%s/#/app/reports/create",
            AppContext.getContext().environment().getSigintHost());

    @Override
    public Context context() {
        return null;
    }
}
