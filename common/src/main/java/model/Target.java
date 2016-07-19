package model;

import abs.TeelaEntity;
import json.serialization.TargetDeserializer;
import json.serialization.TargetJsonSerializer;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.RandomGenerator;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Target extends TeelaEntity {

    private String description;
    @JsonSerialize(using = TargetJsonSerializer.class)
    @JsonDeserialize(using = TargetDeserializer.class)
    private List<TargetGroup> groups = new ArrayList<>();
    private Set<String> keywords = new HashSet<>();
    private Set<String> languages = new HashSet<>();
    private String name;
    private Set<String> phones = new HashSet<>();
    private TargetType type;
    private String originalName;
    private int threatScore;
    private long lmt;
    private boolean deleted;

    public Target(String id) {
        setId(id);
    }

    public Target(){}

    public String getDescription() {
        return description;
    }

    public Target setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<TargetGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<TargetGroup> groups) {
        this.groups = groups;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public Target setLanguages(Set<String> languages) {
        this.languages = languages;
        return this;
    }

    public String getName() {
        return name;
    }

    public Target setName(String name) {
        this.name = name;
        return this;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public Target setPhones(Set<String> phones) {
        this.phones = phones;
        return this;
    }

    public TargetType getType() {
        return type;
    }

    public Target setType(TargetType type) {
        this.type = type;
        return this;
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

    public Target generate() {
        this
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setType(TargetType.getRandom())
                .setPhones(RandomGenerator.generatePhones(10))
                .setLanguages(RandomGenerator.generateLanguagesCodes(5))
                .setKeywords(RandomGenerator.generateRandomStrings(5));
        return this;
    }

}
