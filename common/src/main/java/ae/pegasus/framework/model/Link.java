
package ae.pegasus.framework.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
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
    private Attributes attributes;
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

}
