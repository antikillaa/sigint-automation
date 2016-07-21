package failure_strategy;

import jira.JiraService;
import org.apache.log4j.Logger;
import reporter.ReportResults;
import reporter.TestCase;
import zapi.ZAPIService;

import java.util.List;

public class Failures {
    
    private static ZAPIService service = new ZAPIService();
    private static JiraService jiraService = new JiraService();
    private static Logger log = Logger.getLogger(Failures.class);
    
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
        try {
            return jiraService.hasOpenedBugs(testCaseTitle);
        } catch (Exception e) {
            log.warn("Error during jira connection. Unable to check opened bugs on the issue: " + testCaseTitle);
            log.warn(e.getMessage());
            log.warn(e.getStackTrace());
            return false;
        }
    }
    
}
