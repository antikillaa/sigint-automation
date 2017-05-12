package steps;

import model.entities.Entities;
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
    private static RecordEntityService recordEntityService = new RecordEntityService();

    @When("I send create manual record - $recordType")
    public void createManualRecordVoice(String recordType) {
        Record record;
        switch (recordType) {
            case "SMS":
                record = recordEntityService.createSMSRecord();
                break;
            case "Voice":
                record = recordEntityService.createVoiceRecord();
                break;
            default:
                log.error("Unknown record type");
                throw new AssertionError("Unknown record type passed:" + recordType);
        }
        record.setSourceId(RandomGenerator.getRandomItemFromList(appContext.getDictionary().getSources()).getId());
        service.add(record);
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
