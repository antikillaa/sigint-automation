package steps;

import app_context.AppContext;
import app_context.RunContext;
import conditions.Conditions;
import conditions.Verify;
import data_for_entity.RandomEntities;
import http.OperationResult;
import http.OperationsResults;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;

public abstract class APISteps {

    static RunContext context = RunContext.get();
    static AppContext appContext = AppContext.get();
    static RandomEntities objectInitializer = new RandomEntities();
    Logger log = Logger.getLogger(APISteps.class);

    @Then("I got response code $expected")
    public void checkResponseCode(String expected) {
        log.info("Checking response code");
        Integer actual = context.get("code", Integer.class);
        Assert.assertEquals("Incorrect return codes!", Integer.valueOf(expected), actual);
        
    }
    
    @Then("Request is successful")
    public void checkResultSuccess() {
        log.info("Checking if request is successful");
        OperationResult result = OperationsResults.getResult();
        if (!result.isSuccess()) {
            OperationsResults.throwError(result);
        }
    }
    
    @Then("Request is unsuccessful")
    public void checkResultFail() {
        log.info("Checking if request failed");
        OperationResult result = OperationsResults.getResult();
        if (result.isSuccess()) {
            OperationsResults.throwError(result);
        }
    }

    @Then("Result message should be '$result'")
    public void resultMessageShouldBe(String result) {
        String resultMessage = context.get("resultMessage", String.class);
        Verify.shouldBe(Conditions.equals(resultMessage, result));
    }

}
