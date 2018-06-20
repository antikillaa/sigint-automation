
package ae.pegasus.framework.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attachments",
        "wfId",
        "objectType",
        "state",
        "classification",
        "layoutType",
        "reportType",
        "reportNo",
        "createdByName",
        "events",
        "reportSources",
        "orgUnits",
        "subject",
        "description",
        "considerations",
        "recommendations",
        "notes",
        "categories",
        "layoutMetadata",
        "directCaseFiles",
        "links"
})
public class Report extends G4Entity {

    @JsonProperty("attachments")
    private List<Object> attachments = new ArrayList<Object>();
    @JsonProperty("wfId")
    private String wfId;
    @JsonProperty("objectType")
    private String objectType;
    @JsonProperty("state")
    private ReportState state;
    @JsonProperty("classification")
    private String classification;
    @JsonProperty("layoutType")
    private String layoutType;
    @JsonProperty("reportType")
    private String reportType;
    @JsonProperty("reportNo")
    private String reportNo;
    @JsonProperty("createdByName")
    private String createdByName;
    @JsonProperty("events")
    private List<Event> events = new ArrayList<Event>();
    @JsonProperty("reportSources")
    private List<ReportSource> reportSources = new ArrayList<ReportSource>();
    @JsonProperty("orgUnits")
    private List<OrgUnit> orgUnits = new ArrayList<OrgUnit>();
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("description")
    private String description;
    @JsonProperty("considerations")
    private String considerations;
    @JsonProperty("recommendations")
    private String recommendations;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("categories")
    private List<Category> categories = new ArrayList<Category>();
    @JsonProperty("layoutMetadata")
    private String layoutMetadata;
    @JsonProperty("directCaseFiles")
    private List<DirectCaseFile> directCaseFiles = new ArrayList<DirectCaseFile>();
    @JsonProperty("links")
    private List<Object> links = new ArrayList<Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("attachments")
    public List<Object> getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("wfId")
    public String getWfId() {
        return wfId;
    }

    @JsonProperty("wfId")
    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    @JsonProperty("objectType")
    public String getObjectType() {
        return objectType;
    }

    @JsonProperty("objectType")
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @JsonProperty("state")
    public ReportState getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(ReportState state) {
        this.state = state;
    }

    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }

    @JsonProperty("classification")
    public void setClassification(String classification) {
        this.classification = classification;
    }

    @JsonProperty("layoutType")
    public String getLayoutType() {
        return layoutType;
    }

    @JsonProperty("layoutType")
    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    @JsonProperty("reportType")
    public String getReportType() {
        return reportType;
    }

    @JsonProperty("reportType")
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    @JsonProperty("reportNo")
    public String getReportNo() {
        return reportNo;
    }

    @JsonProperty("reportNo")
    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    @JsonProperty("createdByName")
    public String getCreatedByName() {
        return createdByName;
    }

    @JsonProperty("createdByName")
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    @JsonProperty("events")
    public List<Event> getEvents() {
        return events;
    }

    @JsonProperty("events")
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @JsonProperty("reportSources")
    public List<ReportSource> getReportSources() {
        return reportSources;
    }

    @JsonProperty("reportSources")
    public void setReportSources(List<ReportSource> reportSources) {
        this.reportSources = reportSources;
    }

    @JsonProperty("orgUnits")
    public List<OrgUnit> getOrgUnits() {
        return orgUnits;
    }

    @JsonProperty("orgUnits")
    public void setOrgUnits(List<OrgUnit> orgUnits) {
        this.orgUnits = orgUnits;
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

    @JsonProperty("considerations")
    public String getConsiderations() {
        return considerations;
    }

    @JsonProperty("considerations")
    public void setConsiderations(String considerations) {
        this.considerations = considerations;
    }

    @JsonProperty("recommendations")
    public String getRecommendations() {
        return recommendations;
    }

    @JsonProperty("recommendations")
    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("categories")
    public List<Category> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @JsonProperty("layoutMetadata")
    public String getLayoutMetadata() {
        return layoutMetadata;
    }

    @JsonProperty("layoutMetadata")
    public void setLayoutMetadata(String layoutMetadata) {
        this.layoutMetadata = layoutMetadata;
    }

    @JsonProperty("directCaseFiles")
    public List<DirectCaseFile> getDirectCaseFiles() {
        return directCaseFiles;
    }

    @JsonProperty("directCaseFiles")
    public void setDirectCaseFiles(List<DirectCaseFile> directCaseFiles) {
        this.directCaseFiles = directCaseFiles;
    }

    @JsonProperty("links")
    public List<Object> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<Object> links) {
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
