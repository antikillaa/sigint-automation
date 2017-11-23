package model;

import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.DataProvider;
import data_for_entity.annotations.WithCollectionSize;
import data_for_entity.annotations.WithDataDependencies;
import data_for_entity.data_providers.profiler.DataSourceTypeProvider;
import data_for_entity.data_providers.profiler.IdentifierTypeProvider;
import data_for_entity.data_providers.profiler.IdentifierValueProvider;
import data_for_entity.data_providers.profiler.ValidationStatusProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Identifier extends AbstractEntity {

    @DataIgnore
    private String jsonType = "Identifier";
    private String name;
    @DataIgnore
    private Object properties;
    @WithCollectionSize(1)
    @DataProvider(DataSourceTypeProvider.class)
    private ArrayList<DataSourceType> sources = new ArrayList<>();
    @DataProvider(IdentifierTypeProvider.class)
    private IdentifierType type;
    @DataProvider(ValidationStatusProvider.class)
    private ValidationStatus validationStatus;
    @WithDataDependencies(provider = IdentifierValueProvider.class, fields = {"type"})
    private String value;

    public String getJsonType() {
        return jsonType;
    }

    public void setJsonType(String jsonType) {
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

    public ArrayList<DataSourceType> getSources() {
        return sources;
    }

    public void setSources(ArrayList<DataSourceType> sources) {
        this.sources = sources;
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
