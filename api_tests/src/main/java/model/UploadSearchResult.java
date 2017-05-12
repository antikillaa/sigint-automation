package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class UploadSearchResult extends EntityListResult<Process> {

    @Override
    @JsonProperty("content")
    public List<Process> getResult() {
        return super.getResult();
    }

    @Override
    @JsonProperty("content")
    public void setResult(List<Process> result) {
        super.setResult(result);
    }
}
