package steps;

import abs.EntityList;
import conditions.Conditions;
import conditions.Verify;
import model.*;
import model.Process;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.RecordService;
import services.UploadFilesService;
import utils.DateHelper;
import utils.Parser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static conditions.Conditions.isTrue;

public class APIUploadFilesSteps extends APISteps {
    
    private Logger log = Logger.getLogger(APIUploadFilesSteps.class);
    private UploadFilesService service = new UploadFilesService();

    @When("I send upload $sType - $rType data file request")
    public void uploadFile(String sType, String rType) {
        G4File file = context.get("ssmsFile", G4File.class);

        int code = service.upload(file);
        context.put("code", code);

        Calendar timeout = Calendar.getInstance();
        timeout.add(Calendar.MINUTE, 3);
        DateHelper.setTimeout(timeout.getTime());
        //{"status":409,"error":"Conflict","message":"File: /S/S-SMS/2016/08/22/testssms.csv - already exists","reason":"FileAlreadyExistsException"}
    }

    @When("uploaded file is processed")
    public void fileIsProcessed() {
        log.info("Check: uploaded file is processed..");

        if (DateHelper.isTimeout()) {
            throw new AssertionError("Terminate by timeout!");
        }

        // get md5 of uploaded file from fileMeta
        FileMeta fileMeta = context.get("fileMeta", FileMeta.class);
        String md5 = fileMeta.getMeta().getMd5_sigint();
        if (md5 == null) {
            fileMeta = service.meta(fileMeta.getIdentifier());
            context.put("fileMeta", fileMeta);
            md5 = fileMeta.getMeta().getMd5_sigint();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        Date minDate = calendar.getTime();
        calendar.add(Calendar.MINUTE, 15);
        Date maxDate = calendar.getTime();

        // get upload history list
        UploadFilter filter = new UploadFilter().setMinCreatedDate(minDate).setMaxCreatedDate(maxDate);
        List<Process> processList = service.search(filter);

        // find uploaded file in history list
        if (!processList.isEmpty()) {
            for (Process process : processList) {
                if (process.getMd5().equals(md5)) {
                    log.info("File start processing");
                    log.debug(Parser.entityToString(process));
                    if (process.getState().equals(State.Complete)) {
                        log.info("File is processed");
                        context.put("process", process);
                        return;
                    }
                }
            }
        }
        // file not processed yet, wait and try again
        try {
            log.info("Uploaded file not processed yet..");
            Thread.sleep(5000);
            fileIsProcessed();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    @When("ingest matching complete")
    public void ingestMatchingComplete() {
        Process process = context.get("process", Process.class);
        Verify.shouldBe(isTrue.element(process.isIngestMatchingComplete()));
    }

    @Then("search results contain $numRecords total records with: $hitCount at least hits number and $mentionCount at least mention number")
    public void processResultShouldContainCorrectResult(String numRecords, String hitCount, String mentionCount) {
        Integer totalRecords = Integer.valueOf(numRecords);
        Integer totalHit = Integer.valueOf(hitCount);
        Integer totalMention = Integer.valueOf(mentionCount);

        Process process = context.get("process", Process.class);

        Verify.shouldBe(Conditions.equals(process.getRecordsCount(), totalRecords));
        Verify.shouldBe(isTrue.element(process.getTargetHitCount() >= totalHit));
        //TODO mention count
    }

    @When("I send get upload details request")
    public void getUploadDetails() {
        Process process = context.get("process", Process.class);
        UploadDetails uploadDetails = service.details(process.getId());
        context.put("uploadDetails", uploadDetails);
    }

    @Then("matching results contain $targetHitCount hits number and $hitCount matched records and have $targetResultType target result type")
    public void matchingResultShouldContainCorrectResult(String targetHitCount, String hitCount, String targetResultType) {
        Integer totalTargets = Integer.valueOf(targetHitCount);
        Integer hitNum = Integer.valueOf(hitCount);
        TargetResultType resultType = TargetResultType.valueOf(targetResultType);

        UploadDetails uploadDetails = context.get("uploadDetails", UploadDetails.class);

        // hit count
        Integer hits = 0;
        Integer targets = 0;
        List<MatchingResult> matchingResults = uploadDetails.getSearchResults();
        for (MatchingResult result : matchingResults) {
            if (result.getSearchResultType().equals(SearchResultType.Target)) {
                hits += result.getNumRecords();
                Assert.assertEquals(resultType, result.getTargetResultType());
            }
        }

        //Assert.assertTrue(targets >= totalTargets); TODO
        Verify.shouldBe(Conditions.equals(hitNum, hits));
    }

    @When("I send get an uploaded records details request")
    public void getAnUploadedRecordsDetails() {
        Process process = context.get("process", Process.class);
        RecordService recordService = new RecordService();
        RecordFilter filter = new RecordFilter();

        filter.getProcessIds().add(process.getId());
        EntityList<Record> records = recordService.list(filter);
    }



}
