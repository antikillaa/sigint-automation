package pages.reports;

import model.AppContext;

public class ReportsCreateManualPage extends ReportsPage {

    public static final String url = String.format("%s/#/app/reports/create-manual",
            AppContext.getContext().environment().getSigintHost());

}
