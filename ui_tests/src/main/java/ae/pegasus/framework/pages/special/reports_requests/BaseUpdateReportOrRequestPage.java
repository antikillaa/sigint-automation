package ae.pegasus.framework.pages.special.reports_requests;

import com.codeborne.selenide.SelenideElement;
import ae.pegasus.framework.constants.controls.ControlType;
import ae.pegasus.framework.constants.special.reports_requests.ReportAndRequestAction;
import ae.pegasus.framework.constants.special.reports_requests.ReportAndRequestField;
import ae.pegasus.framework.elements.controls.dropdown.DropDown;
import ae.pegasus.framework.elements.controls.dropdown.FileSelector;
import ae.pegasus.framework.pages.basic_pages.api.BasePage;
import ae.pegasus.framework.utils.PageUtils;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static ae.pegasus.framework.constants.CommonXPaths.INTERNAL_LOADING_XPATH;

public abstract class BaseUpdateReportOrRequestPage extends BaseReportOrRequestPage {

    private SelenideElement getBasicDataElement(ReportAndRequestField field) {
        return getBasicDataElement(field.getControlType(), getFieldByLabel(field.getFieldName()));
    }

    protected void setMultiValueData(SelenideElement basicDataElement, ControlType controlType, boolean isWithLoading, String...values) {
        switch (controlType) {
            case TEXT_AREA:
                PageUtils.clearAndType(basicDataElement, String.join("\n", values));
                break;
            case DROPDOWN_WITH_SEARCH:
                new DropDown(basicDataElement, isWithLoading).searchAndSelectItems(values);
                break;
            case FILE_SELECTOR:
                new FileSelector(basicDataElement).selectItems(values);
                break;
            default:
                throw new IllegalArgumentException("Unexpected control type '" + controlType + "' for setting of multi-value data");
        }
    }

    public void setMultiValueData(ReportAndRequestField field, String...values) {
        setMultiValueData(getBasicDataElement(field),
                field.getControlType(),
                field.isWithLoading(),
                values);
    }

    protected void setSingleValueData(SelenideElement basicDataElement, ControlType controlType, boolean isWithLoading, String value) {
        switch (controlType) {
            case INPUT:
                PageUtils.clearAndType(basicDataElement, value);
                break;
            case SIMPLE_DROPDOWN:
                new DropDown(basicDataElement, isWithLoading).selectItems(value);
                break;
            default:
                throw new IllegalArgumentException("Unexpected control type '" + controlType + "' for setting of single value");
        }
    }

    public void setSingleValueData(ReportAndRequestField field, String value) {
        setSingleValueData(getBasicDataElement(field),
                field.getControlType(),
                field.isWithLoading(),
                value);
    }
}
