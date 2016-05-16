package zapi.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Cycle {

    private int id;
    private String name;
    private String build;
    private String environment;
    private String description;
    private String startDate;
    private String endDate;
    private int projectId;
    private int versionId;
    private String responseMessage;

    public Cycle() {
    }

    public int getId() {
        return id;
    }

    public Cycle setId(int id) {
        this.id = id;
        return this;
    }

    public String getBuild() {
        return build;
    }

    public Cycle setBuild(String build) {
        this.build = build;
        return this;
    }

    public String getEnvironment() {
        return environment;
    }

    public Cycle setEnvironment(String environment) {
        this.environment = environment;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Cycle setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public Cycle setName(String name) {
        this.name = name;
        return this;
    }

    public int getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public Cycle setProjectId(int projectId) {
        this.projectId = projectId;
        return this;
    }

    public Cycle setProjectId(String projectId) {
        this.projectId = Integer.valueOf(projectId);
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public Cycle setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public Cycle setStartDate(Date startDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/MMM/yy");
        this.startDate = dateFormat.format(startDate);
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public Cycle setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public Cycle setEndDate(Date endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/MMM/yy");
        this.endDate = dateFormat.format(endDate);
        return this;
    }

    public int getVersionId() {
        return versionId;
    }

    @JsonProperty("versionId")
    public Cycle setVersionId(int versionId) {
        this.versionId = versionId;
        return this;
    }

    public Cycle setVersionId(String versionId) {
        this.versionId = Integer.valueOf(versionId);
        return this;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public Cycle setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    public String postJson() {
        return "{ \"clonedCycleId\" : \"" +
                "\", \"name\" : \"" + name +
                "\", \"build\" : \"" + build +
                "\", \"environment\" : \"" + environment +
                "\", \"description\" : \"" + description +
                "\", \"startDate\" : \"" + startDate +
                "\", \"endDate\" : \"" + endDate +
                "\", \"projectId\" : " + projectId +
                ", \"versionId\" : " + versionId +
                "}";
    }

    @Override
    public String toString() {
        return "{ \"id\" : " + id +
                ", \"name\" : \"" + name +
                "\", \"build\" : \"" + build +
                "\", \"environment\" : \"" + environment +
                "\", \"description\" : \"" + description +
                "\", \"startDate\" : \"" + startDate +
                "\", \"endDate\" : \"" + endDate +
                "\", \"projectId\" : " + projectId +
                ", \"versionId\" : " + versionId +
                "}";
    }

}
