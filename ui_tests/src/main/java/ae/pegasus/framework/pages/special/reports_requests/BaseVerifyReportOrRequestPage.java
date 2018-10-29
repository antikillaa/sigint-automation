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

public abstract class BaseVerifyReportOrRequestPage extends BaseReportOrRequestPage {

    protected String getSingleValue(SelenideElement fieldElement, ControlType controlType) {
        switch (controlType) {
            case INPUT:
            case TEXT_AREA:
            case SIMPLE_DROPDOWN:
            case DROPDOWN_WITH_SEARCH:
                return fieldElement.$x("./div").getText().trim();
            default:
                throw new IllegalArgumentException("Control type '" + controlType + "' is not applicable for getting of single value");
        }
    }

    public String getReportOrRequestStatus() {
        return getReportOrRequestStaticInformationBlock().$x(".//div//b").getText().trim();
    }

    public String getSingleValue(ReportAndRequestField field) {
        return getSingleValue(getFieldByLabel(field.getFieldName()), field.getControlType());
    }

    protected List<String> getListOfValues(SelenideElement fieldElement, ControlType controlType) {
        switch (controlType) {
            case FILE_SELECTOR:
                return PageUtils.convertElementsCollectionToStringList(fieldElement.$$x(".//a/span"), true);
            case DROPDOWN_WITH_SEARCH:
                return PageUtils.convertElementsCollectionToStringList(fieldElement.$$x(".//div/span"), true);
            default:
                throw new IllegalArgumentException("Control type '" + controlType + "' is not applicable for getting of list of values");
        }
    }

    public List<String> getListOfValues(ReportAndRequestField field) {
        return getListOfValues(getFieldByLabel(field.getFieldName()), field.getControlType());
    }

    public boolean checkVisibilityOfReportAction (ReportAndRequestAction action) {
        for (SelenideElement button : getReportOrRequestHeader().$$x(".//pg-btn//span/span[1]")) {
            if(button.getText().trim().equalsIgnoreCase(action.getActionName())
                    && button.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkVisibilityOnReportCreation (ReportAndRequestAction action) {
        for (SelenideElement button : $$x(".//pg-btn//span/span[1]")) {
            if(button.getText().trim().equalsIgnoreCase(action.getActionName())
                    && button.isEnabled()) {
                return true;
            }
        }
        return false;
    }
}
