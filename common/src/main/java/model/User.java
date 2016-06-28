package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class User extends TeelaEntity {

    private Date createdAt;
    private Date modifiedAt;
    private String modifiedBy;
    private String name;
    @JsonProperty("display_name")
    private String displayName;
    private String password;
    private String phone;
    private String token;
    @JsonProperty("staff_id")
    private String staffId;
    private List<String> roles;
    private List<String> languages;
    @JsonProperty("user_group_ids")
    private List<String> userGroupIds;

    public boolean hasRole(String role) {
        if (roles == null || role == null) {
            return false;
        }
        return roles.contains(role);
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getDisplayName() {
        return displayName;
    }


    public User setDisplayName(String name) {
        this.displayName = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String userName) {
        this.name = userName;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStaffId() {
        return staffId;
    }

    public User setStaffId(String staffId) {
        this.staffId = staffId;
        return this;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getUserGroupIds() {
        return userGroupIds;
    }

    public User setUserGroupIds(List<String> userGroupIds) {
        this.userGroupIds = userGroupIds;
        return this;
    }

    public User generate() {
        this
                .setName(RandomStringUtils.randomAlphabetic(8).toLowerCase())
                .setDisplayName(RandomStringUtils.randomAlphabetic(8))
                .setStaffId(RandomStringUtils.randomAlphanumeric(6))
                .setPassword(RandomStringUtils.randomAlphanumeric(8));
        return this;
    }

}
