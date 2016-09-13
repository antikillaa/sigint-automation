package steps;

import abs.EntityList;
import model.SSMS;
import model.bulders.SSMSGenerator;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;

public class APISSMSSteps extends APISteps {

    private Logger log = Logger.getLogger(APITargetGroupSteps.class);

    @Given("Generate $count SSMS")
    public void generateSSMS(String count) {
        int numSSMS = Integer.valueOf(count);
        log.info("Generating S-SMS data: " + numSSMS + " records.." );

        EntityList<SSMS> ssms = new SSMSGenerator().produceSSMSListRandomly(numSSMS);

        log.debug("S-SMS data generated");
        context.put("ssmsList", ssms.getEntities());
    }



}
