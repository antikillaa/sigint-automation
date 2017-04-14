package model;

import abs.TeelaEntity;
import data_for_entity.annotations.DataIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Organization extends TeelaEntity {

  @DataIgnore
  private String parentTeamId;
  @DataIgnore
  private OrganizationType organizationType;
  private String fullName;
  @DataIgnore
  private Permission defaultPermission;

  public String getParentTeamId() {
    return parentTeamId;
  }

  public void setParentTeamId(String parentTeamId) {
    this.parentTeamId = parentTeamId;
  }

  public OrganizationType getOrganizationType() {
    return organizationType;
  }

  public void setOrganizationType(OrganizationType organizationType) {
    this.organizationType = organizationType;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
