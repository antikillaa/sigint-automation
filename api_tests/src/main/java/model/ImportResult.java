package model;

import java.util.HashMap;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImportResult {

  private Integer imported;
  private Integer created;
  private HashMap<String, String> rejected;

  public Integer getImported() {
    return imported;
  }

  public void setImported(Integer imported) {
    this.imported = imported;
  }

  public Integer getCreated() {
    return created;
  }

  public void setCreated(Integer created) {
    this.created = created;
  }

  public HashMap<String, String> getRejected() {
    return rejected;
  }

  public void setRejected(HashMap<String, String> rejected) {
    this.rejected = rejected;
  }
}
