package ae.pegasus.framework.constants.special.results_details;

public enum SearchResultsDetailsField {
    DURATION("Duration"),
    LANGUAGE("Language"),
    PHONE_NUMBER("Phone Number"),
    IMSI("IMSI"),
    TMSI("TMSI"),
    COUNTRY("Country"),
    EMAIL("E-mail Address"),
    SOURCE("Source"),
    SUBSOURCE("Subsource"),
    TYPE("Type"),
    SUBTYPE("Subtype"),
    EVENT_TIME("Event Time"),
    END_TIME("End Time"),
    PRIORITY("Priority"),
    STATUS("Status"),
    OWNER("Owner"),
    DESIGNATIONS("Designations");

    private final String fieldName;

    SearchResultsDetailsField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
