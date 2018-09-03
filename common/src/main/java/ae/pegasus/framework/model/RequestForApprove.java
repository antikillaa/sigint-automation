package ae.pegasus.framework.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "objectType",
        "internalRequestNo",
        "orgUnits",
        "classification",
        "directCaseFiles",
        "subject",
        "description",
        "links"
})
public class RequestForApprove extends G4Entity {

    @JsonProperty("objectType")
    private String objectType;
    @JsonProperty("internalRequestNo")
    private String internalRequestNo;
    @JsonProperty("orgUnits")
    private List<OrgUnit> orgUnits = null;
    @JsonProperty("classification")
    private String classification;
    @JsonProperty("directCaseFiles")
    private List<DirectCaseFile> directCaseFiles = null;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("description")
    private String description;
    @JsonProperty("links")
    private List<Link> links = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("objectType")
    public String getObjectType() {
        return objectType;
    }

    @JsonProperty("objectType")
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @JsonProperty("internalRequestNo")
    public String getInternalRequestNo() {
        return internalRequestNo;
    }

    @JsonProperty("internalRequestNo")
    public void setInternalRequestNo(String internalRequestNo) {
        this.internalRequestNo = internalRequestNo;
    }

    @JsonProperty("orgUnits")
    public List<OrgUnit> getOrgUnits() {
        return orgUnits;
    }

    @JsonProperty("orgUnits")
    public void setOrgUnits(List<OrgUnit> orgUnits) {
        this.orgUnits = orgUnits;
    }

    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }

    @JsonProperty("classification")
    public void setClassification(String classification) {
        this.classification = classification;
    }

    @JsonProperty("directCaseFiles")
    public List<DirectCaseFile> getDirectCaseFiles() {
        return directCaseFiles;
    }

    @JsonProperty("directCaseFiles")
    public void setDirectCaseFiles(List<DirectCaseFile> directCaseFiles) {
        this.directCaseFiles = directCaseFiles;
    }

    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
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

