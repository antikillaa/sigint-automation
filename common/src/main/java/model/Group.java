package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Group extends TeelaEntity {

    private List<String> roles;
    private List<Permission> permissions;
    private List<String> users;
    @JsonProperty("display_name")
    private String displayName;

    public List<String> getRoles() {
        return roles;
    }

    public Group setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public Group setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public List<String> getUsers() {
        return users;
    }

    public Group setUsers(List<String> users) {
        this.users = users;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Group setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    @Override
    public Group generate() {
        this
                .setDisplayName(RandomStringUtils.randomAlphabetic(8).toUpperCase());
        return this;
    }
}
