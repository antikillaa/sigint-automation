
package ae.pegasus.framework.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "linkId",
        "linkType",
        "attributes"
})
public class Link {

    @JsonProperty("linkId")
    private String linkId;
    @JsonProperty("linkType")
    private String linkType;
    @JsonProperty("attributes")
    private List<RFAEvent> attributes = new ArrayList<>();
    @JsonProperty("entities")
    private CBEntities entities;
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

    @JsonProperty("linkType")
    public String getLinkType() {
        return linkType;
    }

    @JsonProperty("linkType")
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public List<RFAEvent> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RFAEvent> attributes) {
        this.attributes = attributes;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public CBEntities getEntities() {
        return entities;
    }

    public void setEntities(CBEntities entities) {
        this.entities = entities;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
