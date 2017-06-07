package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WhitelistFilter extends SearchFilter<Whitelist>{

  private String query;
  private List<String> types;
  private Date updatedAfter;

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public List<String> getTypes() {
    return types;
  }

  public void setTypes(List<String> types) {
    this.types = types;
  }

  public Date getUpdatedAfter() {
    return updatedAfter;
  }

  public void setUpdatedAfter(Date updatedAfter) {
    this.updatedAfter = updatedAfter;
  }

  @Override
  public boolean isAppliedToEntity(Whitelist entity) {
    return activeFilter.isAppliedToEntity(entity);
  }


  private class UpdateAfterFilter extends SearchFilter<Whitelist> {

    UpdateAfterFilter(Date value) {
      updatedAfter = value;
    }

    @Override
    public boolean isAppliedToEntity(Whitelist entity) {
      return entity.getModifiedAt().after(updatedAfter);
    }
  }

  private class IdentifierFilter extends SearchFilter<Whitelist> {

    IdentifierFilter(String value) {
      query = value;
    }

    @Override
    public boolean isAppliedToEntity(Whitelist entity) {
      return entity.getIdentifier().equals(query);
    }
  }

  private class DescriptionFilter extends SearchFilter<Whitelist> {

    DescriptionFilter(String value) {
      query = value;
    }

    @Override
    public boolean isAppliedToEntity(Whitelist entity) {
      return entity.getDescription().equals(query);
    }
  }

  private class TypeFilter extends SearchFilter<Whitelist> {

    TypeFilter(String value) {
      types = new ArrayList<>();
      types.add(value);
    }

    @Override
    public boolean isAppliedToEntity(Whitelist entity) {
      return types.contains(String.valueOf(entity.getType()));
    }
  }

  private class EmptyFilter extends SearchFilter<Whitelist> {

    EmptyFilter() {
      query = null;
      updatedAfter = null;
      types = null;
    }

    @Override
    public boolean isAppliedToEntity(Whitelist entity) {
      return true;
    }
  }

  /**
   * Init or update whitelist filter.
   * Filter is used in WhitelistService for receive list of whitelists.
   *
   * @param criteria filter field
   * @param value value of filter
   * @return filter entity for whitelists
   */
  public WhitelistFilter filterBy(String criteria, String value) {
    if (criteria.toLowerCase().equals("identifier")) {
      this.setActiveFilter(this.new IdentifierFilter(value));
    } else if (criteria.toLowerCase().equals("description")) {
      this.setActiveFilter(this.new DescriptionFilter(value));
    } else if (criteria.toLowerCase().equals("type")) {
      this.setActiveFilter(this.new TypeFilter(value));
    } else if (criteria.toLowerCase().equals("updatedafter")) {
      this.setActiveFilter(this.new UpdateAfterFilter(new Date(Long.valueOf(value))));
    } else if (criteria.toLowerCase().equals("empty")) {
      this.setActiveFilter(this.new EmptyFilter());
    } else {
      throw new AssertionError("Unknown isAppliedToEntity type");
    }
    return this;
  }
}
