package zapi;

import errors.NullReturnException;
import model.AppContext;
import rs.client.JsonCoverter;
import rs.client.RsClient;
import zapi.model.Cycle;
import zapi.model.CyclesList;
import zapi.model.Execution;

import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ZAPI {

    private RsClient rsClient = new RsClient();
    private String server = AppContext.getContext().getJiraConnection().getProperty("server");
    public static final short UNEXECUTED = -1;
    public static final short PASS = 1;
    public static final short FAIL = 2;
    public static final short WIP = 3;
    public static final short BLOCKED = 4;




    /**
     * Retrieve a Test Cycle
     *
     * @param cycleId Test Cycle id
     * @return JAX-RS Response
     */
    public Response getCycle(int cycleId){
        return rsClient.get(
                server + "/rest/zapi/latest/cycle/" + cycleId
        );
    }

    /**
     * Create a Test Cycle
     *
     * @param cycle test cycle
     * @return JAX-RS Response
     */
    public Response postCycle(Cycle cycle){
        return rsClient.post(
                server + "/rest/zapi/latest/cycle",
                cycle.postJson()
        );
    }

    /**
     * Update a Test Cycle
     *
     * cycleId shouldBe set
     *
     * @param cycle test cycle
     * @return JAX-RS Response
     */
    public Response putCycle(Cycle cycle){
        return rsClient.put(
                server + "/rest/zapi/latest/cycle",
                cycle.toString()
        );
    }

    /**
     * Delete a Test Cycle
     *
     * @param cycleId test cycle id
     * @return JAX-RS Response
     */
    public Response deleteCycle(int cycleId){
        return rsClient.delete(
                server + "/rest/zapi/latest/cycle/" + cycleId
        );
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
    public Response ZQLExecuteSearch(int maxRecords, int offset, String zqlQuery){
        return rsClient.get(
                server + "/rest/zapi/latest/zql/executeSearch" +
                        "?maxRecords=" + maxRecords +
                        "&offset=" + offset +
                        "&zqlQuery=" +   zqlQuery
        );
    }

    /**
     * Retrieve all Executions available by issueId.
     * QueryParam(s): issueId*.
     *
     * @return JAX-RS Response
     */
    public Response getExecution(int issueId){
        return rsClient.get(
                server + "/rest/zapi/latest/execution"
        );
    }

    /**
     * Add execution to test cycle
     *
     * @param execution objectToJson
     * @return Response
     */
    public Response postExecution(Execution execution){
        return rsClient.post(
                server + "/rest/zapi/latest/execution",
                execution.toString()
        );
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
    public Response putExecution(int executionId, short status){
        return rsClient.put(
                server + "/rest/zapi/latest/execution/" + executionId +"/execute",
                "{ \"status\": " + status + " }"
        );
    }

    public Response JQL(int startAt, int maxResults, String fields, String jql) {
        try {
            jql = URLEncoder.encode(jql, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rsClient.get(
                server + "/rest/api/latest/search" +
                        "?startAt=" + startAt +
                        "&maxResults=" + maxResults +
                        "&fields=" + fields +
                        "&jql=" + jql
        );
    }

    public CyclesList getCycles(String projectId, String versionId) throws NullReturnException {
        Response response = rsClient.get(server + "/rest/zapi/latest/cycle?projectId="+projectId+
                "&versionId="+versionId);
        String jsonString = response.readEntity(String.class);
        CyclesList cycles = JsonCoverter.fromJsonToObject(jsonString, CyclesList.class);
        return cycles;
    }

}
