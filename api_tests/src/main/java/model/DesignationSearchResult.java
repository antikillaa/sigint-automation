package model;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class DesignationSearchResult extends EntityListResult<Designation> {

  @Override
  @JsonProperty("content")
  public List<Designation> getResult() {
    return super.getResult();
  }

  @Override
  @JsonProperty("content")
  public void setResult(List<Designation> result) {
    super.setResult(result);
  }
}