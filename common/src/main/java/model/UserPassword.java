package model;

import static utils.StringUtils.capitalizeFirstLetter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserPassword {

  private String name;
  private String oldPassword;
  private String newPassword;
  private String id;

  public UserPassword(User user) {
    this.name = capitalizeFirstLetter(user.getName());
    this.oldPassword = user.getPassword();
    this.newPassword = user.getNewPassword();

    // save User's id for password update via /api/auth/users/{id} call
    this.id = user.getId();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}
