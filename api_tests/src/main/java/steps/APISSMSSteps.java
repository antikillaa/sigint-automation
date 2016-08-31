package steps;

import model.AppContext;
import model.SSMS;
import model.Target;
import model.bulders.SSMSGenerator;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;

import java.util.ArrayList;
import java.util.List;

public class APISSMSSteps extends APISteps {

    private Logger log = Logger.getLogger(APITargetGroupSteps.class);
    private AppContext context = AppContext.getContext();

    @Given("Generate $count SSMS")
    public void generateSSMS(String count) {
        List<SSMS> ssmsList = new ArrayList<>();
        int numSSMS = Integer.valueOf(count);

        for (int i = 0; i < numSSMS; i++ ) {
            Target target = new Target().generate();

            SSMS ssms = new SSMSGenerator().setTarget(target).produce();

            ssmsList.add(ssms);
        }

        log.info("ssms count: " + ssmsList.size());
        context.put("ssmsList", ssmsList);
    }



}
