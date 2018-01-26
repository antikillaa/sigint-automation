package model;

import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.DataProvider;
import data_for_entity.annotations.WithCollectionSize;
import data_for_entity.annotations.WithDataDependencies;
import data_for_entity.data_providers.RandomDateProvider;
import data_for_entity.data_providers.profiler.ClassificationProvider;
import data_for_entity.data_providers.profiler.CriminalRecordProvider;
import data_for_entity.data_providers.profiler.ProfileCategoryProvider;
import data_for_entity.data_providers.profiler.TargetStatusProvider;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Profile extends ProfileAndTargetGroup {

    @DataIgnore
    private String type = "Individual";
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
    @WithDataDependencies(provider = TargetStatusProvider.class, fields = {"activeUntil"})
    private TargetStatus active;
    @DataProvider(RandomDateProvider.class)
    private Date activeUntil;
    @DataIgnore
    private Integer entityCount = 0;
    @DataIgnore
    private Profile parent;
    @DataIgnore
    private ArrayList<String> mergingProfilesIDs;
    @DataProvider(CriminalRecordProvider.class)
    private CriminalRecord criminalRecord;
    @DataProvider(ClassificationProvider.class)
    private String classification; // "C";
    @WithCollectionSize(4)
    private ArrayList<Identifier> identifiers = new ArrayList<>();
    @DataIgnore
    private ArrayList<IdentifierSummary> identifiersSummary;
    @DataIgnore
    private String uploadedImage;
    @DataIgnore
    private ArrayList<VoiceFile> voiceFiles = new ArrayList<>();
    @DataIgnore
    private String voiceModelId;

    public Profile() {
        setJsonType(ProfileJsonType.Draft);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public ArrayList<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(ArrayList<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public ArrayList<IdentifierSummary> getIdentifiersSummary() {
        return identifiersSummary;
    }

    public void setIdentifiersSummary(ArrayList<IdentifierSummary> identifiersSummary) {
        this.identifiersSummary = identifiersSummary;
    }

    public String getUploadedImage() {
        return uploadedImage;
    }

    public void setUploadedImage(String uploadedImage) {
        this.uploadedImage = uploadedImage;
    }

    public ArrayList<VoiceFile> getVoiceFiles() {
        return voiceFiles;
    }

    public void setVoiceFiles(ArrayList<VoiceFile> voiceFiles) {
        this.voiceFiles = voiceFiles;
    }

    public String getVoiceModelId() {
        return voiceModelId;
    }

    public void setVoiceModelId(String voiceModelId) {
        this.voiceModelId = voiceModelId;
    }
}
