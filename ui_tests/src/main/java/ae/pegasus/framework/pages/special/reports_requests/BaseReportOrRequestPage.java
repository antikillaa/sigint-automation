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

public abstract class BaseReportOrRequestPage extends BasePage {

    @Override
    public boolean isPageDisplayed() {
        return false;
    }

    protected SelenideElement getReportOrRequestBase() {
        SelenideElement result = $x("//ux-dialog");
        if (result.isDisplayed()) {
            return result;
        }
        return $x("//router-view");
    }

    protected SelenideElement getFieldByLabel(String fieldLabel) {
        SelenideElement field = getReportOrRequestBase().$x(".//pg-field[@label='" + fieldLabel + "']");
        scrollWindowTo(field);
        return field;
    }

    protected SelenideElement getReportOrRequestHeader() {
        return getReportOrRequestBase().$x(".//div[@class='im-document__header']");
    }

    protected SelenideElement getReportOrRequestStaticInformationBlock() {
        return getReportOrRequestHeader().$x(".//div[contains(@class,'media-body')]");
    }

    protected SelenideElement getMoreActionsDropdown() {
        return getReportOrRequestHeader().$x(".//div[@class='cb-detail__actions']/pg-dropdown");
    }

    @Override
    protected SelenideElement getPageLoading() {
        return getReportOrRequestBase().$x(INTERNAL_LOADING_XPATH);
    }

    public void performAction(ReportAndRequestAction action) {
        if (action.isInMoreAction()) {
            getMoreActionsDropdown().click();
            if (action.getParentAction() != null) {
                SelenideElement parentAction = getActionFromDropDownMenu(action.getParentAction().getActionName());
                getActionFromDropDownSubMenu(parentAction, action.getActionName()).click();
            } else {
                getActionFromDropDownMenu(action.getActionName()).click();
            }
        } else {
            for (SelenideElement button : getReportOrRequestHeader().$$x(".//pg-btn//span/span[1]")) {
                if(button.getText().trim().equalsIgnoreCase(action.getActionName())) {
                    button.click();
                    return;
                }
            }
            throw new IllegalArgumentException("Action '" + action.getActionName() + "' was not found");
        }
    }

    public String getReportOrRequestNumber() {
        return getReportOrRequestStaticInformationBlock().$x(".//h4").getText().trim();
    }
}
