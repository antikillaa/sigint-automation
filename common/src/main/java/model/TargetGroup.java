package model;


import abs.TeelaEntity;
import data_for_entity.annotations.DataIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class TargetGroup extends TeelaEntity {

    // TODO old fields, check it and remove redundant
    @DataIgnore
    private String description;
    //@JsonSerialize(using= TargetGroupJsonSerializer.class)
    //@JsonDeserialize(using= TargetGroupDeserializer.class)
    @DataIgnore
    private List<String> targets;
    @DataIgnore
    private Integer lmt;
    @DataIgnore
    private Integer threatScore;
    @DataIgnore
    private Boolean deleted;

    // checked fields
    //@DataProvider(TargetGroupProvider.class)
    @DataIgnore
    private TargetGroupType type = TargetGroupType.TargetGroup;
    private String name;
    private TargetGroupProperties properties;
    @DataIgnore
    private int noGroups;
    @DataIgnore
    private int noProfiles;
    @DataIgnore
    private ArrayList<String> profiles = new ArrayList<>(); // TODO check format
    @DataIgnore
    private ArrayList<String> groups = new ArrayList<>(); // TODO
    @DataIgnore
    private String parent; // TODO

    @Override
    public String toString() {
        return String.format("name: %s, description: %s, targets: %s," +
                "lmt: %s, deleted: %s", name, description, targets, lmt, deleted);
    }

    public String getName() {
        return name.trim();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public Integer getLmt() {
        return lmt;
    }

    public void setLmt(Integer lmt) {
        this.lmt = lmt;
    }

    public Integer getThreatScore() {
        return threatScore;
    }

    public void setThreatScore(Integer threatScore) {
        this.threatScore = threatScore;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    public TargetGroupType getType() {
        return type;
    }

    public void setType(TargetGroupType type) {
        this.type = type;
    }

    public TargetGroupProperties getProperties() {
        return properties;
    }

    public void setProperties(TargetGroupProperties properties) {
        this.properties = properties;
    }

    public int getNoGroups() {
        return noGroups;
    }

    public void setNoGroups(int noGroups) {
        this.noGroups = noGroups;
    }

    public int getNoProfiles() {
        return noProfiles;
    }

    public void setNoProfiles(int noProfiles) {
        this.noProfiles = noProfiles;
    }

    public ArrayList<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<String> profiles) {
        this.profiles = profiles;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

}
