package model;

import abs.TeelaEntity;
import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.WithFieldDataType;
import data_for_entity.data_types.FieldDataType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class ReportCategory extends TeelaEntity implements Comparable {

    @DataIgnore
    private Integer version;
    private String name;
    private ArrayList<String> values = new ArrayList<>();
    private Integer order;
    @WithFieldDataType(FieldDataType.BOOLEAN)
    private Boolean hidden;
    @DataIgnore
    private Boolean deleted;
    @DataIgnore
    private String currentValue;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    @Override
    public int compareTo(Object o) {
        ReportCategory compareWith = (ReportCategory)o;
        return this.getName().compareTo(compareWith.getName());
    }
}
