package ae.pegasus.framework.constants.special.reports_requests.operator_report;

import ae.pegasus.framework.constants.controls.ControlType;

import static ae.pegasus.framework.constants.controls.ControlType.*;

public enum OperatorMasterReportField {
    ORGANIZATION_UNITS("Responsible Org. Unit", DROPDOWN_WITH_SEARCH),
    FILE_OR_CASE_NAME("Case/File name", FILE_SELECTOR),
    REQUEST_NUMBER("Request Number", DROPDOWN_WITH_SEARCH, true),
    CREATED_FOR("Created For", INPUT),
    DESCRIPTION("Description", TEXT_AREA),
    CONSIDERATIONS("Considerations", TEXT_AREA),
    RECOMMENDATIONS("Recommendations", TEXT_AREA),
    OVERVIEW("Overview", TEXT_AREA),
    RESULT("Results", TEXT_AREA),
    ATTACHEDREPORTHEADER("Originating Reports" , TEXT_AREA),
    VIEW("View", DROPDOWN_WITH_SEARCH);

    private final String fieldName;
    private final ControlType controlType;
    private final boolean isWithLoading;

    OperatorMasterReportField(String fieldName, ControlType controlType) {
        this(fieldName, controlType, false);
    }

    OperatorMasterReportField(String fieldName, ControlType controlType, boolean isWithLoading) {
        this.fieldName = fieldName;
        this.controlType = controlType;
        this.isWithLoading = isWithLoading;
    }

    public String getFieldName() {
        return fieldName;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public boolean isWithLoading() {
        return isWithLoading;
    }
}
