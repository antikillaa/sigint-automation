package ae.pegasus.framework.pages.special.reports_requests.request_for_information;

import com.codeborne.selenide.SelenideElement;
import ae.pegasus.framework.constants.controls.ControlType;
import ae.pegasus.framework.constants.special.reports_requests.request_for_information.RequestForInformationField;
import ae.pegasus.framework.pages.special.reports_requests.BaseVerifyReportOrRequestPage;
import ae.pegasus.framework.utils.PageUtils;

import java.util.List;

public class VerifyRequestForInformationPage extends BaseVerifyReportOrRequestPage {

    public String getSingleValue(RequestForInformationField field) {
        return getSingleValue(getFieldByLabel(field.getFieldName()), field.getControlType());
    }

    @Override
    protected List<String> getListOfValues(SelenideElement fieldElement, ControlType controlType) {
        switch (controlType) {
            case DROPDOWN_WITH_SEARCH:
                return PageUtils.convertElementsCollectionToStringList(fieldElement.$$x(".//div/div/div"), true);
            default:
                return super.getListOfValues(fieldElement, controlType);
        }
    }

    public List<String> getListOfValues(RequestForInformationField field) {
        return getListOfValues(getFieldByLabel(field.getFieldName()), field.getControlType());
    }
}
