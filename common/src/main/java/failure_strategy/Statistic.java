package failure_strategy;

import jira.JiraService;
import reporter.ReportResults;
import reporter.TestCase;
import zapi.ZAPIService;

import java.util.List;

public class Statistic {
    
    private static ZAPIService service = new ZAPIService();
    private static JiraService jiraService = new JiraService();
    
    public static ReportResults getResults() {
        return service.getReportResults();
    }
    
    public static Boolean hasFailuresWithoutBugs() {
        List<TestCase> testCaseList = service.getReportResults().getFailed();
            for (TestCase testCase:testCaseList) {
                if (!jiraService.hasOpenedBugs(testCase.getTitle())) {
                    return true;
                }
            }
            return false;
        }
    
    
    public static Boolean hasOpenedBug(String testCaseTitle) {
        return jiraService.hasOpenedBugs(testCaseTitle);
        
    }
    
}
