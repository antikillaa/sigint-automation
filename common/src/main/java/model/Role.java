package model;

import abs.TeelaEntity;
import data_for_entity.annotations.DataProvider;
import data_for_entity.annotations.WithCollectionSize;
import data_for_entity.data_providers.UserPermissionProvider;
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
    @DataProvider(UserPermissionProvider.class)
    @WithCollectionSize(6)
    private HashSet<String> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(HashSet<String> permissions) {
        this.permissions = permissions;
        
    }
    
}
