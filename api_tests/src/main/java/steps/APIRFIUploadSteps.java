package steps;

import errors.NullReturnException;
import model.AppContext;
import model.FileAttachment;
import model.InformationRequest;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.type.MapType;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import rs.client.JsonCoverter;
import services.RFIService;

import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * Created by dm on 4/15/16.
 */
public class APIRFIUploadSteps {

    private Logger log = Logger.getRootLogger();
    static RFIService service = new RFIService();
    private AppContext context = AppContext.getContext();

    @When("I send create RFI request")
    public void createRFI() {
        log.info("Starting step of creating new RFI");
        InformationRequest rfi = new InformationRequest().generate();
        FileAttachment originalFile = new FileAttachment("original");
        FileAttachment approvedFile = new FileAttachment("approved");
        rfi.addFileAttachment(originalFile);
        rfi.addFileAttachment(approvedFile);
        Response response = service.addNew(rfi);
        readRFIfromJson(response);
        context.putToRunContext("createdRfi", rfi);
        context.putToRunContext("code", response.getStatus());
        log.info("RFI is created");

    }

    private void readRFIfromJson(Response response) {
        String jsonString;
        if (response.getStatus() == 200) {
            jsonString = response.readEntity(String.class);
        } else {
            log.warn("There is no RFI in response with code:" + response.getStatus());
            return;
        }
        InformationRequest createdRfi;
        MapType mapType = JsonCoverter.constructMapTypeToValue(InformationRequest.class);
        try {
            HashMap<String, InformationRequest> map = JsonCoverter.mapper.readValue(jsonString, mapType);
            createdRfi = map.get("result");
            if (response.getStatus() == 200) {
                context.getEntitiesList(RFIList.class).addOrUpdateEntity(createdRfi);
            }

        } catch (java.io.IOException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }


    }

    @Then("Created rfi is correct")
    public void rfiCorrect() {
        log.info("Verifying if RFI is correct");
        InformationRequest etalonRfi;
        InformationRequest createdRfi;
        try {
            etalonRfi = context.getFromRunContext("createdRfi", InformationRequest.class);
        } catch (NullReturnException e) {
            log.error("There is no added rfi in run context!");
            throw new AssertionError();
        }
        try {
            createdRfi = context.getEntitiesList(RFIList.class).getLatest();
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }
        checkRFIs(etalonRfi, createdRfi);

    }

    private void checkRFIs(InformationRequest etalon, InformationRequest created) {
        Assert.assertEquals(etalon.getSubject(), created.getSubject());
        Assert.assertEquals(etalon.getDescription(), created.getDescription());
        Assert.assertEquals(etalon.getDistributionList(), created.getDistributionList());
        Assert.assertEquals(etalon.getCreatedDate(), created.getCreatedDate());
        Assert.assertEquals(etalon.getPriority(), created.getPriority());
        Assert.assertEquals(etalon.getDueDate(), created.getDueDate());
        Assert.assertEquals(etalon.getExternalRequestNumber(), created.getExternalRequestNumber());
        Assert.assertEquals(etalon.getGoals(), created.getGoals());
        Assert.assertEquals(etalon.getRequestSource(), created.getRequestSource());
        Assert.assertEquals(etalon.getTaskCategories(), created.getTaskCategories());
        Assert.assertEquals(etalon.getState(), created.getState());


    }

    @When("I update created RFI")
    public void updateCreatedRFI() {
        InformationRequest rfi;
        try {
            rfi = context.getEntitiesList(RFIList.class).getLatest();
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }
        InformationRequest newRfi = rfi.generate();
        Response response = service.addNew(newRfi);
        context.putToRunContext("createdRfi", newRfi);
        readRFIfromJson(response);
    }

    @Then("RFI is updated")
    public void rfiIsUpdated() {
        try {
            InformationRequest createdRequest = context.getFromRunContext("createdRfi", InformationRequest.class);
            InformationRequest updatedRequest = context.getEntitiesList(RFIList.class).getLatest();
            checkRFIs(createdRequest, updatedRequest);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @When("I get details of created RFI")
    public void rfiDetailsView() {
        log.info("Starting step of getting details of RFI...");

        try {
            InformationRequest createdRFI = context.getEntitiesList(RFIList.class).getLatest();
            InformationRequest requestRFIView = service.view(createdRFI.getId());
            log.debug("received RFI from response:"+requestRFIView);
            context.putToRunContext("requestRFIView", requestRFIView);
        } catch (NullReturnException e) {
            log.error("Cannot get RFI from context List");
            throw new AssertionError();
        }

    }

    @Then("RFI details get via details are correct")
    public void RFIDetailsViewCorrect() {
        log.info("Comparing two RFIs...");
        try {
            InformationRequest createdRFI = context.getEntitiesList(RFIList.class).getLatest();
            InformationRequest requestRFIView = context.getFromRunContext("requestRFIView", InformationRequest.class);
            Assert.assertTrue(createdRFI.equals(requestRFIView));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }

    }


}
