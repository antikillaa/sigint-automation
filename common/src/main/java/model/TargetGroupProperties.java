package model;

import data_for_entity.annotations.DataIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TargetGroupProperties {

    private String description;
    @DataIgnore
    private Date __creation_time;
    @DataIgnore
    private String __creator;
    @DataIgnore
    private Date __last_modified_time;
    @DataIgnore
    private String __modifier;
    @DataIgnore
    private Integer __target_group_version;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date get__creation_time() {
        return __creation_time;
    }

    public void set__creation_time(Date __creation_time) {
        this.__creation_time = __creation_time;
    }

    public String get__creator() {
        return __creator;
    }

    public void set__creator(String __creator) {
        this.__creator = __creator;
    }

    public Date get__last_modified_time() {
        return __last_modified_time;
    }

    public void set__last_modified_time(Date __last_modified_time) {
        this.__last_modified_time = __last_modified_time;
    }

    public String get__modifier() {
        return __modifier;
    }

    public void set__modifier(String __modifier) {
        this.__modifier = __modifier;
    }

    public Integer get__target_group_version() {
        return __target_group_version;
    }

    public void set__target_group_version(Integer __target_group_version) {
        this.__target_group_version = __target_group_version;
    }

}
