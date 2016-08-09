package steps;

import errors.NullReturnException;
import model.AppContext;
import model.SSMS;
import model.Target;
import model.bulders.SSMSGenerator;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import services.SSMSService;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class APISSMSSteps extends APISteps {

    private Logger log = Logger.getLogger(APITargetGroupSteps.class);
    private AppContext context = AppContext.getContext();
    private SSMSService service = new SSMSService();

    @Given("Generate $count SSMS")
    public void generateSSMS(String count) throws NullReturnException {
        List<SSMS> ssmsList = new ArrayList<>();
        int numSSMS = Integer.valueOf(count);

        for (int i = 0; i < numSSMS; i++ ) {
            Target target = new Target().generate();
            String targetPhone = RandomGenerator.getRandomItemFromList(new ArrayList<>(target.getPhones()));

            SSMS ssms = new SSMSGenerator()
                    //.toNumber(targetPhone)
                    .generateSSMS();

            ssmsList.add(ssms);
        }

        log.info("ssms count: " + ssmsList.size());
        context.put("ssmsList", ssmsList);
    }

    @When("Upload file with generated SSMS list")
    public void createFileWithGeneratedSSMS() {

        List<SSMS> ssmsList = context.get("ssmsList", List.class);
        int responseCode = service.upload(ssmsList);

    }


}
