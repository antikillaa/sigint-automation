package model;


import abs.TeelaEntity;
import data_for_entity.annotations.DataIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class TargetGroup extends TeelaEntity {

    private String name;
    private String description;
    //@JsonSerialize(using= TargetGroupJsonSerializer.class)
    //@JsonDeserialize(using= TargetGroupDeserializer.class)
    @DataIgnore
    private List<String> targets;
    @DataIgnore
    private int lmt;
    @DataIgnore
    private int threatScore;
    @DataIgnore
    private boolean deleted;

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

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public int getLmt() {
        return lmt;
    }

    public void setLmt(int lmt) {
        this.lmt = lmt;
    }

    public int getThreatScore() {
        return threatScore;
    }

    public void setThreatScore(int threatScore) {
        this.threatScore = threatScore;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    

}
