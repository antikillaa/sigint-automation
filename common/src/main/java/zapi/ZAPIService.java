package zapi;

import errors.NullReturnException;
import jira.JiraConnector;
import json.JsonCoverter;
import model.AppContext;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import reporter.ReportParser;
import reporter.ReportResults;
import reporter.Step;
import reporter.TestCase;
import zapi.model.*;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ZAPIService {

    private ZAPI zapi = new ZAPI();
    private ObjectMapper mapper = new ObjectMapper();
    private HashMap<String, Integer> issueIdMap = new HashMap<>();
    private Properties connectionProperties = AppContext.getContext().getJiraConnection();
    private Logger log = Logger.getLogger(ReportParser.class);
    private ReportResults reportResults;
    private String pathToXml;

    public ZAPIService(String pathToXML) {
        this.pathToXml = pathToXML;
    }

    public ReportResults getReportResults(){
        if (reportResults==null){
            reportResults = new ReportParser().parseXmlReportFiles(pathToXml);
        }
        return reportResults;
    }

    /**
     * Create test cycle
     */
    private Cycle createCycle(String projectId, String versionId) {
        log.info("Creating test cycle in Zephyr");
        Cycle cycle = new Cycle()
                .setName(connectionProperties.getProperty("cycle"))
                .setDescription("")
                .setStartDate(new Date())
                .setEndDate(new Date())
                .setProjectId(projectId)
                .setVersionId(versionId);

        Response response = zapi.postCycle(cycle);

        if (response.getStatus() == 200) {

            cycle = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Cycle.class);
            log.info("cycle created: " + cycle);

        } else {
            log.error("Creating new cycle failed");
            log.error("status: " + response.getStatus());
            log.error("headers: " + response.getHeaders());
            log.error("body: " + response.readEntity(String.class));
        }
        return cycle;

    }

    private Cycle getCycle(){
        log.info("Getting execution cycle...");
        Cycle cycle;
        JiraConnector connector = new JiraConnector();
        try {
            String projectId = connector.getProjectId(connectionProperties.getProperty("project"));
            String versionId = connector.getVersionId(projectId, connectionProperties.getProperty("version"));
            CyclesList cycles = zapi.getCycles(projectId, versionId);
            Cycle foundcycle = cycles.getCycle(connectionProperties.getProperty("cycle"));
            if (foundcycle == null) {
                log.debug("Cycle is not found. Creating new one");
                cycle = createCycle(projectId, versionId);
            }
            else {
                cycle = foundcycle;
                log.debug("Cycle is found. ID="+cycle.getId()+" Name="+cycle.getName());
            }
        } catch (NullReturnException e) {
            log.warn(e.getMessage());
            log.info("Cannot get existing cycles as project and/or version id wasn't received." +
                    "Creating new cycle...");
            throw new AssertionError("Cycle cannot be created!");
        }
        return cycle;
    }

    private Execution addTestToCycle(Cycle cycle, int issueId){
        // Arrange
        Execution execution = new Execution()
                .setCycleId(cycle.getId())
                .setIssueId(issueId)
                .setProjectId(cycle.getProjectId())
                .setVersionId(cycle.getVersionId());

        // Act
        log.debug("Add new execution of test with issueId: " + execution.getIssueId() + " to cycle");
        Response response = zapi.postExecution(execution);

        execution = null;
        if (response.getStatus() == 200) {
            String json = response.readEntity(String.class);
            json = json.substring(json.lastIndexOf("{"), json.indexOf("}") + 1);

            try {
                execution = mapper.readValue(json, Execution.class);
                log.debug(execution + " executionId = " + execution.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.error("Failed to add test case to cycle");
            log.error("status: " + response.getStatus());
            log.error("headers: " + response.getHeaders());
            log.error("body: " + response.readEntity(String.class));
        }
        return execution;
    }

    private void setExecutionResult(Execution execution, String result){
        log.debug("Set execution result for: " + execution.getIssueKey() + ", result: " + result);

        Response response = zapi.putExecution(execution.getId(), result);
        if (response.getStatus() != 200) {
            log.error("Failed to set execution result");
            log.error("status: " + response.getStatus());
            log.error("headers: " + response.getHeaders());
            log.error("body: " + response.readEntity(String.class));
        }
    }

    /**
     * Mapping test issue from Jira via API
     */
    private void getTestCasesFromProject(String projectKey) {
        // TODO batch load data
        log.info("Download tests from Zephyr...");
        Response response = zapi.JQL(0, 1000, "id,key,summary", "project=" + projectKey + " and issuetype = Test");

        if (response.getStatus() == 200) {
            try {
                IssueList issueList = mapper.readValue(response.readEntity(String.class), IssueList.class);
                log.info("Tests downloaded: " + issueList.getIssues().size());
                for (Issue issue : issueList.getIssues()) {
                    issueIdMap.put(issue.getFields().getSummary(), issue.getId());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.error("Failed to get list of jira issues with type 'Test'");
            log.error("status: " + response.getStatus());
            log.error("headers: " + response.getHeaders());
            log.error("body: " + response.readEntity(String.class));
        }
    }

    /**
     * Publish test run results to Zephyr via ZAPI
     *
     * @param path example "target/allure-results"
     */
    public void reportToZephyr(){
        log.info("Reporting results to Zephyr...");
        Boolean shouldReport = Boolean.valueOf(AppContext.getContext().getGeneralProperties().getProperty("report"));
        if (!shouldReport) {
            log.info("Reporting skipped");
            return;
        }

        getTestCasesFromProject("GQ");

        Cycle cycle = getCycle();
        Assert.assertTrue(cycle != null);

        List<TestCase> testCases = reportResults.getTestCases();
        log.info("Add test results to cycle...");
        log.info("Test result size: " + testCases.size());
        for (TestCase testCase : testCases) {
            log.info("Scenario: " + testCase.getTitle() + ", status: " + testCase.getStatus());
            if (issueIdMap.containsKey(testCase.getTitle())) {
                // create test
                Execution execution = addTestToCycle(cycle, issueIdMap.get(testCase.getTitle()));
                testCase.setUrl(connectionProperties.getProperty("server") + "/browse/" + execution.getIssueKey());

                log.info("URL: " + testCase.getUrl());
                for (Step step : testCase.getSteps()) {
                    log.debug("Step: " + step.getName() + ", status: " + step.getStatus());
                }

                // set test result
                switch ( testCase.getStatus() ) {
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
