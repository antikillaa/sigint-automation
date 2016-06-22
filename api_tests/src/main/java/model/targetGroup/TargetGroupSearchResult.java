package model.targetGroup;

import model.TargetGroup;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class TargetGroupSearchResult {

    private List<TargetGroup> result;

    public List<TargetGroup> getResult() {
        return result;
    }

    public void setResult(List<TargetGroup> result) {
        this.result = result;
    }
}
