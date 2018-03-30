package ae.pegasus.framework.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class TargetGroupSearchResult extends EntityListResult<TargetGroup> {

    @Override
    @JsonProperty("content")
    public List<TargetGroup> getResult() {
        return super.getResult();
    }

    @Override
    @JsonProperty("content")
    public void setResult(List<TargetGroup> result) {
        super.setResult(result);
    }
}
