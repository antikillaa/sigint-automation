package steps;

import app_context.AppContext;
import app_context.RunContext;
import data_for_entity.RandomEntities;
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

}
