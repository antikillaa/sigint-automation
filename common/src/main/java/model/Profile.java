package model;

import abs.AbstractEntity;
import data_for_entity.annotations.DataIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Profile extends AbstractEntity {

    private ProfileType type = ProfileType.Profile;
    private String name;
    private ProfileProperties properties;
    @DataIgnore
    private ArrayList<String> entities = new ArrayList<>();
    @DataIgnore
    private ArrayList<String> groups = new ArrayList<>();
    @DataIgnore
    private Boolean target;
    @DataIgnore
    private ProfileConsolidatedAttributes consolidatedAttributes;
    @DataIgnore
    private Boolean active = true;
    @DataIgnore
    private Date activeUntil;
    @DataIgnore
    private Integer entityCount;
    @DataIgnore
    private String parent;
    @DataIgnore
    private String mergingProfilesIDs;


    public ProfileType getType() {
        return type;
    }

    public void setType(ProfileType type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileProperties getProperties() {
        return properties;
    }

    public void setProperties(ProfileProperties properties) {
        this.properties = properties;
    }

    public ArrayList<String> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<String> entities) {
        this.entities = entities;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public Boolean getTarget() {
        return target;
    }

    public void setTarget(Boolean target) {
        this.target = target;
    }

    public ProfileConsolidatedAttributes getConsolidatedAttributes() {
        return consolidatedAttributes;
    }

    public void setConsolidatedAttributes(ProfileConsolidatedAttributes consolidatedAttributes) {
        this.consolidatedAttributes = consolidatedAttributes;
    }

    public Date getActiveUntil() {
        return activeUntil;
    }

    public void setActiveUntil(Date activeUntil) {
        this.activeUntil = activeUntil;
    }

    public Integer getEntityCount() {
        return entityCount;
    }

    public void setEntityCount(Integer entityCount) {
        this.entityCount = entityCount;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getMergingProfilesIDs() {
        return mergingProfilesIDs;
    }

    public void setMergingProfilesIDs(String mergingProfilesIDs) {
        this.mergingProfilesIDs = mergingProfilesIDs;
    }
}
