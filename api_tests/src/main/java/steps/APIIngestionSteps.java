package steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ingestion.IngestionService;
import ingestion.docker.DockerDataGenerator;
import ingestion.docker.IDockerAdapter;
import ingestion.docker.adapters.ECDRGSMDockerAdapter;
import ingestion.docker.adapters.EtisalatSubscriberDockerAdapter;
import ingestion.docker.adapters.FSMSDockerAdapter;
import ingestion.docker.adapters.SCDRDockerAdapter;
import ingestion.docker.adapters.SCELLDockerAdapter;
import ingestion.docker.adapters.SSMSDockerAdapter;
import ingestion.docker.adapters.SVLRDockerAdapter;
import ingestion.docker.adapters.TSMSDockerAdapter;
import ingestion.docker.adapters.TVoiceDockerAdapter;
import model.G4File;
import model.Process;
import model.RecordType;
import model.Source;
import model.SourceType;
import model.UploadDetails;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

public class APIIngestionSteps extends APISteps {

    @Given("$sType - $rType data file with $rCount records was generated")
    public void generateIngestionFiles(String sType, String rType, String rCount) {

        SourceType sourceType = appContext.getDictionary().getSourceType(sType);
        RecordType recordType = appContext.getDictionary().getRecordType(rType);

        IDockerAdapter dockerAdapter = null;
        switch (sourceType.getType()) {
            case "S":
                switch (recordType.getType()) {
                    case "SMS":
                        dockerAdapter = new SSMSDockerAdapter();
                        break;
                    case "CDR":
                        dockerAdapter = new SCDRDockerAdapter();
                        break;
                    case "VLR":
                        dockerAdapter = new SVLRDockerAdapter();
                        break;
                    case "CELL":
                        dockerAdapter = new SCELLDockerAdapter();
                        break;
                }
                break;
            case "T":
                switch (recordType.getType()) {
                    case "SMS":
                        dockerAdapter = new TSMSDockerAdapter();
                        break;
                    case "Voice":
                        dockerAdapter = new TVoiceDockerAdapter();
                        break;
                }
                break;
            case "F":
                dockerAdapter = new FSMSDockerAdapter();
                break;
            case "ETISALAT":
                switch (recordType.getType()) {
                    case "CDR":
                        dockerAdapter = new ECDRGSMDockerAdapter();
                        break;
                    case "Subscriber":
                        dockerAdapter = new EtisalatSubscriberDockerAdapter();
                        break;
                }
                break;
            default:
                log.error("Unknown source type: " + sType);
                break;
        }
        assertNotNull("Can't assign docker adapter", dockerAdapter);

        IngestionService ingestionService = new IngestionService(new DockerDataGenerator(dockerAdapter));
        IngestionService.cleanIngestionDir();
        G4File file = ingestionService.getGenerator().generateIngestionFile(rCount);

        context.put("g4file", file);
    }

    @Given("$sType - $rType data file is renamed to make filename unique")
    public void renameIngestionFile(String sType, String rType) {
        G4File sourceFile = context.get("g4file", G4File.class);
        G4File renamed = IngestionService.renameFile(sourceFile, String.format("%s_%s", sType, rType));

        context.put("g4file", renamed);
    }

    @Then("Upload details contain $rCount - $rType records")
    public void summaryContainsRecordsWithType(String rCount, String rType) {
        Integer expectedCount = Integer.valueOf(rCount);
        Integer actualCount;

        Source source = context.get("source", Source.class);
        Process process = context.get("uploadDetails", UploadDetails.class).getProcess();
        context.put("process", process);

        assertEquals("Wrong source id detected", source.getId(), process.getSourceId());
        switch (rType.toLowerCase()) {
            case "sms":
                actualCount = process.getSmsCount();
                break;
            case "voice":
                actualCount = process.getVoiceCount();
                break;
            case "cdr":
                actualCount = process.getVoiceCount();
                break;
            default:
                actualCount = process.getRecordsCount();
                break;
        }
        assertEquals(rCount + " count calculation fails", expectedCount, actualCount);
    }
}
