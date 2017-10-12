package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CBEntity extends AbstractEntity {

    private List<String> sources;
    private String sourceType;
    private String recordType;
    private String subSourceType;
    private Date modifiedOn;
    private Date eventTime;
    private Map<String, Object> identifiers = new LinkedHashMap<>();
    private Map<String, Object> attributes = new LinkedHashMap<>();
    private Object events;
    private CBEntities entities;
    private CBEntityAssignments assignments;
    private List<Geo> geo;
    private SearchObjectType objectType;
    private Reports reports = new Reports();

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Object getEvents() {
        return events;
    }

    public void setEvents(Object events) {
        this.events = events;
    }

    public CBEntities getEntities() {
        return entities;
    }

    public void setEntities(CBEntities entities) {
        this.entities = entities;
    }

    public CBEntityAssignments getAssignments() {
        return assignments;
    }

    public void setAssignments(CBEntityAssignments assignments) {
        this.assignments = assignments;
    }

    public List<Geo> getGeo() {
        return geo;
    }

    public void setGeo(List<Geo> geo) {
        this.geo = geo;
    }

    public SearchObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(SearchObjectType objectType) {
        this.objectType = objectType;
    }

    public Map<String, Object> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Map<String, Object> identifiers) {
        this.identifiers = identifiers;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getSubSourceType() {
        return subSourceType;
    }

    public void setSubSourceType(String subSourceType) {
        this.subSourceType = subSourceType;
    }

    public Reports getReports() {
        return reports;
    }

    public void setReports(Reports reports) {
        this.reports = reports;
    }

    public class Reports {
        private List<String> reportIds = new ArrayList<>();

        public List<String> getReportIds() {
            return reportIds;
        }

        public void setReportIds(List<String> reportIds) {
            this.reportIds = reportIds;
        }
    }
}