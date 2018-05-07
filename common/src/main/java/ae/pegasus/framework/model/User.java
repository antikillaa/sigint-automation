package ae.pegasus.framework.model;

import ae.pegasus.framework.data_for_entity.annotations.DataIgnore;
import ae.pegasus.framework.data_for_entity.annotations.DataProvider;
import ae.pegasus.framework.data_for_entity.annotations.WithDataSize;
import ae.pegasus.framework.data_for_entity.data_providers.EmailProvider;
import ae.pegasus.framework.data_for_entity.data_providers.user_password.UserPasswordProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User extends Organization {

    @DataProvider(EmailProvider.class)
    private String name;
    @DataProvider(EmailProvider.class)
    // TODO: remove email field, cause it's similar to name
    private String email;
    @DataIgnore
    private List<String> languages = new ArrayList<>();
    @DataIgnore
    private String imageURL;
    private String staffId;
    @DataIgnore
    private Date lastLoginDate;
    @DataIgnore
    private Boolean inactive;

    @DataProvider(UserPasswordProvider.class)
    @WithDataSize(10)
    private String password;
    @DataIgnore
    private String newPassword;
    @DataIgnore
    private String adminPassword;

    @DataIgnore
    private Boolean isDeleted = false;
    @DataIgnore
    private Boolean locked = false;
    @DataIgnore
    private UserPermission defaultPermission = new UserPermission();
    @DataIgnore
    private UserPermission effectivePermission = new UserPermission();
    @DataIgnore
    private Map<String, List<String>> parentTeams;

    public User() {
        setOrganizationType(OrganizationType.USER);
    }

    @DataIgnore
    private List<String> roles = new ArrayList<>();

    public String toString() {
        return String.format("name:%s, password:%s", name, password);
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public UserPermission getDefaultPermission() {
        return defaultPermission;
    }

    public void setDefaultPermission(UserPermission defaultPermission) {
        this.defaultPermission = defaultPermission;
    }

    public Map<String, List<String>> getParentTeams() {
        return parentTeams;
    }

    public void setParentTeams(Map<String, List<String>> parentTeams) {
        this.parentTeams = parentTeams;
    }

    public UserPermission getEffectivePermission() {
        return effectivePermission;
    }

    public void setEffectivePermission(UserPermission effectivePermission) {
        this.effectivePermission = effectivePermission;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
