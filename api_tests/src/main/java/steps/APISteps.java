package steps;

import model.AppContext;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;

public abstract class APISteps {

    static AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(APISteps.class);

    @Then("I got response code $expected")
    public void checkResponseCode(String expected) {
        log.info("Checking response code");
        Integer actual = context.get("code", Integer.class);
        Assert.assertEquals("Incorrect return codes!", Integer.valueOf(expected), actual);
        
    }

}
