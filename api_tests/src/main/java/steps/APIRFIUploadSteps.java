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
        sendRFI(rfi);

    }

    private void sendRFI(InformationRequest RFI) {
        Response response = service.addNew(RFI);
        readRFIfromJson(response);
        context.putToRunContext("code", response.getStatus());

    }

    private void readRFIfromJson(Response response) {
        String jsonString;
        if (response.getStatus() == 200) {
            jsonString = response.readEntity(String.class);
        } else {
            log.warn("There is no RFI in response with code:" + response.getStatus());
            return;
        }
        InformationRequest createdRFI;
        MapType mapType = JsonCoverter.constructMapTypeToValue(InformationRequest.class);
        try {
            HashMap<String, InformationRequest> map = JsonCoverter.mapper.readValue(jsonString, mapType);
            createdRFI = map.get("result");
            context.getEntitiesList(RFIList.class).addOrUpdateEntity(createdRFI);
            context.putToRunContext("createdRFI", createdRFI);

        } catch (java.io.IOException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }


    }

    @Then("Created rfi is correct")
    public void rfiCorrect() {
        log.info("Verifying if RFI is correct");
        InformationRequest etalonRFI;
        InformationRequest createdRFI;
        etalonRFI = APISteps.getRFIfromContext();
        try {
            createdRFI = context.getEntitiesList(RFIList.class).getLatest();
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }
        checkRFIs(etalonRFI, createdRFI);

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
        InformationRequest RFI;
        RFI = APISteps.getRFIfromContext();
        InformationRequest newRFI = RFI.generate();
        Response response = service.addNew(newRFI);
        context.putToRunContext("createdRFI", newRFI);
        readRFIfromJson(response);
    }

    @Then("RFI is updated")
    public void rfiIsUpdated() {
        try {
            InformationRequest createdRequest = APISteps.getRFIfromContext();
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
        InformationRequest createdRFI = APISteps.getRFIfromContext();
        InformationRequest requestRFIView = service.view(createdRFI.getId());
        log.debug("received RFI from response:"+requestRFIView);
        context.putToRunContext("requestRFIView", requestRFIView);
    }

    @Then("RFI details get via details are correct")
    public void RFIDetailsViewCorrect() {
        log.info("Comparing two RFIs...");
        try {
            InformationRequest createdRFI = APISteps.getRFIfromContext();
            InformationRequest requestRFIView = context.getFromRunContext("requestRFIView", InformationRequest.class);
            Assert.assertTrue(createdRFI.equals(requestRFIView));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }

    }

    @When("I create new RFI in status $status")
    public void createRFIInStatus(String status) {
        InformationRequest RFI = new InformationRequest().generate();
        RFI.setState(status.toUpperCase());
        sendRFI(RFI);

    }

    @When("I delete created RFI")
    public void deleteRFI() {
        InformationRequest RFI = APISteps.getRFIfromContext();
        Response response = service.remove(RFI);
        context.putToRunContext("code", response.getStatus());
        }

    @When("I cancel RFI")
    public void cancelRFI() {
        InformationRequest RFI = APISteps.getRFIfromContext();
        Response response = service.cancel(RFI);
        context.putToRunContext("code", response.getStatus());
    }
}
