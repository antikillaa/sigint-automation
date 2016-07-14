package jira;

import jira.model.Issue;
import jira.model.IssueLink;
import zapi.ZAPIService;

import java.util.ArrayList;
import java.util.List;

public class JiraService {
    
    static List<String> activeStatuses = initActiveList();
    
    private JiraConnector connector = new JiraConnector();
    
    
    private static List<String>  initActiveList() {
        List<String> activeStatuses = new ArrayList<>();
        activeStatuses.add("open");
        activeStatuses.add("in progress");
        activeStatuses.add("designer review");
        activeStatuses.add("team lead review");
        return activeStatuses;
    }
    
    public boolean hasOpenedBugs(String testCaseTitle) {
        Boolean isOpened = false;
        Issue issue = connector.getIssue(new ZAPIService().getTestCaseKeyByTitle(testCaseTitle));
        for (IssueLink issueLink: issue.getFields().getIssueLinks()) {
                Issue outwardIssue = issueLink.getOutwardIssue();
            if (outwardIssue.getFields().getIssueType().getName().equalsIgnoreCase("bug")){
                String status = outwardIssue.getFields().getStatus().getName().toLowerCase();
                isOpened = activeStatuses.contains(status);
            }
        }
        return isOpened;
    }
}
