package model;

public class MetaProperties {
    
    private RecordType recordType;
    private SourceType sourceType;
    private String error;
    
    public RecordType getRecordType() {
        return recordType;
    }
    
    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }
    
    public SourceType getSourceType() {
        return sourceType;
    }
    
    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
