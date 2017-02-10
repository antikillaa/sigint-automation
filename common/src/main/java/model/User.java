package model;

import abs.TeelaEntity;
import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.WithDataSize;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User extends TeelaEntity {

    private String name;
    @JsonProperty("display_name")
    private String displayName;
    @WithDataSize(10)
    private String password;
    @DataIgnore
    @JsonProperty("new_password")
    private String newPassword;
    @DataIgnore
    private String phone;
    @JsonProperty("staff_id")
    private String staffId;
    @DataIgnore
    private List<String> roles = new ArrayList<>();
    @DataIgnore
    private List<String> languages = new ArrayList<>();
    @DataIgnore
    @JsonProperty("user_group_ids")
    private List<String> userGroupIds = new ArrayList<>();
    @DataIgnore
    @JsonProperty("expanded_permissions")
    private List<String> expandedPermissions = new ArrayList<>();
    @DataIgnore
    @JsonProperty("expanded_roles")
    private List<String> expandedRoles = new ArrayList<>();
    
    
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

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
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

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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

    public void setUserGroupIds(List<String> userGroupIds) {
        this.userGroupIds = userGroupIds;
    }

    public List<String> getExpandedPermissions() {
        return expandedPermissions;
    }

    public void setExpandedPermissions(List<String> expandedPermissions) {
        this.expandedPermissions = expandedPermissions;
    }

    public List<String> getExpandedRoles() {
        return expandedRoles;
    }

    public void setExpandedRoles(List<String> expandedRoles) {
        this.expandedRoles = expandedRoles;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
