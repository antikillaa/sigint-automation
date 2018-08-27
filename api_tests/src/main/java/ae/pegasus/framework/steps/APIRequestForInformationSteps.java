package ae.pegasus.framework.steps;

import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.services.RequestForInformationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class APIRequestForInformationSteps extends APISteps {

    public static RequestForInformationService serviceRequestForInformation = new RequestForInformationService();

    @When("I send generate RFI number request")
    public void sendGenerateRFINumberRequest() {
        Result rfiNo = new Result();
        OperationResult<Result> operationResult = serviceRequestForInformation.generateNumber();
        rfiNo.setResult(operationResult.getEntity().getResult());
        context.put("rfiNo", rfiNo);
    }

    @When("I send create a RFI request")
    public void sendCreateRFIRequest() {
        RequestForInformation requestForInformation = new RequestForInformation();
        Result rfitNo = context.get("rfiNo", Result.class);
        serviceRequestForInformation.buildRFI(requestForInformation, rfitNo);
        context.put("requestForInformation", requestForInformation);
        serviceRequestForInformation.add(requestForInformation);
    }

    @When("I send view a RFI request")
    public void sendViewRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        String id = lastRFI.getId();
        serviceRequestForInformation.view(id);
    }

    @When("I send delete a RFI request")
    public void sendDeleteRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        serviceRequestForInformation.remove(lastRFI);
    }

    @When("I send get owner members a RFI request")
    public void sendGetOwnersMembersRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        OperationResult<List<CurrentOwner>> currentOwnerMembers = serviceRequestForInformation.possibleOwnersMembers(lastRFI);
        context.put("currentOwner", currentOwnerMembers.getEntity());
    }

    @When("I send get owner teams a RFI request")
    public void sendGetOwnersTeamsRFIRequest() {
        RequestForInformation requestForInformation = new RequestForInformation();
        Result rfitNo = context.get("rfiNo", Result.class);
        serviceRequestForInformation.buildRFI(requestForInformation, rfitNo);
        OperationResult<List<CurrentOwner>> currentOwnersTeams = serviceRequestForInformation.possibleOwnersTeams(requestForInformation);
        context.put("currentOwner", currentOwnersTeams.getEntity());
        context.put("requestForInformation", requestForInformation);
    }

    @When("I send submit a RFI request")
    public void sendSubmitRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        List<CurrentOwner> currentOwner = context.get("currentOwner", List.class);
        List<NextOwners> allOwners = new ArrayList<>();
        serviceRequestForInformation.setNextOwnersMember(currentOwner, allOwners);
        lastRFI.setNextOwners(allOwners);
        lastRFI.setComment("QE_auto " + RandomStringUtils.randomAlphabetic(5));
        serviceRequestForInformation.submit(lastRFI);
    }

    @When("I send complete took ownership a RFI request")
    public void sendSubmitTookOwnershipRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        lastRFI.setComment("QE_auto " + RandomStringUtils.randomAlphabetic(5));
        serviceRequestForInformation.submitTookOwnership(lastRFI);
    }

    @When("I send Approve a RFI request")
    public void sendApproveRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        List<OrgUnit> currentOrgUnits = lastRFI.getOrgUnits();
        List<NextOwners> nextOwners = new ArrayList<>();
        serviceRequestForInformation.setNextOwnersTeam(currentOrgUnits, nextOwners);
        lastRFI.setNextOwners(nextOwners);
        serviceRequestForInformation.approve(lastRFI);
    }

    @When("I send cancel a RFI request")
    public void sendCancelRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        lastRFI.setComment("QE_auto " + RandomStringUtils.randomAlphabetic(5));
        serviceRequestForInformation.cancel(lastRFI);
    }

    @When("I send send a RFI request")
    public void sendSendRFIRequest() {
        RequestForInformation RFI = context.get("requestForInformation", RequestForInformation.class);
        List<OrgUnit> currentOrgUnits = RFI.getOrgUnits();
        List<NextOwners> nextOwners = new ArrayList<>();
        serviceRequestForInformation.setNextOwnersTeam(currentOrgUnits, nextOwners);
        RFI.setNextOwners(nextOwners);
        serviceRequestForInformation.send(RFI);
    }

    @When("I send take ownership a RFI request")
    public void sendTakeOwnershipRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        lastRFI.setComment("QE_auto " + RandomStringUtils.randomAlphabetic(5));
        serviceRequestForInformation.takeOwnership(lastRFI);
    }

    @When("I send unassign a RFI request")
    public void sendUnassignRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        lastRFI.setComment("QE_auto " + RandomStringUtils.randomAlphabetic(5));
        serviceRequestForInformation.unassign(lastRFI);
    }

    @When("I send edit a RFI request")
    public void sendEditRFIRequest() {
        RequestForInformation createdRFI = Entities.getRequestForInformations().getLatest();
        createdRFI.setSubject("QE_auto " + RandomStringUtils.randomAlphabetic(5));
        createdRFI.setRequired("QE_auto " + RandomStringUtils.randomAlphabetic(5));
        context.put("requestForInformation", createdRFI);
        serviceRequestForInformation.add(createdRFI);
        checkRFI(createdRFI);
    }

    @Then("RFI is correct")
    public void rfiIsCreated() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        checkRFI(lastRFI);
    }

    private void checkRFI(RequestForInformation lastRFI) {
        RequestForInformation createdRFI = context.get("requestForInformation", RequestForInformation.class);
        assertEquals(lastRFI.getClassification(), createdRFI.getClassification());
        assertEquals(lastRFI.getManualNo(), createdRFI.getManualNo());
        assertEquals(lastRFI.getRequired(), createdRFI.getRequired());
        assertEquals(lastRFI.getObjectType(), "RFI");
    }

    @Then("RFI is submitted")
    public void rfiIsSubmitted() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        assertEquals(lastRFI.getState(), "Awaiting Approval");
        assertEquals(lastRFI.getStateId(), "2");
        assertEquals(lastRFI.getStateType(), "INITIAL");
        assertEquals(lastRFI.getWfId(), "2");
    }

    @Then("RFI is approved")
    public void rfiIsApproved() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        assertEquals(lastRFI.getState(), "Awaiting Assignment");
        assertEquals(lastRFI.getStateId(), "3");
        assertEquals(lastRFI.getStateType(), "INITIAL");
        assertEquals(lastRFI.getWfId(), "2");
    }

    @Then("RFI is cancelled")
    public void rfiIsCancelled() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        assertEquals(lastRFI.getState(), "Cancelled");
        assertEquals(lastRFI.getStateId(), "7");
        assertEquals(lastRFI.getStateType(), "FINAL");
        assertEquals(lastRFI.getWfId(), "2");
    }

    @Then("RFI is ownershipped")
    public void rfiIsOwnershipped() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        assertEquals(lastRFI.getState(), "Under Assignment");
        assertEquals(lastRFI.getStateId(), "4");
        assertEquals(lastRFI.getStateType(), "IN_PROGRESS");
        assertEquals(lastRFI.getWfId(), "2");
    }

    @Then("RFI is completed")
    public void rfiIsComplited() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        assertEquals(lastRFI.getState(), "Completed");
        assertEquals(lastRFI.getStateId(), "5");
        assertEquals(lastRFI.getStateType(), "FINAL");
        assertEquals(lastRFI.getWfId(), "2");
    }

    @Then("RFI is unassigned")
    public void rfiIsUnassigned() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        assertEquals(lastRFI.getState(), "Awaiting Assignment");
        assertEquals(lastRFI.getStateId(), "3");
        assertEquals(lastRFI.getStateType(), "INITIAL");
        assertEquals(lastRFI.getWfId(), "2");
    }
}