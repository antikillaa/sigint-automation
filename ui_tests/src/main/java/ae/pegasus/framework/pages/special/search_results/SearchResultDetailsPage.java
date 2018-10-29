package ae.pegasus.framework.pages.special.search_results;

import com.codeborne.selenide.SelenideElement;
import ae.pegasus.framework.constants.special.results_details.SearchResultsDetailsAction;
import ae.pegasus.framework.constants.special.results_details.SearchResultsDetailsField;
import ae.pegasus.framework.constants.special.results_details.SearchResultsDetailsSection;
import ae.pegasus.framework.constants.special.results_details.SearchResultsTranslationType;
import ae.pegasus.framework.pages.basic_pages.api.BaseModalDialogPage;

import static com.codeborne.selenide.Condition.visible;

public class SearchResultDetailsPage extends BaseModalDialogPage {
    private final String SAVE_BUTTON_LABEL = "Save";
    private final String EDIT_BUTTON_LABEL = "Edit";

    @Override
    public boolean isPageDisplayed() {
        return false;
    }

    private SelenideElement getActionMenuOpenButton() {
        return getDialogBody().$x(".//div[@class='cb-detail__actions']/pg-dropdown");
    }

    public void performAction(SearchResultsDetailsAction action) {
        getActionMenuOpenButton().click();
        if (action.getParentAction() != null) {
            SelenideElement parentAction = getActionFromDropDownMenu(action.getParentAction().getActionName());
            getActionFromDropDownSubMenu(parentAction, action.getActionName()).click();
        } else {
            getActionFromDropDownMenu(action.getActionName()).click();
        }
        waitDialogLoading();
    }

    private SelenideElement getContentBase() {
        return getDialogBody().$x(".//div[@slot='content']");
    }

    private SelenideElement getRecordDetailsBase() {
        return getDialogBody().$x(".//div[contains(@class,'cb-detail__body-right')]");
    }

    private SelenideElement getFieldsTable(SearchResultsDetailsSection section) {
        switch (section) {
            case NONE:
                return getRecordDetailsBase().$x(".//div[@slot='sidebar']/table[1]//tbody");
            case RECORD_METADATA:
                return getRecordDetailsBase().$x(".//cb-record-metadata-detail//tbody");
            case RECORD_ASSESSMENT:
                return getRecordDetailsBase().$x(".//cb-record-assessment-detail//tbody");
            default:
               for(SelenideElement sectionName : getRecordDetailsBase().$$x(".//div[@slot='sidebar']/h5")) {
                   if (sectionName.getText().trim().equalsIgnoreCase(section.getSectionName())) {
                        return sectionName.$x("./following-sibling::table//tbody");
                   }
               }
               throw new IllegalArgumentException("Section with name '" + section.getSectionName() + "' was not found");
        }
    }

    private String getFieldValueFromTable(SelenideElement fieldsTable, SearchResultsDetailsField field) {
        for(SelenideElement fieldElement : fieldsTable.$$x(".//tr")) {
            if (fieldElement.$x("./td[1]//label").getText().trim().equalsIgnoreCase(field.getFieldName())) {
                return fieldElement.$x("./td[2]").getText().trim();
            }
        }
        throw new IllegalArgumentException("Field with name '" + field.getFieldName() + "' was not found");
    }

    public String getFieldValue(SearchResultsDetailsSection section, SearchResultsDetailsField field) {
        return getFieldValueFromTable(getFieldsTable(section), field);
    }

    public boolean isFieldDisplayed(SearchResultsDetailsSection section, SearchResultsDetailsField field) {
        try {
            getFieldValueFromTable(getFieldsTable(section), field);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String getSMSText() {
        return getDialogBody().$x(".//cb-phone-view//div[@class='sms-detail__text']").getText();
    }

    public String getAttachmentDetails() {
        return getDialogBody().$x("//div[@slot='content']//div[@class='para']").getText();
    }

    private SelenideElement getTranslationBase(SearchResultsTranslationType translationType) {
        return getContentBase().$x(".//h6[text()='" + translationType.getTranslationType() + "']/..");
    }

    private SelenideElement getTranslationTextArea(SearchResultsTranslationType translationType) {
        return getTranslationBase(translationType).$x(".//textarea");
    }

    private SelenideElement getTranslationButton(SearchResultsTranslationType translationType, String buttonLabel) {
        return getTranslationBase(translationType).$x(".//pg-btn[@label='" + buttonLabel + "']");
    }

    public boolean isTranslationDisplayed(SearchResultsTranslationType translationType) {
        return getTranslationBase(translationType).isDisplayed();
    }

    public String getTranslation(SearchResultsTranslationType translationType) {
        String result = "";
        if (isTranslationDisplayed(translationType)) {
            if (getTranslationButton(translationType, EDIT_BUTTON_LABEL).isDisplayed()) {
               result = getTranslationBase(translationType).$x("./div/div").getText().trim();
            } else {
                result = getTranslationTextArea(translationType).getValue().trim();
            }
        }
        return result;
    }

    public void saveTranslation(SearchResultsTranslationType translationType) {
        closeAllPopUps();
        getTranslationButton(translationType, SAVE_BUTTON_LABEL).click();
        waitDialogLoading();
        getSuccessPopUp().shouldBe(visible);
    }

    public void clearTranslation(SearchResultsTranslationType translationType) {
        if (isTranslationDisplayed(translationType)) {
            getTranslationButton(translationType, EDIT_BUTTON_LABEL).click();
            getTranslationTextArea(translationType).setValue("");
            saveTranslation(translationType);
        }
    }

    public void setTranslation(SearchResultsTranslationType translationType, String translationToSet) {
        SelenideElement editButton = getTranslationButton(translationType, EDIT_BUTTON_LABEL);
        if (editButton.isDisplayed()) {
            editButton.click();
        }
        getTranslationTextArea(translationType).setValue(translationToSet.trim());
        saveTranslation(translationType);
    }
}
