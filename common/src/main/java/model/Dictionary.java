package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Dictionary {

    private List<DictionaryRecordType> recordTypes;
    private List<Source> sources;
    private List<DictionarySourceType> sourceTypes;

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public List<DictionaryRecordType> getRecordTypes() {
        return recordTypes;
    }

    public void setRecordTypes(List<DictionaryRecordType> recordTypes) {
        this.recordTypes = recordTypes;
    }

    public List<DictionarySourceType> getSourceTypes() {
        return sourceTypes;
    }

    public void setSourceTypes(List<DictionarySourceType> sourceTypes) {
        this.sourceTypes = sourceTypes;
    }

}
