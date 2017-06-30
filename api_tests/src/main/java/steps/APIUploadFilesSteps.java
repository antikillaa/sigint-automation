package steps;

import static org.junit.Assert.assertEquals;

import app_context.AppContext;
import conditions.Conditions;
import conditions.Verify;
import error_reporter.ErrorReporter;
import http.OperationResult;
import ingestion.IngestionService;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import json.JsonConverter;
import model.FileMeta;
import model.G4File;
import model.G4Record;
import model.GenerationMatrix;
import model.GenerationMatrixRow;
import model.LoggedUser;
import model.MatchingResult;
import model.PegasusMediaType;
import model.Process;
import model.Record;
import model.RecordFilter;
import model.SearchResultType;
import model.Source;
import model.TargetResultType;
import model.UploadDetails;
import model.UploadFilter;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import services.RecordService;
import services.UploadFilesService;
import utils.DateHelper;

@SuppressWarnings("unchecked")
public class APIUploadFilesSteps extends APISteps {

    private UploadFilesService service = new UploadFilesService();
    private static final int WAITING_TIME = 6 * 60; // in seconds
    private static final int POLLING_INTERVAL = 20; // in seconds

    @When("I send upload data file request")
    public void uploadFile() {

        G4File file = context.get("g4file", G4File.class);
        Source source = context.get("source", Source.class);
        LoggedUser user = AppContext.get().getLoggedUser();

        if ((file.getName().endsWith(".xls"))) {
            file.setMediaType(PegasusMediaType.MS_EXCEL_TYPE);
        } else {
            file.setMediaType(PegasusMediaType.TEXT_CSV_TYPE);
        }
        String remotePath = service.getRemotePath(file, source);

        OperationResult<FileMeta> uploadResult = service.upload(file, source, user.getId(), remotePath);
        context.put("meta", uploadResult.getEntity());
    }

    @When("I upload audio files")
    public void uploadWavs() {

        List<File> files = IngestionService.getWavs();
        if (files.isEmpty()) {
            ErrorReporter.reportAndRaiseError("Can't find audio files");
        }
        G4File metafile = context.get("g4file", G4File.class);
        Source source = context.get("source", Source.class);
        LoggedUser user = AppContext.get().getLoggedUser();
        String remotePath = service.getRemotePath(metafile, source);

        List<FileMeta> audioMetas = new ArrayList<>();
        for (File file: files) {
            G4File g4file = new G4File(file.getPath());
            g4file.setMediaType(PegasusMediaType.AUDIO);
            OperationResult<FileMeta> uploadResult = service.upload(g4file, source, user.getId(), remotePath);
            if (!uploadResult.isSuccess()) {
                log.error("Can't upload " + file.getName());
            }
            audioMetas.add(uploadResult.getEntity());
        }
        context.put("audioMetas", audioMetas);
        DateHelper.setStartTime();
    }

    @Then("Uploaded audio files are processed")
    public void audioFilesAreProcessed() {
        List<FileMeta> audioMetas = context.get("audioMetas", List.class);

        for (FileMeta audioMeta: audioMetas) {
            context.put("meta", audioMeta);
            fileIsProcessed();
        }
        log.info(String.format("Audio processing time: %d seconds", DateHelper.getDuration()));
    }

    @Then("Uploaded file is processed")
    public void fileIsProcessed() {
        String fileProcesingError = String.format(
            "Uploaded file is not processed during %d seconds", WAITING_TIME);

        // if file isn't processed
        // wait, update meta and check again
        Date deadline = DateHelper.getDateWithShift(WAITING_TIME);
        FileMeta fileMeta = context.get("meta", FileMeta.class);
        String fileId = fileMeta.getId();
        Boolean fileProcessed = isProcessed(fileId);
        while (!fileProcessed && !isTimeout(deadline)) {
            DateHelper.waitTime(POLLING_INTERVAL);
            fileProcessed = isProcessed(fileId);
        }
        if (!fileProcessed) {
            ErrorReporter.reportAndRaiseError(fileProcesingError);
        }
    }

