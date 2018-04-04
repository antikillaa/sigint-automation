package ae.pegasus.framework.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SearchEntity extends AbstractEntity {

    @JsonProperty("@type")
    private String type;
    private DataSourceCategory eventFeed;
    private List<String> sources;
    private String sourceType;
    private String recordType;
    private String subSourceType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataSourceCategory getEventFeed() {
        return eventFeed;
    }

    public void setEventFeed(DataSourceCategory eventFeed) {
        this.eventFeed = eventFeed;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getSubSourceType() {
        return subSourceType;
    }

    public void setSubSourceType(String subSourceType) {
        this.subSourceType = subSourceType;
    }
}
