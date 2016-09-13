package zapi;

import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import json.JsonCoverter;
import model.AppContext;
import zapi.model.Cycle;
import zapi.model.CyclesList;
import zapi.model.Execution;
import zapi.model.ExecutionStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class ZAPI {

    private G4Client g4Client = new G4Client();
    private String server = AppContext.getContext().getJiraConnection().getProperty("server");

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
    G4Response getCycle(int cycleId){
        String url = server + "/rest/zapi/latest/cycle/" + cycleId;
        return g4Client.get(url);
    }

    /**
     * Create a Test Cycle
     *
     * @param cycle test cycle
     * @return JAX-RS Response
     */
    G4Response postCycle(Cycle cycle){
        String url = server + "/rest/zapi/latest/cycle";
        return g4Client.post(url, cycle.postJson());
    }

    /**
     * Update a Test Cycle
     *
     * cycleId shouldBe set
     *
     * @param cycle test cycle
     * @return JAX-RS Response
     */
    G4Response putCycle(Cycle cycle){
        String url = server + "/rest/zapi/latest/cycle";
        return g4Client.put(url, cycle.toString());
    }

    /**
     * Delete a Test Cycle
     *
     * @param cycleId test cycle id
     * @return JAX-RS Response
     */
    G4Response deleteCycle(int cycleId){
        String url = server + "/rest/zapi/latest/cycle/" + cycleId;
        return g4Client.delete(url);
    }


    /**
     * Retrieve (Z)ephyr (Q)uery (L)anguage statements based on QueryParams.
     *
     * QueryParam(s):
     * zqlQuery\*, expand, maxRecords, startIndex.  (2.0, 2.1)
     * zqlQuery\*, expand, maxRecords, offset.  (2.2 or later)
     *
     * <p>ZQL Reference
     * <br>LIST OF FIELDS: component, creationDate, cycleName, executedBy, execution, executionDate, executionStatus, executionDefectKey, fixVersion, issue, priority, project
     * <br>LIST OF OPERATORS: =, !=, <=, >=, >, <, is not, is, not in, in,
     * <br>LIST OF KEYWORDS: AND, OR, NOT,ORDER BY
     *
     * @return JAX-RS Response
     */
    G4Response ZQLExecuteSearch(int maxRecords, int offset, String zqlQuery){
        String url = server + "/rest/zapi/latest/zql/executeSearch" +
                "?maxRecords=" + maxRecords +
                "&offset=" + offset +
                "&zqlQuery=" +   zqlQuery;

        return g4Client.get(url);
    }

    /**
     * Retrieve all Executions available by issueId.
     * QueryParam(s): issueId*.
     *
     * @return JAX-RS Response
     */
    G4Response getExecution(int issueId){
        String url = server + "/rest/zapi/latest/execution";
        return g4Client.get(url);
    }

    /**
     * Add execution to test cycle
     *
     * @param execution objectToJson
     * @return Response
     */
    G4Response postExecution(Execution execution){
        String url = server + "/rest/zapi/latest/execution";
        return g4Client.post(url, execution.toString());
    }

    /**
     * Update execution status
     *
     * List of status:
     * -1 = UNEXECUTED,
     * 1 = PASS,
     * 2 = FAIL,
     * 3 = WIP,
     * 4 = BLOCKED
     *
     *  @return JAX-RS Response
     */
    G4Response putExecution(int executionId, String status){
        ExecutionStatus executionStatus = new ExecutionStatus().setStatus(status);
        String url = server + "/rest/zapi/latest/execution/" + executionId + "/execute";

        return g4Client.put(url, executionStatus);
    }

    G4Response JQL(int startAt, int maxResults, String fields, String jql) {
        try {
            jql = URLEncoder.encode(jql, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new Error("Unable to encode JQL query to UTF-8: " + jql);
        }

        String url = server + "/rest/api/latest/search" +
                "?startAt=" + startAt +
                "&maxResults=" + maxResults +
                "&fields=" + fields +
                "&jql=" + jql;

        return g4Client.get(url);
    }

    CyclesList getCycles(String projectId, String versionId) throws NullReturnException {
        String url = server + "/rest/zapi/latest/cycle?projectId=" + projectId + "&versionId=" + versionId;
        G4Response response = g4Client.get(url);

        return JsonCoverter.fromJsonToObject(response.getMessage(), CyclesList.class);
    }

}
