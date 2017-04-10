package jira;

import jira.model.Issue;
import jira.model.IssueLink;
import zapi.ZAPIService;

import java.util.ArrayList;
import java.util.List;

public class JiraService {
    
    private static List<String> activeStatuses = initActiveList();
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
        String key = new ZAPIService().getTestCaseKeyByTitle(testCaseTitle);
        Issue issue = connector.getIssue(key);
        List<IssueLink> issueLinks = issue.getFields().getIssueLinks();
        if (issueLinks.size() == 0) {
            return false;
        }
        for (IssueLink issueLink: issue.getFields().getIssueLinks()) {
                Issue innerIssue = issueLink.getInnerIssue();
            if (innerIssue == null) {
                continue;
            }
            if (innerIssue.getFields().getIssueType().getName().equalsIgnoreCase("bug")){
                String status = innerIssue.getFields().getStatus().getName().toLowerCase();
                isOpened = isOpened || activeStatuses.contains(status);
            }
        }
        return isOpened;
    }
}
