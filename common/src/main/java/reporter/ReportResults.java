package reporter;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ReportResults {

    private Logger log = Logger.getLogger(ReportResults.class);
    private List<TestCase> testCases = new ArrayList<>();

    public int getTotalRun(){
        return testCases.size();
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public List<TestCase> getTestCasesByStatus(String status) {
        log.debug("Getting '" + status + "' tests list");
        List<TestCase> testCases = new ArrayList<>();
        for (TestCase test : testCases) {
            if (test.getStatus().equals(status)) {
                if (!testCases.add(test)) {
                    log.warn("Test: " + test.getTitle() + ", does not added into '" + status + "' list");
                }
            }
        }
        return testCases;
    }

    public List<TestCase> getPassed() {
        return getTestCasesByStatus("passed");
    }

    public List<TestCase> getFailed() {
        return getTestCasesByStatus("failed");
    }

    public List<TestCase> getBrokened() {
        return getTestCasesByStatus("broken");
    }

    public List<TestCase> getSkipped() {
        return getTestCasesByStatus("pending");
    }


}
