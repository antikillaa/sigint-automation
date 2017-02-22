package model;

import data_for_entity.annotations.DataIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TargetGroupProperties {

    private String description;
    @DataIgnore
    @JsonProperty("__creation_time")
    private Date creation_time;
    @DataIgnore
    @JsonProperty("__creator")
    private String creator;
    @DataIgnore
    @JsonProperty("__last_modified_time")
    private Date last_modified_time;
    @DataIgnore
    @JsonProperty("__modifier")
    private String modifier;
    @DataIgnore
    @JsonProperty("__target_group_version")
    private Integer target_group_version;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getLast_modified_time() {
        return last_modified_time;
    }

    public void setLast_modified_time(Date last_modified_time) {
        this.last_modified_time = last_modified_time;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getTarget_group_version() {
        return target_group_version;
    }

    public void setTarget_group_version(Integer target_group_version) {
        this.target_group_version = target_group_version;
    }

}
