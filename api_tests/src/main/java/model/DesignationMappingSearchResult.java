package model;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class DesignationMappingSearchResult extends EntityListResult<DesignationMapping> {

  @Override
  @JsonProperty("content")
  public List<DesignationMapping> getResult() {
    return super.getResult();
  }

  @Override
  @JsonProperty("content")
  public void setResult(List<DesignationMapping> result) {
    super.setResult(result);
  }
}