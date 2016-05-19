package model;


import abs.TeelaEntity;
import json.TargetGroupJsonSerializer;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class TargetGroup extends TeelaEntity {

    private String name;
    private String description;
    @JsonSerialize(using= TargetGroupJsonSerializer.class)
    private List<Target> targets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public TargetGroup generate() {
        return null;
    }

}
