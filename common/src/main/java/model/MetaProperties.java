package model;

public class MetaProperties {
    
    private RecordType recordType;
    private SourceType sourceType;
    private String error;
    private String processId;
    
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