    private boolean isTimeout(Date deadline) {
        return DateHelper.isTimeout(deadline);
    }

    private boolean isProcessed(String fileId) {
        OperationResult<FileMeta> fileMetaOperationResult = service.meta(fileId);
        if (!fileMetaOperationResult.isSuccess()) {
            log.error(fileMetaOperationResult.getMessage());
            ErrorReporter.reportAndRaiseError("Can't get meta details about file");
        }
        FileMeta fileMeta = fileMetaOperationResult.getEntity();

        if (fileMeta != null && fileMeta.getMeta() != null) {
            String error = fileMeta.getMeta().getProperties().getError();
            if (error != null) {
                log.error("Got 'error' in response:\n" + error);
                return true;
            }
            // FileMeta.etag == Process.md5
            log.info(String.format("%s%s meta: {'etag': '%s' , isProcessed: %s}",
                fileMeta.getFile(),
                fileMeta.getExtension(),
                fileMeta.getEtag(),
                fileMeta.getMeta().getIsProcessed()));
            if (fileMeta.getEtag().length() > 0 & fileMeta.getMeta().getIsProcessed()) {
                context.put("meta", fileMeta);
                return true;
            }
        }
        return false;
    }

    @Then("$rCount records are ingested")
    public void waitForIngestMatchingComplete(String rCount) {
        int recordsCount = Integer.valueOf(rCount);
        String ingestionMatchingError = String.format(
            "Ingest matching was not completed during %d seconds", WAITING_TIME);
        DateHelper.setStartTime();
        // if target matching isn't complete
        // wait, update meta and check again
        Date deadline = DateHelper.getDateWithShift(WAITING_TIME);
        boolean ingestCompleted = isIngestMatchingComplete(recordsCount);
        while (!ingestCompleted && !isTimeout(deadline)) {
            log.info("Ingest isn't processed yet");
            DateHelper.waitTime(POLLING_INTERVAL);
            ingestCompleted = isIngestMatchingComplete(recordsCount);
        }
        if (!ingestCompleted) {
            ErrorReporter.reportAndRaiseError(ingestionMatchingError);
        }
        log.info(String.format("Ingestion time: %d seconds", DateHelper.getDuration()));
    }

    private boolean isIngestMatchingComplete(int recordsCount) {
        // data range for Upload history filter
        Date minDate = DateHelper.getDateWithShift(-WAITING_TIME);

        // get upload history list
        UploadFilter filter = new UploadFilter().setMinCreatedDate(minDate);
        List<Process> processList = service.search(filter);

        // find uploaded file in history list
        FileMeta fileMeta = context.get("meta", FileMeta.class);
        for (Process process : processList) {
            if (!process.getMd5().equals(fileMeta.getEtag())) {
                continue;
            }
            log.debug(JsonConverter.toJsonString(process));
            if (process.isIngestMatchingComplete() && (process.getRecordsCount() > 0)) {
                log.info(String.format("%s records are ingested", process.getRecordsCount()));
                if (process.getRecordsCount() == recordsCount) {
                    context.put("process", process);
                    return true;
                }
            }
            return false;
        }
        log.error("Can't find uploaded file " + fileMeta.getName());
        return false;
    }

    @When("I send get upload details request")
    public void getUploadDetails() {
        Process process = context.get("process", Process.class);
        UploadDetails uploadDetails = service.details(process.getId()).getEntity();
        context.put("uploadDetails", uploadDetails);
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
                case "SMS":
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

    @Then("all uploaded records ingested to system with targets Hits & Mentions")
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

        RecordService recordService = new RecordService();
        OperationResult<List<Record>> searchResult = recordService.search(filter);
        List<Record> ingestedRecords = searchResult.getEntity();

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
        assertEquals("totalRecordsHit value is not correct", matrix.getTotalRecordsHit(), totalRecordsHit);
        assertEquals("totalMentionsHit value is not correct", matrix.getTotalRecordsMention(), totalMentionsHit);
    }

}
