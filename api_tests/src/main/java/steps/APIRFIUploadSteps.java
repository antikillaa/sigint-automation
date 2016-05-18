package steps;

import model.AppContext;
import model.FileAttachment;
import model.InformationRequest;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.RFIService;

import javax.ws.rs.core.Response;


public class APIRFIUploadSteps {

    private static Logger log = Logger.getLogger(APIRFIUploadSteps.class);
    private static RFIService service = new RFIService();
    private AppContext context = AppContext.getContext();

    @When("I send create RFI request $withApproved approved copy and $withCopy original document")
    public void createRFI(String withApproved, String withCopy) {
        log.info("Starting step of creating new RFI");
        InformationRequest RFI = new InformationRequest().generate();
        if (withApproved.toLowerCase().equals("with")){
            RFI.setApprovedCopy(new FileAttachment(("approved")));
        }
        if (withCopy.toLowerCase().equals("with")) {
            RFI.setOriginalDocument(new FileAttachment("original"));
        }
        sendRFI(RFI);

    }

    private void sendRFI(InformationRequest RFI) {
        int response = service.addNew(RFI);
        context.putToRunContext("code", response);
        context.putToRunContext("requestRFI", RFI);
        if (response == 200) {
            rfiCorrect();
        }

    }

    private void rfiCorrect() {
        log.info("Verifying if RFI is correct");
        InformationRequest etalonRFI;
        InformationRequest createdRFI;
        etalonRFI = context.getFromRunContext("requestRFI", InformationRequest.class);;
        createdRFI = context.entities().getRFIs().getLatest();
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
        Assert.assertTrue(created.getInternalRequestNumber() != null);
        if (etalon.getApprovedCopy() != null) {
            Assert.assertEquals(etalon.getApprovedCopy().getFilename(),
                    created.getApprovedCopy().getFilename());
        }
        if (etalon.getOriginalDocument() != null) {
            Assert.assertEquals(etalon.getOriginalDocument().getFilename(),
                    created.getOriginalDocument().getFilename());
        }

    }

    @When("I update created RFI")
    public void updateCreatedRFI() {
        InformationRequest RFI = context.entities().getRFIs().getLatest();
        InformationRequest newRFI = RFI.generate();
        int response = service.addNew(newRFI);
        context.putToRunContext("code", response);
        context.putToRunContext("requestRFI", newRFI);
    }

    @Then("RFI is updated")
    public void rfiIsUpdated() {
        rfiCorrect();

    }

    @When("I get details of created RFI")
    public void rfiDetailsView() {
        log.info("Starting step of getting details of RFI...");
        InformationRequest createdRFI = context.entities().getRFIs().getLatest();
        InformationRequest requestRFIView = service.view(createdRFI.getId());
        log.debug("received RFI from response:"+requestRFIView);
        context.putToRunContext("requestRFIView", requestRFIView);
    }

    @Then("RFI details get via details are correct")
    public void RFIDetailsViewCorrect() {
        log.info("Comparing two RFIs...");
        InformationRequest createdRFI = context.entities().getRFIs().getLatest();
        InformationRequest requestRFIView = context.getFromRunContext("requestRFIView", InformationRequest.class);
        Assert.assertTrue(createdRFI.equals(requestRFIView));


    }

    @When("I create new RFI in status $status")
    public void createRFIInStatus(String status) {
        InformationRequest RFI = new InformationRequest().generate();
        RFI.setState(status.toUpperCase());
        sendRFI(RFI);

    }

    @When("I delete created RFI")
    public void deleteRFI() {
        InformationRequest RFI = context.entities().getRFIs().getLatest();
        int response = service.remove(RFI);
        context.putToRunContext("code",  response);
        }

    @When("I cancel RFI")
    public void cancelRFI() {
        InformationRequest RFI = context.entities().getRFIs().getLatest();
        Response response = service.cancel(RFI);
        context.putToRunContext("code", response.getStatus());
    }

    @When("I take ownership of RFI")
    public void assignRFI() {
        InformationRequest RFI = context.entities().getRFIs().getLatest();
    }

    @Then("RFI has status Assigned and assigned to analyst")
    public void checkAssignedRFI(){

    }
}
