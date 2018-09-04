package ae.pegasus.framework.steps;


import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.RequestForApprove;
import ae.pegasus.framework.model.Result;
import ae.pegasus.framework.model.SearchRecord;
import ae.pegasus.framework.services.RequestForApproveService;
import org.jbehave.core.annotations.When;

import java.util.List;

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
    public void sendRFARequest() {
        RequestForApprove requestForApprove = new RequestForApprove();
        Result rfaNo = context.get("rfaNo", Result.class);
        List<SearchRecord> entities = context.get("searchEntities", List.class);
        serviceRequestForApprove.buildRFA(requestForApprove, rfaNo, entities);
        context.put("requestForApprove", requestForApprove);
        serviceRequestForApprove.add(requestForApprove);
    }


}
