package model;

import data_for_entity.annotations.DataIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Organization extends G4Entity {

    @DataIgnore
    @Deprecated
    private String parentTeamId;
    @DataIgnore
    private List<String> parentTeamIds = new ArrayList<>();
    @DataIgnore
    private OrganizationType organizationType;
    private String fullName;
    @DataIgnore
    private UserPermission defaultPermission = new UserPermission();

    Organization() {
        setParentTeamId("00");
        setParentTeamIds(Arrays.asList("00"));
    }

    public String getParentTeamId() {
        return parentTeamId;
    }

    public void setParentTeamId(String parentTeamId) {
        this.parentTeamId = parentTeamId;
    }

    public List<String> getParentTeamIds() {
        return parentTeamIds;
    }

    public void setParentTeamIds(List<String> parentTeamIds) {
        this.parentTeamIds = parentTeamIds;
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

    public UserPermission getDefaultPermission() {
        return defaultPermission;
    }

    public void setDefaultPermission(UserPermission defaultPermission) {
        this.defaultPermission = defaultPermission;
    }
}
