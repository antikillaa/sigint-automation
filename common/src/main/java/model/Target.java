package model;

import abs.TeelaEntity;
import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.DataProvider;
import data_for_entity.annotations.WithDataSize;
import data_for_entity.data_providers.custom.LanguageCodesProvider;
import data_for_entity.data_providers.PhonesProvider;
import data_for_entity.data_providers.data_target.TargetTypeProvider;
import json.serialization.TargetDeserializer;
import json.serialization.TargetJsonSerializer;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Target extends TeelaEntity {

    private String description;
    @JsonSerialize(using = TargetJsonSerializer.class)
    @JsonDeserialize(using = TargetDeserializer.class)
    @DataIgnore
    private ArrayList<TargetGroup> groups = new ArrayList<>();
    private HashSet<String> keywords = new HashSet<>();
    @WithDataSize(5)
    @DataProvider(LanguageCodesProvider.class)
    private HashSet<String> languages = new HashSet<>();
    private String name;
    @DataProvider(PhonesProvider.class)
    private HashSet<String> phones = new HashSet<>();
    @DataProvider(TargetTypeProvider.class)
    private TargetType type;
    @DataIgnore
    private String originalName;
    @DataIgnore
    private int threatScore;
    @DataIgnore
    private long lmt;
    @DataIgnore
    private boolean deleted;
   
    
    
    @Override
    public String toString(){
        return String.format("groups:%s, keywords:%s, languages:%s, name:%s," +
                "phones:%s, type:%s, originalName:%s, threatScore:%s, lmt:%s",
                Arrays.toString(groups.toArray()), Arrays.toString(keywords.toArray()),
                Arrays.toString(languages.toArray()), name, Arrays.toString(phones.toArray()),
                type, originalName, threatScore, lmt);
        
    }

    public Target(String id) {
        setId(id);
    }

    public Target(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TargetGroup> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<TargetGroup> groups) {
        this.groups = groups;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(HashSet<String> keywords) {
        this.keywords = keywords;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(HashSet<String> languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(HashSet<String> phones) {
        this.phones = phones;
    }

    public TargetType getType() {
        return type;
    }

    public void setType(TargetType type) {
        this.type = type;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getThreatScore() {
        return threatScore;
    }

    public void setThreatScore(int threatScore) {
        this.threatScore = threatScore;
    }

    public long getLmt() {
        return lmt;
    }

    public void setLmt(long lmt) {
        this.lmt = lmt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public Target addGroup(TargetGroup targetGroup) {
        List<TargetGroup> groups = getGroups();
        groups.add(targetGroup);
        return this;
    }

}
