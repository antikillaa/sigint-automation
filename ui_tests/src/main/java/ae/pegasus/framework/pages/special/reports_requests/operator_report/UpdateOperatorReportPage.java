package ae.pegasus.framework.pages.special.reports_requests.operator_report;

import com.codeborne.selenide.SelenideElement;
import ae.pegasus.framework.constants.special.reports_requests.operator_report.OperatorReportField;
import ae.pegasus.framework.pages.special.reports_requests.BaseUpdateReportOrRequestPage;

public class UpdateOperatorReportPage extends BaseUpdateReportOrRequestPage {
    private SelenideElement getBasicDataElement(OperatorReportField field) {
        return getBasicDataElement(field.getControlType(), getFieldByLabel(field.getFieldName()));
    }

    public void setMultiValueData(OperatorReportField field, String...values) {
        setMultiValueData(getBasicDataElement(field),
                field.getControlType(),
                field.isWithLoading(),
                values);
    }

    public void setSingleValueData(OperatorReportField field, String value) {
        setSingleValueData(getBasicDataElement(field),
                field.getControlType(),
                field.isWithLoading(),
                value);
    }
}
