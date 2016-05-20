package model;


import abs.TeelaEntity;
import json.serialization.TargetGroupDeserializer;
import json.serialization.TargetGroupJsonSerializer;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class TargetGroup extends TeelaEntity {

    private String name;
    private String description;
    @JsonSerialize(using= TargetGroupJsonSerializer.class)
    @JsonDeserialize(using= TargetGroupDeserializer.class)
    private List<Target> targets;

    public String getName() {
        return name;
    }

    public TargetGroup setName(String name) {
        this.name = name;
        return this;
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
        this
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(20));
        return this;
    }

}
