package steps;

import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import model.Record;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.RecordEntityService;
import services.RecordService;
import utils.RandomGenerator;

public class APIRecordSteps extends APISteps {

    private Logger log = Logger.getLogger(APIRecordSteps.class);
    private RecordService service = new RecordService();
    static RecordEntityService recordEntityService = new RecordEntityService();

    @When("I send create manual record - $recordType")
    public void createManualRecordVoice(String recordType) {
        Record record;
        if (recordType.equals("SMS")) {
            record = recordEntityService.createSMSRecord();
        } else if (recordType.equals("Voice")) {
            record = recordEntityService.createVoiceRecord();
        } else {
            log.error("Unknown record type");
            throw new AssertionError("Unknown record type passed:" + recordType);
        }
        record.setSourceId(RandomGenerator.getRandomItemFromList(appContext.getDictionary().getSources()).getId());
        int responseCode = service.add(record);

        context.put("code", responseCode);
        context.put("requestRecord", record);
    }

    @Then("Created record is correct")
    public void createdRecordIsCorrect() {
        Record requestRecord = context.get("requestRecord", Record.class);
        Record createdRecord = Entities.getRecords().getLatest();

        requestRecord.setState("PROCESSED");
        Verify.shouldBe(Conditions.equals(createdRecord, requestRecord));
    }
}
