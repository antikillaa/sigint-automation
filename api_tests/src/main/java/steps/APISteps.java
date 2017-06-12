package steps;

import app_context.AppContext;
import app_context.RunContext;
import conditions.Verify;
import data_for_entity.RandomEntities;
import errors.OperationResultError;
import http.OperationResult;
import http.OperationsResults;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import utils.DateHelper;

import java.io.IOException;

import static conditions.Conditions.isTrue;
import static utils.StringUtils.stripQuotes;

public abstract class APISteps {

    static RunContext context = RunContext.get();
    static AppContext appContext = AppContext.get();
    static RandomEntities objectInitializer = new RandomEntities();
    static Logger log = Logger.getLogger(APISteps.class);

    @Then("I got response code $expected")
    public void checkResponseCode(String expected) {
        OperationResult result = OperationsResults.getResult();
        log.info("Response code: " + result.getCode());
        Assert.assertTrue("Incorrect return code!\n" + result.getMessage(), Integer.valueOf(expected) == result.getCode());
    }

    @Then("Message contains $text")
    public void checkMessageContainsText(String text) {
        OperationResult result = OperationsResults.getResult();
        log.info("Response message: " + result.getMessage());
        Assert.assertTrue(text + " not found", result.getMessage().contains(stripQuotes(text)));
    }

    @Then("Error message is $message")
    public void checkErrorMessage(String message) throws IOException {
        log.info("Verifying error message");
        OperationResult operationResult = OperationsResults.getResult();
        Verify.shouldBe(isTrue.element(operationResult.getMessage().toLowerCase().contains(message.toLowerCase())));
    }
    
    @Then("Request is successful")
    public void checkResultSuccess() {
        OperationResult result = OperationsResults.getResult();
        if (!result.isSuccess()) {
            throw new OperationResultError(result);
        }
    }
    
    @Then("Request is unsuccessful")
    public void checkResultFail() {
        OperationResult result = OperationsResults.getResult();
        if (result.isSuccess()) {
            throw new OperationResultError(result);
        }
    }

    @When("I wait for $count seconds")
    public void waitSeveralseconds(String count) {
        int delay = Integer.valueOf(count);

        log.info("Waiting for " + delay + " seconds...");
        DateHelper.waitTime(delay);
    }
}
