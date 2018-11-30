
package ae.pegasus.framework.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "linkId",
        "linkName",
        "linkNo",
        "linkType",
        "relationType",
        "attributes"
})
public class DirectCaseFile {

    @JsonProperty("linkId")
    private String linkId;
    @JsonProperty("linkName")
    private String linkName;
    @JsonProperty("linkNo")
    private String linkNo;
    @JsonProperty("linkType")
    private String linkType;
    @JsonProperty("relationType")
    private String relationType;
    @JsonProperty("attributes")
    private Attributes attributes;

    @JsonProperty("processed")
    private Boolean processed;

    @JsonProperty("bidirectional")
    private Boolean bidirectional;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("linkId")
    public String getLinkId() {
        return linkId;
    }

    @JsonProperty("linkId")
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    @JsonProperty("linkName")
    public String getLinkName() {
        return linkName;
    }

    @JsonProperty("linkName")
    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    @JsonProperty("linkNo")
    public String getLinkNo() {
        return linkNo;
    }

    @JsonProperty("linkNo")
    public void setLinkNo(String linkNo) {
        this.linkNo = linkNo;
    }

    @JsonProperty("linkType")
    public String getLinkType() {
        return linkType;
    }

    @JsonProperty("linkType")
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    @JsonProperty("relationType")
    public String getRelationType() {
        return relationType;
    }

    @JsonProperty("relationType")
    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    @JsonProperty("attributes")
    public Attributes getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Boolean getBidirectional() {
        return bidirectional;
    }

    public void setBidirectional(Boolean bidirectional) {
        this.bidirectional = bidirectional;
    }
}
