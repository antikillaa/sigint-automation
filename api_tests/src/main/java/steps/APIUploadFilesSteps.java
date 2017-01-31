package steps;

import app_context.AppContext;
import app_context.RunContext;
import conditions.Conditions;
import conditions.Verify;
import error_reporter.ErrorReporter;
import http.OperationResult;
import http.OperationsResults;
import model.*;
import model.Process;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.UploadFilesService;
import utils.DateHelper;
import utils.Parser;

import java.util.Date;
import java.util.List;

public class APIUploadFilesSteps extends APISteps {

    private Logger log = Logger.getLogger(APIUploadFilesSteps.class);
    private UploadFilesService service = new UploadFilesService();

    @When("I send upload $sType - $rType data file request")
    public void uploadFile(String sType, String rType) {
        G4File file = context.get("g4file", G4File.class);

        //wait for targets index update in ingest service
        int targetUpdateDelayInSec = 61;
        log.info("Wait " + targetUpdateDelayInSec + " sec, while targets index updated in ingest service");
        DateHelper.waitTime(targetUpdateDelayInSec);

        Source source = RunContext.get().get("source", Source.class);
        LoggedUser user = AppContext.get().getLoggedUser();
        OperationResult<FileMeta> uploadResult = service.upload(file, source, user.getId());
        OperationsResults.setResult(uploadResult);
        context.put("meta", uploadResult.getResult());
    }

    @When("uploaded file is processed")
    public void fileIsProcessed() {
        // if file isn't processed
        // wait, update meta and check again
        Date deadline = DateHelper.getDateWithShift(6 * 60);
        FileMeta fileMeta = context.get("meta", FileMeta.class);
        String fileId = fileMeta.getId();
        Boolean fileProcessed = isProcessed(fileId);
        while (!fileProcessed && !isTimeout(deadline)) {
            log.info("Checking is file is processed..");
            DateHelper.waitTime(5);
            fileProcessed = isProcessed(fileId);
        }
        if (!fileProcessed) {
            String errorMessage = "Uploaded file is not processed. Failed by timeout";
            ErrorReporter.reportAndRaiseError(errorMessage);
        }
    }

    private boolean isTimeout(Date deadline) {
        return DateHelper.isTimeout(deadline);
    }

    private boolean isProcessed(String fileId) {
        OperationResult<FileMeta> fileMetaOperationResult = service.meta(fileId);
        FileMeta fileMeta = fileMetaOperationResult.getResult();

        if (fileMeta != null && fileMeta.getMeta() != null) {
            if (fileMeta.getMeta().getProperties().getError() != null) {
                ErrorReporter.reportAndRaiseError(fileMeta.getMeta().getProperties().getError());
            }
            log.info("meta: {'etag': '" + fileMeta.getEtag() + "', isProcessed: " + fileMeta.getMeta().getIsProcessed() + '}');
            return (fileMeta.getEtag().length() > 0 & fileMeta.getMeta().getIsProcessed()); // FileMeta.etag == Process.md5
        } else {
            return false;
        }
    }

    @When("ingest matching complete")
    public void waitForIngestMatchingComplete() {
        // if target matching isn't complete
        // wait, update meta and check again
        Date deadline = DateHelper.getDateWithShift(6 * 60);
        boolean ingestCompleted = isIngestMatchingComplete();
        while (!ingestCompleted && !isTimeout(deadline)) {
            log.info("Uploaded file isn't processed yet..");
            DateHelper.waitTime(5);
            ingestCompleted = isIngestMatchingComplete();
        }
        if (!ingestCompleted) {
            String errorMessage = "Ingest matching was not completed";
            ErrorReporter.reportAndRaiseError(errorMessage);
        }
    }

    private boolean isIngestMatchingComplete() {
        // data range for Upload history filter
        Date minDate = DateHelper.getDateWithShift(-6 * 60);

        // get upload history list
        UploadFilter filter = new UploadFilter().setMinCreatedDate(minDate);
        List<Process> processList = service.search(filter);

        // find uploaded file in history list
        FileMeta fileMeta = context.get("meta", FileMeta.class);
        for (Process process : processList) {
            if (process.getMd5().equals(fileMeta.getEtag())) {
                log.info("Check process status of uploaded file..");
                log.debug(Parser.entityToString(process));
                if (process.isIngestMatchingComplete() && (process.getRecordsCount() != null)) {
                    log.info("File is processed");
                    context.put("process", process);
                    return true;
                }
            }
        }
        return false;
    }

    @Then("search results contain correct counts: total records")
    public void processResultShouldContainCorrectResult() {
        Process process = context.get("process", Process.class);
        GenerationMatrix matrix = context.get("generationMatrix", GenerationMatrix.class);
        Source source = context.get("source", Source.class);

        Integer actualRecordsCount = process.getRecordsCount();
//        Integer actualTargetHits = process.getTargetHitCount();
//        Integer actualTargetMentions = process.getTargetMentionCount();
//
//        Integer expectedTargetHits = matrix.getTotalTargersHit();
//        Integer expectedTargetMentions = matrix.getTotalTargetMention();
        Integer expectedRecordsCount = matrix.getTotalRecords();

        switch (source.getRecordType()) {
            case SMS:
                Verify.shouldBe(Conditions.equals(actualRecordsCount, expectedRecordsCount));
                //Verify.shouldBe(Conditions.equals(actualTargetHits, expectedTargetHits));
                //Verify.shouldBe(Conditions.equals(actualTargetMentions, expectedTargetMentions));
                break;
            case Voice:
                expectedRecordsCount = matrix.getTotalRecordsHit() + matrix.getTotalRandomRecords();

                Verify.shouldBe(Conditions.equals(actualRecordsCount, expectedRecordsCount));
                //Verify.shouldBe(Conditions.equals(actualTargetHits, expectedTargetHits));
                //Verify.shouldBe(Conditions.equals(actualTargetMentions, 0));
                break;
        }
    }

    @When("I send get upload details request")
    public void getUploadDetails() {
        Process process = context.get("process", Process.class);
        UploadDetails uploadDetails = service.details(process.getId()).getResult();
        context.put("uploadDetails", uploadDetails);
    }

    @Then("matching results contain correct Summary: total records")
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

}
