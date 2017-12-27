package model;

import java.util.List;

public class IdentifierSummary extends AbstractEntity {

    private ProfileJsonType jsonType = ProfileJsonType.IdentifierSummary;
    private String name; //"474123449782275"
    private Object properties; //{}
    private List<String> sources; //["T"]
    private Integer targetHitsCount;
    private Integer targetMentionsCount;
    private IdentifierType type; //"IMSI"
    private ValidationStatus validationStatus; //null
    private String value; //"474123449782275"

    public ProfileJsonType getJsonType() {
        return jsonType;
    }

    public void setJsonType(ProfileJsonType jsonType) {
        this.jsonType = jsonType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public Integer getTargetHitsCount() {
        return targetHitsCount;
    }

    public void setTargetHitsCount(Integer targetHitsCount) {
        this.targetHitsCount = targetHitsCount;
    }

    public Integer getTargetMentionsCount() {
        return targetMentionsCount;
    }

    public void setTargetMentionsCount(Integer targetMentionsCount) {
        this.targetMentionsCount = targetMentionsCount;
    }

    public IdentifierType getType() {
        return type;
    }

    public void setType(IdentifierType type) {
        this.type = type;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
