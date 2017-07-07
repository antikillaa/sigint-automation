package steps;

import ingestion.IngestionService;
import ingestion.docker.DockerDataGenerator;
import ingestion.docker.IDockerAdapter;
import ingestion.docker.adapters.*;
import model.*;
import model.Process;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class APIIngestionSteps extends APISteps {

    @Given("$sType - $rType data file with $rCount records was generated")
    public void generateIngestionFiles(String sType, String rType, String rCount) {

        IDockerAdapter dockerAdapter = null;
        switch (sType) {
            case "S":
                switch (rType) {
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
                    case "Voice":
                        dockerAdapter = new SVoiceDockerAdapter();
                        break;
                    case "Fax":
                        dockerAdapter = new SFaxDockerAdapter();
                        break;
                }
                break;
            case "T":
                switch (rType) {
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
            case "Phonebook":
                dockerAdapter = new PhonebookDockerAdapter();
                break;
            case "ETISALAT":
                switch (rType) {
                    case "CDR":
                        dockerAdapter = new EtisalatCDRDockerAdapter();
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
        List<File> files = ingestionService.getGenerator().generateIngestionFiles(rCount);

        context.put("g4files", files);
    }

    @Given("I create remote path for ingestion")
    public void createRemotePath() {
        Source source = context.get("source", Source.class);

        String path = "/" + source.getType()
                + "/" + source.getName()
                + new SimpleDateFormat("/yyyy/MM/dd/HH_mm_ss.SSS/").format(new Date());

        log.info("Remote path: " + path);
        context.put("remotePath", path);
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
