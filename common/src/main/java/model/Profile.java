package model;

import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.DataProvider;
import data_for_entity.data_providers.profiler.CriminalRecordProvider;
import data_for_entity.data_providers.profiler.ProfileCategoryProvider;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Profile extends ProfileAndTargetGroup {

    private ProfileType type = ProfileType.Individual;
    private ProfileProperties properties;
    @DataIgnore
    private ArrayList<CBEntity> entities = new ArrayList<>();
    @DataIgnore
    private ArrayList<TargetGroup> groups = new ArrayList<>();
    @DataIgnore
    private Boolean target;
    @DataProvider(ProfileCategoryProvider.class)
    //@JsonDeserialize(using = ProfileCategoryDeserializer.class)
    private String category;
    @DataIgnore
    private ProfileConsolidatedAttributes consolidatedAttributes;
    @DataIgnore
    private TargetStatus active = TargetStatus.ACTIVE;
    @DataIgnore
    private Date activeUntil;
    @DataIgnore
    private Integer entityCount;
    @DataIgnore
    private Profile parent;
    @DataIgnore
    private ArrayList<String> mergingProfilesIDs = new ArrayList<>();
    @DataProvider(CriminalRecordProvider.class)
    private CriminalRecord criminalRecord;
    //@DataProvider(ClassificationProvider.class)
    private String classification = "C";

    public Profile() {
        setJsonType(ProfileJsonType.Draft);
    }

    public ProfileType getType() {
        return type;
    }

    public void setType(ProfileType type) {
        this.type = type;
    }

    public ProfileProperties getProperties() {
        return properties;
    }

    public void setProperties(ProfileProperties properties) {
        this.properties = properties;
    }

    public ArrayList<CBEntity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<CBEntity> entities) {
        this.entities = entities;
    }

    public ArrayList<TargetGroup> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<TargetGroup> groups) {
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

    public TargetStatus getActive() {
        return active;
    }

    public void setActive(TargetStatus active) {
        this.active = active;
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

    public Profile getParent() {
        return parent;
    }

    public void setParent(Profile parent) {
        this.parent = parent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getMergingProfilesIDs() {
        return mergingProfilesIDs;
    }

    public void setMergingProfilesIDs(ArrayList<String> mergingProfilesIDs) {
        this.mergingProfilesIDs = mergingProfilesIDs;
    }

    public CriminalRecord getCriminalRecord() {
        return criminalRecord;
    }

    public void setCriminalRecord(CriminalRecord criminalRecord) {
        this.criminalRecord = criminalRecord;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
