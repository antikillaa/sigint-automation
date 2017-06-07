package model;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class WhitelistSearchResult extends EntityListResult<Whitelist> {

  @Override
  @JsonProperty("content")
  public List<Whitelist> getResult() {
    return super.getResult();
  }

  @Override
  @JsonProperty("content")
  public void setResult(List<Whitelist> result) {
    super.setResult(result);
  }
}
