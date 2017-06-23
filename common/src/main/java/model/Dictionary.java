package model;

import java.util.List;

public class Dictionary {

    private List<RecordType> recordTypes;
    private List<Source> sources;
    private List<SourceType> sourceTypes;

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public List<RecordType> getRecordTypes() {
        return recordTypes;
    }

    public void setRecordTypes(List<RecordType> recordTypes) {
        this.recordTypes = recordTypes;
    }

    public List<SourceType> getSourceTypes() {
        return sourceTypes;
    }

    public void setSourceTypes(List<SourceType> sourceTypes) {
        this.sourceTypes = sourceTypes;
    }

    public SourceType getSourceType(String sType) {
        return sourceTypes.stream()
                .filter(sourceType -> sourceType.getType() != null && sourceType.getType().equals(sType))
                .findAny().orElse(null);
    }

    public RecordType getRecordType(String rType) {
        return recordTypes.stream()
                .filter(recordType -> recordType.getType() != null && recordType.getType().equals(rType))
                .findAny().orElse(null);
    }
}
