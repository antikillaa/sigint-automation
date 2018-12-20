package ae.pegasus.framework.pages.special.reports_requests.operator_report;

import com.codeborne.selenide.SelenideElement;
import ae.pegasus.framework.constants.special.reports_requests.operator_report.OperatorMasterReportField;
import ae.pegasus.framework.pages.special.reports_requests.BaseUpdateIMPage;

public class UpdateOperatorReportPage extends BaseUpdateIMPage {
    private SelenideElement getBasicDataElement(OperatorMasterReportField field) {
        return getBasicDataElement(field.getControlType(), getFieldByLabel(field.getFieldName()));
    }

    public void setMultiValueData(OperatorMasterReportField field, String...values) {
        setMultiValueData(getBasicDataElement(field),
                field.getControlType(),
                field.isWithLoading(),
                values);
    }

    public void setSingleValueData(OperatorMasterReportField field, String value) {
        setSingleValueData(getBasicDataElement(field),
                field.getControlType(),
                field.isWithLoading(),
                value);
    }
}
