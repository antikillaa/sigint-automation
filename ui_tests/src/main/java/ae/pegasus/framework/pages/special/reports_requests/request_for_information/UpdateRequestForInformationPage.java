package ae.pegasus.framework.pages.special.reports_requests.request_for_information;

import com.codeborne.selenide.SelenideElement;
import ae.pegasus.framework.constants.special.modal_dialog.ModalDialogButton;
import ae.pegasus.framework.constants.special.modal_dialog.ModalDialogField;
import ae.pegasus.framework.constants.special.reports_requests.ReportAndRequestAction;
import ae.pegasus.framework.constants.special.reports_requests.request_for_information.RequestForInformationField;
import ae.pegasus.framework.pages.Pages;
import ae.pegasus.framework.pages.special.reports_requests.BaseUpdateReportOrRequestPage;

import static com.codeborne.selenide.Condition.visible;

public class UpdateRequestForInformationPage extends BaseUpdateReportOrRequestPage {
    private SelenideElement getBasicDataElement(RequestForInformationField field) {
        return getBasicDataElement(field.getControlType(), getFieldByLabel(field.getFieldName()));
    }

    public void setMultiValueData(RequestForInformationField field, String...values) {
        setMultiValueData(getBasicDataElement(field),
                field.getControlType(),
                field.isWithLoading(),
                values);
    }

    public void setSingleValueData(RequestForInformationField field, String value) {
        setSingleValueData(getBasicDataElement(field),
                field.getControlType(),
                field.isWithLoading(),
                value);
    }

    public void deleteRequestForInformation(String...commentLines) {
        if(commentLines.length == 0) {
            throw new IllegalArgumentException("At least one comment line should be provided to delete request for information");
        }

        closeAllPopUps();
        performAction(ReportAndRequestAction.DELETE_RFI);
        Pages.modalDialogPage().clickButton(ModalDialogButton.YES);
        Pages.modalDialogPage().
                setDataToField(ModalDialogField.COMMENTS,
                        String.join("\n", commentLines));
        Pages.modalDialogPage().clickButton(ModalDialogButton.OK);
        getSuccessPopUp().shouldBe(visible);
    }

    @Override
    public void performAction(ReportAndRequestAction action) {
        switch (action) {
            case RECALL:
                closeAllPopUps();
                super.performAction(action);
                getSuccessPopUp().shouldBe(visible);
                break;
            default:
                super.performAction(action);
                break;
        }
    }
}
