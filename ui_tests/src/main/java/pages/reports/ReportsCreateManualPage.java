package pages.reports;

import blocks.context.Context;
import elements.ReportSelect;
import elements.Select;
import app_context.AppContext;
import org.openqa.selenium.By;

public class ReportsCreateManualPage extends ReportsPage {

    public static final String url = String.format("%s/#/app/reports/create-manual",
            AppContext.getContext().environment().getSigintHost());

    @Override
    public Context context() {
        return null;
    }


    public Select getRecordTypeSelect() {
        return new ReportSelect(getForm()
                .$(By.xpath("//select[@value.bind='record.type']")));
    }

    public Select getSourceSelect() {
        return new ReportSelect(getForm()
                .$(By.xpath(".//select[@value.bind='record.sourceId']")));
    }

    public ReportsCreateManualPage selectRecordType(String recordType) {
        getRecordTypeSelect().selectOptionbyValue(recordType);
        return this;
    }

    public ReportsCreateManualPage selectSourceType(String sourceType) {
        getSourceSelect().selectOptionByText(sourceType);
        return this;
    }
}
