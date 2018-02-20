package model;

import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.DataProvider;
import data_for_entity.annotations.WithDataDependencies;
import data_for_entity.data_providers.whitelist.WhiteListIdentifierProvider;
import data_for_entity.data_providers.whitelist.WhiteListTypeProvider;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DesignationMapping extends BaseEntity {

  @DataIgnore
  private Long version;
  @DataProvider(WhiteListTypeProvider.class)
  private WhiteListType type;
  @WithDataDependencies(provider = WhiteListIdentifierProvider.class, fields = {"type"})
  private String identifier;
  @DataIgnore
  private boolean spam;
  @DataIgnore
  private List<String> designations;

  public DesignationMapping copy() {
    DesignationMapping dmCopy = new DesignationMapping();
    dmCopy.setVersion(this.version);
    dmCopy.setType(this.type);
    dmCopy.setIdentifier(this.identifier);
    dmCopy.setSpam(this.spam);
    dmCopy.setDesignations(this.designations);

    return dmCopy;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public WhiteListType getType() {
    return type;
  }

  public void setType(WhiteListType type) {
    this.type = type;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public boolean isSpam() {
    return spam;
  }

  public void setSpam(boolean spam) {
    this.spam = spam;
  }

  public List<String> getDesignations() {
    return designations;
  }

  public void setDesignations(List<String> designations) {
    this.designations = designations;
  }
}
