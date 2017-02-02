package steps;

import app_context.AppContext;
import app_context.RunContext;
import conditions.Conditions;
import conditions.Verify;
import data_for_entity.RandomEntities;
import http.OperationResult;
import http.OperationsResults;
import model.RequestResult;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;

public abstract class APISteps {

    static RunContext context = RunContext.get();
    static AppContext appContext = AppContext.get();
    static RandomEntities objectInitializer = new RandomEntities();
    static Logger LOGGER = Logger.getLogger(APISteps.class);

    @Then("I got response code $expected")
    public void checkResponseCode(String expected) {
        LOGGER.info("Checking response code");
        Integer actual = context.get("code", Integer.class);
        Assert.assertEquals("Incorrect return codes!", Integer.valueOf(expected), actual);
    }
    
    @Then("Request is successful")
    public void checkResultSuccess() {
        LOGGER.info("Checking if request is successful");
        OperationResult result = OperationsResults.getResult();
        if (!result.isSuccess()) {
            OperationsResults.throwError(result);
        }
    }
    
    @Then("Request is unsuccessful")
    public void checkResultFail() {
        LOGGER.info("Checking if request failed");
        OperationResult result = OperationsResults.getResult();
        if (result.isSuccess()) {
            OperationsResults.throwError(result);
        }
    }

    @Then("Request message should be '$result'")
    public void resultMessageShouldBe(String result) {
        RequestResult requestResult = context.get("requestResult", RequestResult.class);
        Verify.shouldBe(Conditions.equals(requestResult.getMessage(), result));
    }

}
