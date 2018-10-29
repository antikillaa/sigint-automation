package ae.pegasus.framework.constants.special.results_details;

public enum SearchResultsDetailsSection {
    RECORD_DETAILS("Record Details"),
    FROM("From"),
    TO("To"),
    RECORD_METADATA("Record Metadata"),
    RECORD_ASSESSMENT("Record Assessment"),
    NONE("");

    private final String sectionName;

    SearchResultsDetailsSection(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionName() {
        return sectionName;
    }
}
