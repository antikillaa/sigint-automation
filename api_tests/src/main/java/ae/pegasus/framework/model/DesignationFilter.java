package ae.pegasus.framework.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DesignationFilter extends SearchFilter<Designation> {

  private String name;
  private Date updatedAfter;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getUpdatedAfter() {
    return updatedAfter;
  }

  public void setUpdatedAfter(Date updatedAfter) {
    this.updatedAfter = updatedAfter;
  }

  @Override
  public boolean isAppliedToEntity(Designation entity) {
    return activeFilter.isAppliedToEntity(entity);
  }

  private class UpdateAfterFilter extends SearchFilter<Designation> {

    UpdateAfterFilter(Date value) {
      updatedAfter = value;
    }

    @Override
    public boolean isAppliedToEntity(Designation entity) {
      return entity.getModifiedAt().after(updatedAfter);
    }
  }


  private class NameFilter extends SearchFilter<Designation> {

    NameFilter(String value) {
      name = value;
    }

    @Override
    public boolean isAppliedToEntity(Designation entity) {
      return entity.getName().equals(name);
    }
  }


  private class EmptyFilter extends SearchFilter<Designation> {

    EmptyFilter() {
      name = null;
      updatedAfter = null;
    }

    @Override
    public boolean isAppliedToEntity(Designation entity) {
      return true;
    }
  }

  /**
   * Init or update designation filter.
   * Filter is used in DesignationService for receive list of designations.
   *
   * @param criteria filter field
   * @param value value of filter
   * @return filter entity for designations
   */
  public DesignationFilter filterBy(String criteria, String value) {
    if (criteria.toLowerCase().equals("name")) {
      this.setActiveFilter(this.new NameFilter(value));
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

