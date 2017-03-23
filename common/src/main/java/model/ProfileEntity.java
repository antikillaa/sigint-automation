package model;

import abs.AbstractEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProfileEntity extends AbstractEntity {

    private String name;
    private DummyFieldType properties;
    private ArrayList<SourceType> sources = new ArrayList<>();
    private ProfileEntityType type;
    private ValidationStatus validationStatus;
    private DummyFieldType entities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DummyFieldType getProperties() {
        return properties;
    }

    public void setProperties(DummyFieldType properties) {
        this.properties = properties;
    }

    public ArrayList<SourceType> getSources() {
        return sources;
    }

    public void setSources(ArrayList<SourceType> sources) {
        this.sources = sources;
    }

    public ProfileEntityType getType() {
        return type;
    }

    public void setType(ProfileEntityType type) {
        this.type = type;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public DummyFieldType getEntities() {
        return entities;
    }

    public void setEntities(DummyFieldType entities) {
        this.entities = entities;
    }
}
