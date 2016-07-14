package jira.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class FieldsFilter {

    private String summary;
    private Status status;
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public IssueType getIssueType() {
        return issueType;
    }
    
    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }
    
    public List<IssueLink> getIssueLinks() {
        return issueLinks;
    }
    
    public void setIssueLinks(List<IssueLink> issueLinks) {
        this.issueLinks = issueLinks;
    }
    
    @JsonProperty("issuetype")
    private IssueType issueType;
    
    @JsonProperty("issuelinks")
    private List<IssueLink> issueLinks;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

