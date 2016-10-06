package steps;

import abs.EntityList;
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

import static conditions.Conditions.isTrue;

public class APIUploadFilesSteps extends APISteps {
    
    private Logger log = Logger.getLogger(APIUploadFilesSteps.class);
    private UploadFilesService service = new UploadFilesService();

    @When("I send upload $sType - $rType data file request")
    public void uploadFile(String sType, String rType) {
        G4File file = context.get("g4file", G4File.class);

        int code = service.upload(file);
        context.put("code", code);

        Calendar timeout = Calendar.getInstance();
        timeout.add(Calendar.MINUTE, 11);
        DateHelper.setTimeout(timeout.getTime());
    }

    @When("uploaded file is processed")
    public void fileIsProcessed() {
        log.info("Check: uploaded file is processed..");

        if (DateHelper.isTimeout()) {
            String errorMessage = "Uploaded file is not processed. Failed by timeout";
            log.error(errorMessage);
            throw new AssertionError(errorMessage);
        }

        // get md5 of uploaded file from fileMeta
        FileMeta fileMeta = context.get("fileMeta", FileMeta.class);
        String md5 = fileMeta.getMeta().getMd5_sigint();
        if (md5 == null) {
            fileMeta = service.meta(fileMeta.getIdentifier());
            context.put("fileMeta", fileMeta);
            md5 = fileMeta.getMeta().getMd5_sigint();
        }

        // data range for Upload history filter
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
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
