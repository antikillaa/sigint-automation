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
    private List<TargetGroup> groups;
    private LinkedHashSet<String> keywords;
    private LinkedHashSet<String> languages;
    private String name;
    private LinkedHashSet<String> phones;
    private TargetTypes type;

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

    public LinkedHashSet<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(LinkedHashSet<String> keywords) {
        this.keywords = keywords;
    }

    public LinkedHashSet<String> getLanguages() {
        return languages;
    }

    public Target setLanguages(LinkedHashSet<String> languages) {
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

    public LinkedHashSet<String> getPhones() {
        return phones;
    }

    public Target setPhones(LinkedHashSet<String> phones) {
        this.phones = phones;
        return this;
    }

    public TargetTypes getType() {
        return type;
    }

    public Target setType(TargetTypes type) {
        this.type = type;
        return this;
    }


    public Target generate() {
        this
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setType(TargetTypes.getRandom())
                .setPhones(RandomGenerator.generatePhones(10))
                .setLanguages(RandomGenerator.generateLanguagesCodes(5))
                .setKeywords(RandomGenerator.generateRandomStrings(5));
        return this;
    }

    public Map<Integer, Object[]> generateTargets(int count) {
        Map<Integer, Object[]> data = new HashMap<>();
        data.put(1, new Object[] {"ID", "Name", "Type", "Phones", "Keywords", "Target Groups"});

        for (int i = 0; i < count; i++) {
            Target target = new Target().generate();
            List<String> targetFields = new ArrayList<>();

            //ID	Name	Type	Phones	Keywords    Target Groups
            targetFields.add(target.getId());
            targetFields.add(target.getName());
            targetFields.add(target.getType().name());
            targetFields.add(target.getPhones().toString().replace("[", "").replace("]", ""));
            targetFields.add(target.getKeywords().toString().replace("[", "").replace("]", ""));

            Object[] array = new Object[targetFields.size()];
            Object[] row = targetFields.toArray(array);

            Integer rowNum = i + 2;
            data.put(rowNum, row);
        }
        return data;
    }
}
