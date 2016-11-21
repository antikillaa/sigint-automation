package steps;

import abs.EntityList;
import app_context.AppContext;
import app_context.RunContext;
import conditions.Conditions;
import conditions.Verify;
import model.*;
import model.Process;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.RecordService;
import services.UploadFilesService;
import utils.DateHelper;
import utils.Parser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class APIUploadFilesSteps extends APISteps {

    private Logger log = Logger.getLogger(APIUploadFilesSteps.class);
    private UploadFilesService service = new UploadFilesService();

    @When("I send upload $sType - $rType data file request")
    public void uploadFile(String sType, String rType) {
        G4File file = context.get("g4file", G4File.class);

        log.info("Wait 31 sec, while targets index updated in ingest service");
        //wait 31 sec, for targets index update in ingest service
        try {
            Thread.sleep(31000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            log.trace(e);
        }
        Source source = RunContext.get().get("source", Source.class);
        LoggedUser user = AppContext.get().getLoggedUser();
        int code = service.upload(file, source, user.getId());
        context.put("code", code);

        Date minDate = context.get("uploadDate", Date.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minDate);
        calendar.add(Calendar.MINUTE, 12);
        context.put("timeout", calendar.getTime());
    }

    @When("uploaded file is processed")
    public void fileIsProcessed() {
        // if file isn't processed
        // wait, update meta and check again
        while (!isProcessed()) {
            log.info("Uploaded file isn't processed yet..");
            checkTimeout();
            DateHelper.waitTime(5);
        }
    }

    private void checkTimeout() {
        Date deadline = context.get("timeout", Date.class);
        if (DateHelper.isTimeout(deadline)) {
            String errorMessage = "Uploaded file is not processed. Failed by timeout";
            log.error(errorMessage);
            throw new AssertionError(errorMessage);
        }
    }

    private boolean isProcessed() {
        log.info("Check: uploaded file is processed..");

        // update file meta
        FileMeta fileMeta = context.get("fileMeta", FileMeta.class);
        fileMeta = service.meta(fileMeta.getId());
        context.put("fileMeta", fileMeta);

        return fileMeta.getMeta().getIsProcessed();
        
    }

    @When("ingest matching complete")
    public void waitForIngestMatchingComplete() {
        // if target matching isn't complete
        // wait, update meta and check again
        while (!isIngestMatchingComplete()) {
            log.info("Uploaded file isn't matching yet..");
            checkTimeout();
            DateHelper.waitTime(1);
        }
    }

    private boolean isIngestMatchingComplete() {
        // data range for Upload history filter
        Date minDate = context.get("uploadDate", Date.class);
        Date maxDate = context.get("timeout", Date.class);

        // get upload history list
        UploadFilter filter = new UploadFilter().setMinCreatedDate(minDate).setMaxCreatedDate(maxDate);
        List<Process> processList = service.search(filter);

        // find uploaded file in history list
        FileMeta fileMeta = context.get("fileMeta", FileMeta.class);
        for (Process process : processList) {
            if (process.getMd5().equals(fileMeta.getMeta().getMd5_sigint())) {
                log.info("Check uploaded file processing status..");
                log.debug(Parser.entityToString(process));
                if (process.getState().equals(State.Complete) && process.isIngestMatchingComplete()) {
                    log.info("File is processed");
                    context.put("process", process);
                    return true;
                }
            }
        }
        return false;
    }

    @Then("search results contain correct counts: total records, target hits and mentions")
    public void processResultShouldContainCorrectResult() {
        Process process = context.get("process", Process.class);
        GenerationMatrix matrix = context.get("generationMatrix", GenerationMatrix.class);
        Source source = context.get("source", Source.class);

        switch (source.getRecordType()) {
            case SMS:
                Verify.shouldBe(Conditions.equals(process.getRecordsCount(), matrix.getTotalRecords()));
                Verify.shouldBe(Conditions.equals(process.getTargetHitCount(), matrix.getTotalTargersHit()));
                Verify.shouldBe(Conditions.equals(process.getTargetMentionCount(), matrix.getTotalTargetMention()));
                break;
            case Voice:
                Verify.shouldBe(Conditions.equals(process.getRecordsCount(), matrix.getTotalRecordsHit() + matrix.getTotalRandomRecords()));
                Verify.shouldBe(Conditions.equals(process.getTargetHitCount(), matrix.getTotalTargersHit()));
                Verify.shouldBe(Conditions.equals(process.getTargetMentionCount(), 0));
                break;
        }
        //TODO SMS/Voice counts
    }

    @When("I send get upload details request")
    public void getUploadDetails() {
        Process process = context.get("process", Process.class);
        UploadDetails uploadDetails = service.details(process.getId());
        context.put("uploadDetails", uploadDetails);
    }

    @Then("matching results contain correct Summary: total records, target hits and mentions")
    public void matchingResultShouldContainCorrectSummary() {
        UploadDetails uploadDetails = context.get("uploadDetails", UploadDetails.class);
        context.put("process", uploadDetails.getProcess());
        processResultShouldContainCorrectResult();
    }

    @Then("matching results contain correct Matching results: total hit&Mention records, list of hit/mention targets with hit/mention records counts for each")
    public void matchingResultShouldContainCorrectMatchingResult() {
        UploadDetails uploadDetails = context.get("uploadDetails", UploadDetails.class);
        GenerationMatrix matrix = context.get("generationMatrix", GenerationMatrix.class);

        for (GenerationMatrixRow row : matrix.getRows()) {
            MatchingResult result = null;
            String targetName = row.getTarget().getName();

            // verify target HIT
            if (row.getTotalRecordsHit() > 0) {
                result = uploadDetails.findMatchingResultByTargetNameAndTargetResultType(targetName, TargetResultType.HIT);
                Verify.shouldBe(Conditions.equals(result.getNumRecords(), row.getTotalRecordsHit()));
            }

            // verify target Mention
            Source source = context.get("source", Source.class);
            switch (source.getRecordType()) {
                case SMS:
                    if (row.getTotalRecordsMention() > 0) {
                        result = uploadDetails.findMatchingResultByTargetNameAndTargetResultType(targetName, TargetResultType.MENTION);
                        Verify.shouldBe(Conditions.equals(result.getNumRecords(), row.getTotalRecordsMention()));
                    }
                    break;
            }

            //TODO add verification for target with group
            if (result != null) {
                Verify.shouldBe(Conditions.equals(result.getSearchResultType(), SearchResultType.Target));
            }
        }
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
