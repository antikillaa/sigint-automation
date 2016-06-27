package model;

import abs.TeelaEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.HashSet;
import java.util.Set;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends TeelaEntity {

    private String name;
    @JsonProperty("display_name")
    private String displayName;
    private Set<String> permissions = new HashSet<>();

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Role setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public Role setPermissions(Set<String> permissions) {
        this.permissions = permissions;
        return this;
    }


    @Override
    public <T extends TeelaEntity> T generate() {
        this
                .setName(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .setPermissions(Permission.getRandomSet())
                .setDisplayName(RandomStringUtils.randomAlphabetic(10));
        return (T) this;
    }
}
