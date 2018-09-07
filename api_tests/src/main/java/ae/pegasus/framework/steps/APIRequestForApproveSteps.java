package ae.pegasus.framework.steps;


import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.services.RequestForApproveService;
import org.apache.commons.lang.RandomStringUtils;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.ArrayList;
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
        sleep(60000);
        serviceRequestForApprove.add(requestForApprove);
    }

    @When("I send get owner teams a RFA request")
    public void sendGetOwnerRFARequest() {
        RequestForApprove requestForApprove = new RequestForApprove();
        Result rfaNo = context.get("rfaNo", Result.class);
        List<SearchRecord> entities = context.get("searchEntities", List.class);
        serviceRequestForApprove.buildRFA(requestForApprove, rfaNo, entities);
        OperationResult<List<CurrentOwner>> currentOwnersTeams = serviceRequestForApprove.possibleOwnersTeams(requestForApprove);
        context.put("currentOwner", currentOwnersTeams.getEntity());
        context.put("requestForApprove", requestForApprove);
    }

    @When("I send Send for approval a RFA request")
    public void sendSendRFIRequest() {
        RequestForApprove RFA = context.get("requestForApprove", RequestForApprove.class);
        List<OrgUnit> currentOrgUnits = RFA.getOrgUnits();
        List<NextOwners> nextOwners = new ArrayList<>();
        serviceRequestForApprove.setNextOwnersTeam(currentOrgUnits, nextOwners);
        RFA.setNextOwners(nextOwners);
        sleep(60000);
        serviceRequestForApprove.sendForApprove(RFA);
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

    @When("I send cancel a RFA request")
    public void sendCancelRFARequest() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        lastRFA.setComment("Auto QE " + RandomStringUtils.random(5));
        sleep(50000);
        serviceRequestForApprove.cancel(lastRFA);
    }

    @When("I send update a RFA request")
    public void sendUpdateRFARequest() {
        RequestForApprove requestForApprove = new RequestForApprove();
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        lastRFA.setDescription("QE Auto" + RandomStringUtils.randomAlphabetic(5));
        lastRFA.setSubject("QE Auto" + RandomStringUtils.randomAlphabetic(5));
        context.put("requestForApprove", requestForApprove);
        serviceRequestForApprove.update(lastRFA);
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
        assertEquals(lastRFA.getState(), "Deleted");
        assertEquals(lastRFA.getStateId(), "0");
        assertEquals(lastRFA.getStateType(), "DELETED");
        assertEquals(lastRFA.getWfId(), "4");
    }

    @Then("RFA is Sent for Approval")
    public void rfaIsSentForApproval() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        checkRFA(lastRFA);
        assertEquals(lastRFA.getState(), "Awaiting Approval");
        assertEquals(lastRFA.getStateId(), "2");
        assertEquals(lastRFA.getStateType(), "INITIAL");
        assertEquals(lastRFA.getWfId(), "4");
    }

    @Then("RFA is cancelled")
    public void rfaIsCancelled() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        checkRFA(lastRFA);
        assertEquals(lastRFA.getState(), "Cancelled");
        assertEquals(lastRFA.getStateId(), "6");
        assertEquals(lastRFA.getStateType(), "FINAL");
        assertEquals(lastRFA.getWfId(), "4");
    }
}
