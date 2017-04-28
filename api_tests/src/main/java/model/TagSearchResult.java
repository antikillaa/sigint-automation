package model;

import abs.EntityList;
import abs.EntityListResult;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class TagSearchResult extends EntityListResult<Tag> {

    @Override
    @JsonProperty("content")
    public EntityList<Tag> getResult() {
        return super.getResult();
    }

    @Override
    @JsonProperty("content")
    public void setResult(List<Tag> result) {
        super.setResult(result);
    }
}
