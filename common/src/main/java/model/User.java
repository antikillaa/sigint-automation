package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class User extends TeelaEntity {

    private String name;
    @JsonProperty("display_name")
    private String displayName;
    private String password;
    private String phone;
    @JsonProperty("staff_id")
    private String staffId;
    private List<String> roles;
    private List<String> languages;
    @JsonProperty("user_group_ids")
    private List<String> userGroupIds;
    @JsonProperty("expanded_permissions")
    private List<String> expandedPermissions;
    @JsonProperty("expanded_roles")
    private List<String> expandedRoles;
    
    
    public String toString() {
        return String.format("name:%s, password:%s", name, password);
    }

    public boolean hasRole(String role) {
        if (roles == null || role == null) {
            return false;
        }
        return roles.contains(role);
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

    public List<String> getExpandedPermissions() {
        return expandedPermissions;
    }

    public User setExpandedPermissions(List<String> expandedPermissions) {
        this.expandedPermissions = expandedPermissions;
        return this;
    }

    public List<String> getExpandedRoles() {
        return expandedRoles;
    }

    public User setExpandedRoles(List<String> expandedRoles) {
        this.expandedRoles = expandedRoles;
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
