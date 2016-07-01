package steps;

import conditions.Verify;
import model.Record;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.RecordService;
import utils.RandomGenerator;

import static conditions.Conditions.equals;

public class APIRecordSteps extends APISteps {

    private Logger log = Logger.getLogger(APIEtisalatSubscriberDataSteps.class);
    private RecordService service = new RecordService();

    @When("I send create manual record - $recordType")
    public void createManualRecordVoice(String recordType) {
        Record record = new Record();
        record.setSourceId(RandomGenerator.getRandomItemFromList(context.getDictionary().getSources()).getId());
        if (recordType.equals("SMS")) {
            record.generateSMS();
        } else if (recordType.equals("Voice")) {
            record.generateVoice();
        } else {
            log.error("Unknown record type");
            throw new AssertionError("Unknown record type");
        }

        int responseCode = service.add(record);

        context.put("code", responseCode);
        context.put("requestRecord", record);
    }

    @Then("Created record is correct")
    public void createdRecordIsCorrect() {
        Record requestRecord = context.get("requestRecord", Record.class);
        Record createdRecord = context.entities().getRecords().getLatest();

        requestRecord.setState("PROCESSED");
        Verify.shouldBe(equals.elements(createdRecord, requestRecord));
    }
}
