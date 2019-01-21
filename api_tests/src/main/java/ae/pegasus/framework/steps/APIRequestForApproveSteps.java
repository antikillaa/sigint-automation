package ae.pegasus.framework.steps;


import ae.pegasus.framework.controllers.APILogin;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.model.information_managment.CurrentOwner;
import ae.pegasus.framework.model.information_managment.Link;
import ae.pegasus.framework.model.information_managment.NextOwners;
import ae.pegasus.framework.model.information_managment.PossibleActions;
import ae.pegasus.framework.model.information_managment.rfa.RequestForApprove;
import ae.pegasus.framework.services.RequestForApproveService;
import ae.pegasus.framework.services.SearchService;
import org.apache.commons.lang3.RandomStringUtils;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class APIRequestForApproveSteps extends APISteps {

    public static RequestForApproveService serviceRequestForApprove = new RequestForApproveService();
    private static APIReportSteps serviceReport = new APIReportSteps();

    @When("I send generate RFA number request")
    public void sendGenerateRFANumberRequest() {
        Result rfaNo = new Result();
        OperationResult<Result> operationResult = serviceRequestForApprove.generateNumber();
        rfaNo.setResult(operationResult.getEntity().getResult());
        context.put("rfaNo", rfaNo);
    }

    @When("I save logged user")
    public void saveLoggedUser() {
        LoggedUser loggedUser = appContext.getLoggedUser();
        context.put("loggedUser", loggedUser);
    }

    @When("I get allowed RFA actions")
    public void sendGetAllowedMRActions() {
        String imId = Entities.getMasterReports().getLatest() == null ? "" : Entities.getRequestForApproves().getLatest().getId();
        OperationResult<List<PossibleActions>> operationResult = serviceRequestForApprove.possibleaction(imId);
        List<PossibleActions> possibleActions = operationResult.getEntity();
        context.put("possibleActions", possibleActions);
    }

    @When("I send $state a RFA request")
    public void sendMoveToStateRequest(String state) {
        switch (state) {
            case "Save as Draft":
                saveAsDraft(state);
                break;
            case "View":
                break;
            case "Delete":
                break;
            case "Save":
                break;
            case "Submit for Review":
                break;
            case "Cancel":
                break;
            case "Take Ownership":
                break;
            case "Assign":
                break;
            case "Unassign":
                break;
            case "Re-assign":
                break;
            case "Return to Author":
                break;
            case "Approve":
                break;
            case "Reject":
                break;
            default:
                log.error("State is not found");
        }
    }

    public void saveAsDraft(String state) {
        RequestForApprove requestForApprove = new RequestForApprove();
        Result rfaNo = context.get("rfaNo", Result.class);
        List<SearchRecord> entities = context.get("searchEntities", List.class);

        RequestForApprove rfi = new RequestForApprove();

        if (state.equals("Save as Draft")) {
            serviceRequestForApprove.buildRFA(requestForApprove, rfaNo, entities);
            String actionId = serviceReport.getRequestAdress(state);
            context.put("requestForApprove", rfi);
            sleep(60000); //FIXME
            OperationResult<RequestForApprove> operationResult = serviceRequestForApprove.add(requestForApprove, actionId);
            RequestForApprove reportResult = operationResult.getEntity();
            context.put("reportID", reportResult.getId());

        } else if (state.equals("Save")) {
            RequestForApprove report = Entities.getRequestForApproves().getLatest();
            String actionId = serviceReport.getRequestAdress(state);
            report.setSubject("qe_" + RandomStringUtils.randomAlphabetic(10));
            OperationResult<RequestForApprove> operationResult = serviceRequestForApprove.add(report, actionId);
            RequestForApprove reportResult = operationResult.getEntity();
            context.put("expectedReport", reportResult);
        }
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
        sleep(60000); //FIXME
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
        sleep(50000); //FIXME
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

    @When("I send take ownership a RFA request")
    public void sendTakeOwnershipRFARequest() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        serviceRequestForApprove.takeOwnership(lastRFA);
    }

    @When("I send remove ownership a RFA request")
    public void sendRemoveOwnershipRFARequest() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        lastRFA.setComment("Auto QE " + RandomStringUtils.random(5));
        serviceRequestForApprove.removeOwnership(lastRFA);
    }

    @When("I send reject a RFA request")
    public void sendRejectRFARequest() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        lastRFA.setComment("Auto QE " + RandomStringUtils.random(5));
        serviceRequestForApprove.reject(lastRFA);
    }

    @When("I send approve a RFA request")
    public void sendApproveRFARequest() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        lastRFA.setComment("Auto QE " + RandomStringUtils.random(5));
        serviceRequestForApprove.approve(lastRFA);
    }

    @When("I send search for accessed audio request")
    public void sendGotAccessAudioRequest() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();

        CBSearchFilter filter = getIdLink(lastRFA);

        SearchService serviceSearch = new SearchService();
        OperationResult<List<SearchEntity>> operationResult = serviceSearch.search(filter);
        List<SearchEntity> searchEntities = operationResult.getEntity();
        assertNotNull(searchEntities);
        context.put("searchEntities", searchEntities);

    }

    private CBSearchFilter getIdLink(RequestForApprove lastRFA) {
        List<String> ids = new ArrayList<>();
        for (Link requestForApprove : lastRFA.getLinks()) {
            String request = requestForApprove.getLinkId();
            ids.add(request);
        }
        String id = ids.toString()
                .replace(",", "|")
                .replaceAll("[\\[\\]]", "");
        CBSearchFilter filter = context.get("cbSearchFilter", CBSearchFilter.class);
        filter.setQuery("id:(" + id + ")");
        return filter;
    }

    @When("I as login first user")
    public void loginAsFirstUser() {
        User loggedUser = context.get("loggedUser", LoggedUser.class).getUser();
        String email = loggedUser.getName();
        String password = loggedUser.getPassword();
        APILogin login = new APILogin();
        login.signInAsUser(email, password);
        checkResultSuccess();
    }

    @Then("Audio content is available")
    public void audioIsAvailable() {
        List<SearchRecord> searchRecord = context.get("searchEntities", List.class);
        for (SearchRecord searchEntity : searchRecord) {
            ArrayList<String> ids = (ArrayList<String>) searchEntity.getAttributes().get("UPLOAD_M4A_FILE_ID");
            assertNotNull(ids);
        }
    }

    @Then("User able access to audio")
    public void userAccessToAudio() {
        List<SearchRecord> searchEntities = context.get("searchEntities", List.class);
        for (SearchRecord searchRecord : searchEntities) {
            String id = searchRecord.getAttributes().get("UPLOAD_M4A_FILE_ID")
                    .toString()
                    .replaceAll("[\\[\\]]", "");
            serviceRequestForApprove.getAudioContent(id);
        }
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

    @Then("RFA is ownershipped")
    public void rfaIsOwnershipped() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        checkRFA(lastRFA);
        assertEquals(lastRFA.getState(), "Under Approval");
        assertEquals(lastRFA.getStateId(), "3");
        assertEquals(lastRFA.getStateType(), "IN_PROGRESS");
        assertEquals(lastRFA.getWfId(), "4");
    }

    @Then("RFA is rejected")
    public void rfaIsRejected() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        checkRFA(lastRFA);
        assertEquals(lastRFA.getState(), "Rejected");
        assertEquals(lastRFA.getStateId(), "5");
        assertEquals(lastRFA.getStateType(), "FINAL");
        assertEquals(lastRFA.getWfId(), "4");
    }

    @Then("RFA is unownershipped")
    public void rfaIsUnownershipped() {
        rfaIsSentForApproval();
    }

    @Then("RFA is approved")
    public void rfaIsApproved() {
        RequestForApprove lastRFA = Entities.getRequestForApproves().getLatest();
        checkRFA(lastRFA);
        assertEquals(lastRFA.getState(), "Approved");
        assertEquals(lastRFA.getStateId(), "4");
        assertEquals(lastRFA.getStateType(), "FINAL");
        assertEquals(lastRFA.getWfId(), "4");
    }
}
