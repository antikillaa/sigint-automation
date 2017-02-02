package steps;

import abs.EntityList;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import http.OperationResult;
import http.OperationsResults;
import model.*;
import model.Process;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.RecordEntityService;
import services.RecordService;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        OperationResult<Record> operationResult = service.add(record);
        OperationsResults.setResult(operationResult);
        context.put("requestRecord", record);
    }

    @Then("Created record is correct")
    public void createdRecordIsCorrect() {
        Record requestRecord = context.get("requestRecord", Record.class);
        Record createdRecord = Entities.getRecords().getLatest();

        requestRecord.setState("PROCESSED");
        Verify.shouldBe(Conditions.equals(createdRecord, requestRecord));
    }

    @Then("all uploaded records ingested to system")
    public void allRecordsIngestedToSystem() {
        List<G4Record> uploadedRecords = context.get("entitiesList", List.class);
        GenerationMatrix matrix = context.get("generationMatrix", GenerationMatrix.class);
        Process process = context.get("process", Process.class);

        int totalRecordsHit = 0;
        int totalMentionsHit = 0;

        RecordFilter filter = new RecordFilter();
        ArrayList<String> ids = new ArrayList<>();
        ids.add(process.getId());
        filter.setProcessIds(ids);

        OperationResult<EntityList<Record>> searchResult = service.list(filter);
        List<Record> ingestedRecords = searchResult.getResult().getEntities();

        // for each uploaded records
        for (G4Record g4Record : uploadedRecords) {
            boolean isIngested = false;
            // for each founded records
            for (Record record : ingestedRecords) {
                // check uploaded record isFound
                if (Objects.equals(record.getFromNumber(), g4Record.getFromNumber())) {
                    isIngested = true;

                    // for each ingested record check and calculate target Hits & Mention
                    if (record.getTargetHitIds() != null && record.getTargetHitIds().size() > 0) {
                        totalRecordsHit++;
                    }
                    if (record.getTargetMentionIds() != null && record.getTargetMentionIds().size() > 0) {
                        totalMentionsHit++;
                    }
                    break;
                }
            }
            Assert.assertTrue("Uploaded record does not found after ingest," +
                    " fromNumber=" + g4Record.getFromNumber(), isIngested);
        }
        Assert.assertEquals("totalRecordsHit value is not correct", matrix.getTotalRecordsHit(), totalRecordsHit);
        Assert.assertEquals("totalMentionsHit value is not correct", matrix.getTotalRecordsMention(), totalMentionsHit);
    }
}
