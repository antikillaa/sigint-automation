package model;

public class FileMetaFilter {

  private Query query;

  public Query getQuery() {
    return query;
  }

  public FileMetaFilter setQuery(Query query) {
    this.query = query;
    return this;
  }

  public static class Query {
    String type;
    String field;
    String value;

    public Query(String type, String field, String value) {
      this.type = type;
      this.field = field;
      this.value = value;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getField() {
      return field;
    }

    public void setField(String field) {
      this.field = field;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

}
