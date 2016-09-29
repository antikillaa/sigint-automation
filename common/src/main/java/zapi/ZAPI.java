package zapi;

import app_context.properties.G4Properties;
import http.G4HttpClient;
import http.G4Response;
import http.HttpMethod;
import http.requests.HttpRequest;
import zapi.model.Cycle;
import zapi.model.Execution;
import zapi.model.ExecutionStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Zephyr API
 */
class ZAPI {

    private String server = G4Properties.getJiraProperties().getJiraServer();
    private G4HttpClient g4HttpClient = new G4HttpClient().setHost(server);
    private String username = G4Properties.getJiraProperties().getUsername();
    private String password = G4Properties.getJiraProperties().getPassword();

    static final String UNEXECUTED = "-1";
    static final String PASS = "1";
    static final String FAIL = "2";
    static final String WIP = "3";
    static final String BLOCKED = "4";


    /**
     * Retrieve a Test Cycle
     *
     * @param cycleId Test Cycle id
     * @return G4Response
     */
    G4Response getCycle(int cycleId) {
        String url = "/rest/zapi/latest/cycle/" + cycleId;
        HttpRequest request = new HttpRequest(url);
        return g4HttpClient.sendRequest(request, username, password);
    }

    /**
     * Create a Test Cycle
     *
     * @param cycle test cycle
     * @return G4Response
     */
    G4Response postCycle(Cycle cycle) {
        String url = "/rest/zapi/latest/cycle";

        HttpRequest request = new HttpRequest(url)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(cycle);

        return g4HttpClient.sendRequest(request, username, password);
    }

    /**
     * Update a Test Cycle
     * <p>
     * cycleId shouldBe set
     *
     * @param cycle test cycle
     * @return G4Response
     */
    G4Response putCycle(Cycle cycle) {
        String url = "/rest/zapi/latest/cycle";

        HttpRequest request = new HttpRequest(url)
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(cycle.toString());

        return g4HttpClient.sendRequest(request, username, password);
    }

    /**
     * Delete a Test Cycle
     *
     * @param cycleId test cycle id
     * @return G4Response
     */
    G4Response deleteCycle(int cycleId) {
        String url = "/rest/zapi/latest/cycle/" + cycleId;
        HttpRequest request = new HttpRequest(url).setHttpMethod(HttpMethod.DELETE);
        return g4HttpClient.sendRequest(request, username, password);
    }


    /**
     * Retrieve (Z)ephyr (Q)uery (L)anguage statements based on QueryParams.
     * <p>
     * QueryParam(s):
     * zqlQuery\*, expand, maxRecords, startIndex.  (2.0, 2.1)
     * zqlQuery\*, expand, maxRecords, offset.  (2.2 or later)
     * <p>
     * <p>ZQL Reference
     * <br>LIST OF FIELDS: component, creationDate, cycleName, executedBy, execution, executionDate, executionStatus, executionDefectKey, fixVersion, issue, priority, project
     * <br>LIST OF OPERATORS: =, !=, <=, >=, >, <, is not, is, not in, in,
     * <br>LIST OF KEYWORDS: AND, OR, NOT,ORDER BY
     *
     * @return G4Response
     */
    G4Response ZQLExecuteSearch(int maxRecords, int offset, String zqlQuery) {
        String url = "/rest/zapi/latest/zql/executeSearch" +
                "?maxRecords=" + maxRecords +
                "&offset=" + offset +
                "&zqlQuery=" + zqlQuery;

        HttpRequest request = new HttpRequest(url);
        return g4HttpClient.sendRequest(request, username, password);
    }

    /**
     * Retrieve all Executions available by issueId.
     * QueryParam(s): issueId*.
     *
     * @return G4Response
     */
    G4Response getExecution(int issueId) {
        String url = "/rest/zapi/latest/execution";
        HttpRequest request = new HttpRequest(url);
        return g4HttpClient.sendRequest(request, username, password);
    }

    /**
     * Add execution to test cycle
     *
     * @param execution objectToJson
     * @return G4Response
     */
    G4Response postExecution(Execution execution) {
        String url = "/rest/zapi/latest/execution";

        HttpRequest request = new HttpRequest(url)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(execution);

        return g4HttpClient.sendRequest(request, username, password);
    }

    /**
     * Update execution status
     * <p>
     * List of status:
     * -1 = UNEXECUTED,
     * 1 = PASS,
     * 2 = FAIL,
     * 3 = WIP,
     * 4 = BLOCKED
     *
     * @return G4Response
     */
    G4Response putExecution(int executionId, String status) {
        ExecutionStatus executionStatus = new ExecutionStatus().setStatus(status);
        String url = "/rest/zapi/latest/execution/" + executionId + "/execute";

        HttpRequest request = new HttpRequest(url)
                .setHttpMethod(HttpMethod.PUT)
                .setPayload(executionStatus);

        return g4HttpClient.sendRequest(request, username, password);
    }

    G4Response JQL(int startAt, int maxResults, String fields, String jql) {
        try {
            jql = URLEncoder.encode(jql, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new Error("Unable to encode JQL query to UTF-8: " + jql);
        }

        String url = "/rest/api/latest/search" +
                "?startAt=" + startAt +
                "&maxResults=" + maxResults +
                "&fields=" + fields +
                "&jql=" + jql;

        HttpRequest request = new HttpRequest(url);

        return g4HttpClient.sendRequest(request, username, password);
    }

    /**
     * GET request: Getting test cycles request for current version of project
     *
     * @param projectId project id
     * @param versionId version id
     * @return G4Response
     */
    G4Response getCycles(String projectId, String versionId) {
        String url = "/rest/zapi/latest/cycle?projectId=" + projectId + "&versionId=" + versionId;

        HttpRequest request = new HttpRequest(url);
        return g4HttpClient.sendRequest(request, username, password);
    }

}
