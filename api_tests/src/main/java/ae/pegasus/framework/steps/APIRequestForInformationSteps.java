package ae.pegasus.framework.steps;

import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.RequestForInformation;
import ae.pegasus.framework.model.Result;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.services.RequestForInformationService;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.assertEquals;

public class APIRequestForInformationSteps extends APISteps {

    public static RequestForInformationService requestForInformationService = new RequestForInformationService();


    @When("I send generate RFI number request")
    public void sendGenerateRFINumberRequest() {
        Result rfiNo = new Result();
        OperationResult<Result> operationResult = requestForInformationService.generateNumber();
        rfiNo.setResult(operationResult.getEntity().getResult());
        context.put("rfiNo", rfiNo);
    }

    @When("I send create a RFI request")
    public void sendCreateRFIRequest() {
        RequestForInformation requestForInformation = new RequestForInformation();
        Result rfitNo = context.get("rfiNo", Result.class);
        requestForInformationService.buildRFI(requestForInformation, rfitNo);
        context.put("requestForInformation", requestForInformation);
        requestForInformationService.add(requestForInformation);
    }

    @When("I send view a RFI request")
    public void sendViewRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        String id = lastRFI.getId();
        requestForInformationService.view(id);
    }

    @When("I send delete a RFI request")
    public void sendDeleteRFIRequest() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        requestForInformationService.remove(lastRFI);
    }

    @Then("RFI is created")
    public void rfiIsCreated() {
        RequestForInformation lastRFI = Entities.getRequestForInformations().getLatest();
        RequestForInformation createdRFI = context.get("requestForInformation", RequestForInformation.class);
        assertEquals(lastRFI.getClassification(), createdRFI.getClassification());
        assertEquals(lastRFI.getManualNo(), createdRFI.getManualNo());
        assertEquals(lastRFI.getRequired(), createdRFI.getRequired());
        assertEquals(lastRFI.getObjectType(), "RFI");
    }
}
