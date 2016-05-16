package zapi.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Execution {

    private int id;
    private int orderId;
    private String executionStatus;
    private String comment;
    private String htmlComment;
    private String cycleName;
    private int versionId;
    private int cycleId;
    private String versionName;
    private int projectId;
    private String createdBy;
    private String modifiedBy;
    private int issueId;
    private String issueKey;
    private String summary;
    private String issueDescription;
    private String label;
    private String component;
    private String projectKey;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIssueId() {
        return issueId;
    }

    @JsonProperty("issueId")
    public Execution setIssueId(int issueId) {
        this.issueId = issueId;
        return this;
    }

    public int getVersionId() {
        return versionId;
    }

    @JsonProperty("versionId")
    public Execution setVersionId(int versionId) {
        this.versionId = versionId;
        return this;
    }

    public Execution setVersionId(String version) {
        try {
            String propFileName = "versionId.properties";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            Properties properties = new Properties();
            properties.load(inputStream);

            this.versionId = Integer.valueOf(
                    properties.getProperty(version)
            );
        }
        catch (IOException e) {
            System.err.println("IOException! versionId.property file is missing!");
            e.printStackTrace();

            this.versionId = -1;
        }
        return this;
    }

    public int getCycleId() {
        return cycleId;
    }

    public Execution setCycleId(int cycleId) {
        this.cycleId = cycleId;
        return this;
    }

    public int getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public Execution setProjectId(int projectId) {
        this.projectId = projectId;
        return this;
    }

    public Execution setProjectId(String projectKey) {
        try {
            String propFileName = "projectId.properties";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            Properties properties = new Properties();
            properties.load(inputStream);

            this.projectId = Integer.valueOf(
                    properties.getProperty(projectKey)
            );
        }
        catch (IOException e) {
            System.err.println("IOException! projectId.property file is missing!");
            e.printStackTrace();
            this.projectId = -1;
        }
        return this;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getHtmlComment() {
        return htmlComment;
    }

    public void setHtmlComment(String htmlComment) {
        this.htmlComment = htmlComment;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @JsonProperty("issueSummary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("issueSummary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    @Override
    public String toString(){
        return "{ \"issueId\" : " + issueId +
                ", \"versionId\" : " + versionId +
                ", \"cycleId\" : " + cycleId +
                ", \"projectId\" : " + projectId +
                "}";
    }
}
