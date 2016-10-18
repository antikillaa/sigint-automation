package zapi;

import app_context.properties.G4Properties;
import app_context.properties.JiraProperties;
import errors.NullReturnException;
import http.G4Response;
import jira.JiraConnector;
import json.JsonConverter;
import org.apache.log4j.Logger;
import org.junit.Assert;
import reporter.ReportParser;
import reporter.ReportResults;
import reporter.Step;
import reporter.TestCase;
import zapi.model.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ZAPIService {

    private ZAPI zapi = new ZAPI();
    private HashMap<String, Integer> issueIdMap = new HashMap<>();
    private JiraProperties connectionProperties = G4Properties.getJiraProperties();
    private Logger log = Logger.getLogger(ReportParser.class);
    private ReportResults reportResults;

    /**
     * Return report results
     *
     * @return ReportResults
     */
    public ReportResults getReportResults() {
        if (reportResults == null) {
            reportResults = new ReportParser().parseXmlReportFiles("target/allure-results");
        }
        for (TestCase testCase : reportResults.getFailed()) {
            testCase.setUrl(connectionProperties.getJiraServer() + "/browse/" +
                    getTestCaseKeyByTitle(testCase.getTitle()));
        }
        return reportResults;
    }

    /**
     * Create test cycle
     */
    private Cycle createCycle(String projectId, String versionId) {
        log.info("Creating test cycle in Zephyr");
        Cycle cycle = new Cycle()
                .setClonedCycleId("")
                .setName(connectionProperties.getCycleName())
                .setBuild("")
                .setEnvironment("")
                .setDescription("")
                .setStartDate(new Date())
                .setEndDate(new Date())
                .setProjectId(projectId)
                .setVersionId(versionId);

        G4Response response = zapi.postCycle(cycle);

        if (response.getStatus() == 200) {
            cycle = JsonConverter.readEntityFromResponse(response, Cycle.class);
            log.info(response.getMessage());
        } else {
            log.error("Creating new cycle failed");
            log.error("status: " + response.getStatus());
            log.error("body: " + response.getMessage());
        }
        return cycle;
    }

    /**
     * Get current test cycle or create new one if cycle missing in Jira.
     *
     * @return test cycle
     */
    private Cycle getCycle() {
        log.info("Getting execution cycle...");
        Cycle cycle;
        JiraConnector connector = new JiraConnector();
        try {
            String projectId = connector.getProjectId(connectionProperties.getProject());
            String versionId = connector.getVersionId(projectId, connectionProperties.getVersion());

            G4Response response = zapi.getCycles(projectId, versionId);

            CyclesList cycles = JsonConverter.readEntityFromResponse(response, CyclesList.class);
            Cycle foundcycle = cycles.getCycle(connectionProperties.getCycleName());
            if (foundcycle == null) {
                log.debug("Cycle is not found. Creating new one");
                cycle = createCycle(projectId, versionId)
                        .setProjectId(projectId)
                        .setVersionId(versionId);
            } else {
                cycle = foundcycle;
                log.debug("Cycle is found. ID=" + cycle.getId() + " Name=" + cycle.getName());
            }
        } catch (NullReturnException e) {
            log.warn(e.getMessage());
            log.info("Cannot get existing cycles as project and/or version id wasn't received." +
                    "Creating new cycle...");
            throw new AssertionError("Cycle cannot be created!");
        }
        return cycle;
    }

    /**
     * Create new Execution for Jira issue.
     *
     * @param cycle   cycle
     * @param issueId issueId
     * @return Execution
     */
    private Execution addTestToCycle(Cycle cycle, int issueId) {
        // Arrange
        Execution execution = new Execution()
                .setCycleId(cycle.getId())
                .setIssueId(String.valueOf(issueId))
                .setProjectId(cycle.getProjectId())
                .setVersionId(cycle.getVersionId());

        // Act
        log.debug("Add new execution of test with issueId: " + execution.getIssueId() + " to cycle");
        G4Response response = zapi.postExecution(execution);

        execution = null;
        if (response.getStatus() == 200) {
            String message = response.getMessage();
            String json = message.substring(message.lastIndexOf("{"), message.indexOf("}") + 1);
            execution = JsonConverter.fromJsonToObject(json, Execution.class);
            log.debug(execution + " executionId = " + execution.getId());
        } else {
            log.error("Failed to add test case to cycle");
            log.error("status: " + response.getStatus());
            log.error("body: " + response.getMessage());
        }
        return execution;
    }

    /**
     * Set execution result.
     *
     * @param execution Execution
     * @param result    result status of execution:
     *                  ZAPI.UNEXECUTED = "-1"
     *                  ZAPI.PASS = "1"
     *                  ZAPI.FAIL = "2"
     *                  ZAPI.WIP = "3"
     *                  ZAPI.BLOCKED = "4"
     */
    private void setExecutionResult(Execution execution, String result) {
        log.debug("Set execution result for: " + execution.getIssueKey() + ", result: " + result);

        G4Response response = zapi.putExecution(execution.getId(), result);
        if (response.getStatus() != 200) {
            log.error("Failed to set execution result");
            log.error("status: " + response.getStatus());
            log.error("body: " + response.getMessage());
        }
    }

    /**
     * Return test key.
     *
     * @param title Test key title (summary)
     * @return test key, ex: GQ-123, TEEL-1234
     */
    public String getTestCaseKeyByTitle(String title) {
        log.debug("Finding test case by it's title " + title);

        G4Response response = zapi.JQL(0, 1000, "key", String.format("summary~\"%s\"", title));

        if (response.getStatus() != 200) {
            log.error("Was unable to complete request to JIRA");
            return null;
        }

        try {
            IssueList issueList = JsonConverter.readEntityFromResponse(response, IssueList.class);
            log.debug("Found issues: " + issueList.getIssues());
            return issueList.getIssues().get(0).getKey();
        } catch (IndexOutOfBoundsException e) {
            log.error("Cannot find test case by it's name " + title);
            return "";
        }
    }

    /**
     * Mapping test issue from Jira via API
     */
    private void getTestCasesFromProject(String projectKey) {
        // TODO batch load data
        log.info("Download tests from Zephyr...");
        G4Response response = zapi.JQL(0, 1000, "id,key,summary", "project=" + projectKey + " and issuetype = Test");

        if (response.getStatus() == 200) {
            IssueList issueList = JsonConverter.readEntityFromResponse(response, IssueList.class);
            log.info("Tests downloaded: " + issueList.getIssues().size());
            for (Issue issue : issueList.getIssues()) {
                issueIdMap.put(issue.getFields().getSummary(), issue.getId());
            }
        } else {
            log.error("Failed to get list of jira issues with type 'Test'");
            log.error("status: " + response.getStatus());
            log.error("body: " + response.getMessage());
        }
    }

    /**
     * Export auto-tests results run report to Zephyr (Jira).
     */
    public void reportToZephyr() {
        log.info("Reporting results to Zephyr...");
        getTestCasesFromProject("GQ");

        Cycle cycle = getCycle();
        Assert.assertTrue(cycle != null);
        getReportResults();
        List<TestCase> testCases = reportResults.getTestCases();
        log.debug("Add test results to cycle...");
        log.debug("Test result size: " + testCases.size());
        for (TestCase testCase : testCases) {
            log.debug("Scenario: " + testCase.getTitle() + ", status: " + testCase.getStatus());
            if (issueIdMap.containsKey(testCase.getTitle())) {
                // create test
                Execution execution = addTestToCycle(cycle, issueIdMap.get(testCase.getTitle()));
                for (Step step : testCase.getSteps()) {
                    log.debug("Step: " + step.getName() + ", status: " + step.getStatus());
                }

                // set test result
                switch (testCase.getStatus()) {
                    case "passed":
                        setExecutionResult(execution, ZAPI.PASS);
                        break;
                    case "failed":
                        setExecutionResult(execution, ZAPI.FAIL);
                        break;
                    case "broken":
                        setExecutionResult(execution, ZAPI.FAIL);
                    default:
                        setExecutionResult(execution, ZAPI.WIP);
                        break;
                }
            } else {
                log.warn("Test: '" + testCase.getTitle() + "', does not found on Zephyr");
            }
        }
    }
}
