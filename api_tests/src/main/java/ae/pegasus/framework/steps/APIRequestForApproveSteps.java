package ae.pegasus.framework.steps;


import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.RequestForApprove;
import ae.pegasus.framework.model.Result;
import ae.pegasus.framework.model.SearchRecord;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.services.RequestForApproveService;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class APIRequestForApproveSteps extends APISteps {

    public static RequestForApproveService serviceRequestForApprove = new RequestForApproveService();

    @When("I send generate RFA number request")
    public void sendGenerateRFANumberRequest() {
        Result rfaNo = new Result();
        OperationResult<Result> operationResult = serviceRequestForApprove.generateNumber();
        rfaNo.setResult(operationResult.getEntity().getResult());
        context.put("rfaNo", rfaNo);
    }

    @When("I send create a RFA request")
    public void sendCreateRFARequest() {
        RequestForApprove requestForApprove = new RequestForApprove();
        Result rfaNo = context.get("rfaNo", Result.class);
        List<SearchRecord> entities = context.get("searchEntities", List.class);
        serviceRequestForApprove.buildRFA(requestForApprove, rfaNo, entities);
        context.put("requestForApprove", requestForApprove);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serviceRequestForApprove.add(requestForApprove);
    }

    @When("I send view a RFA request")
    public void sendViewRFARequest() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        String id = lastRFA.getId();
        serviceRequestForApprove.view(id);
    }

    @When("I send delete a RFA request")
    public void sendDeleteRFARequest() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        serviceRequestForApprove.remove(lastRFA);
    }


    @Then("RFA is created")
    public void rfaIsCreated() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        checkRFA(lastRFA);
    }

    private void checkRFA(RequestForApprove lastRFA) {
        RequestForApprove createdRFA = context.get("requestForApprove", RequestForApprove.class);
        assertEquals(lastRFA.getClassification(), createdRFA.getClassification());
        assertEquals(lastRFA.getInternalRequestNo(), createdRFA.getInternalRequestNo());
        assertEquals(lastRFA.getDescription(), createdRFA.getDescription());
        assertEquals(lastRFA.getSubject(), createdRFA.getSubject());
        assertEquals(lastRFA.getObjectType(), "RFA");
    }

    @Then("RFA is deleted")
    public void rfaIsDeleted() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        checkRFA(lastRFA);
        assertEquals(lastRFA.getState(), "Deleted");
        assertEquals(lastRFA.getStateId(), "0");
        assertEquals(lastRFA.getStateType(), "DELETED");
        assertEquals(lastRFA.getWfId(), "4");
    }
}
