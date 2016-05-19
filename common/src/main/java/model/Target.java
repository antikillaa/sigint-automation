package model;

import abs.TeelaEntity;
import json.TargetJsonSerializer;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Target extends TeelaEntity {

    private String id;
    private String description;
    @JsonSerialize(using = TargetJsonSerializer.class)
    private List<TargetGroup> groups;
    private List<String> keywords;
    private List<String> languages;
    private String name;
    private List<String> phones;
    private TargetTypes type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TargetGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<TargetGroup> groups) {
        this.groups = groups;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public TargetTypes getType() {
        return type;
    }

    public void setType(TargetTypes type) {
        this.type = type;
    }


    public Target generate() {
        return null;
    }

}
