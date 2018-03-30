package ae.pegasus.framework.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

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