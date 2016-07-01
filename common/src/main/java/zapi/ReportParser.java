package zapi;

import errors.NullReturnException;
import jira.JiraConnector;
import json.JsonCoverter;
import model.AppContext;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import zapi.model.*;

import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ReportParser {

    private ZAPI zapi = new ZAPI();
    private ObjectMapper mapper = new ObjectMapper();
    private List<TestResult> results = new ArrayList<>();
    private HashMap<String, Integer> issueIdMap = new HashMap<>();
    private Properties connectionProperties = AppContext.getContext().getJiraConnection();
    private Logger log = Logger.getRootLogger();


    private void parseXmlReportFiles(String pathToXml){
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(pathToXml), "*.xml")) {
            for (Path file: stream) {
                if(!file.toFile().isDirectory()) {

                    log.debug("Parse file: " + file.getFileName());

                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(new File(String.valueOf(file)));

                    doc.getDocumentElement().normalize();

                    NodeList testList = doc.getElementsByTagName("test-case");
                    if (testList.getLength() != 0) {
                        for (int i = 0; i < testList.getLength(); i++){
                            Node testNode = testList.item(i);
                            if (testNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element testElement = (Element) testNode;
                                TestResult result = new TestResult().setResult(testElement.getAttribute("status"));
                                NodeList nameList = testElement.getElementsByTagName("name");
                                Node nameNode = nameList.item(0);
                                if(nameNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element nameElement = (Element) nameNode;
                                    result.setScenario(nameElement.getTextContent());
                                }
                                results.add(result);
                            }
                        }
                    } else {
                        log.warn("file not contain any tests");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        log.info("Add new test-case to cycle...");
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
        log.info("Set execution result for issueKey: " + execution.getIssueKey() + ", result: " + result);

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
    private void setIssueMapForProject(String projectKey) {
        // TODO batch load data
        Response response = zapi.JQL(0, 1000, "id,key,summary", "project=" + projectKey + " and issuetype = Test");

        if (response.getStatus() == 200) {
            try {
                IssueList issueList = mapper.readValue(response.readEntity(String.class), IssueList.class);
                log.info("Test list size : " + issueList.getIssues().size());
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
    public void reportToZephyr(String path){
        log.info("Reporting results to Zephyr...");
        Boolean shouldReport = Boolean.valueOf(AppContext.getContext().getGeneralProperties().getProperty("report"));
        if (!shouldReport) {
            return;
        }
        setIssueMapForProject("GQ");
        parseXmlReportFiles(path);

        // Create test cycle
        Cycle cycle = getCycle();
        Assert.assertTrue(cycle != null);

        log.debug("Test result size: " + results.size());

        // Add test results to cycle
        for (TestResult result : results) {
            // create test
            if (issueIdMap.containsKey(result.getScenario())) {
                Execution execution = addTestToCycle(cycle, issueIdMap.get(result.getScenario()));
                // set test result
                switch ( result.getResult() ) {
                    case "passed":
                        setExecutionResult(execution, ZAPI.PASS);
                        break;
                    case "failed":
                        setExecutionResult(execution, ZAPI.FAIL);
                        break;
                    default:
                        setExecutionResult(execution, ZAPI.UNEXECUTED);
                        break;
                }
            } else {
                log.warn(
                        "Test not present in issueIdMap!" +
                                "summary: " + result.getScenario() + "\n" +
                                "status: " + result.getResult()
                );
            }
        }
    }
}
