package steps;

import static org.junit.Assert.assertNotNull;

import docker.DockerService;
import docker.adapters.FSMSDockerAdapter;
import docker.adapters.IDockerAdapter;
import docker.adapters.SSMSDockerAdapter;
import docker.adapters.TSMSDockerAdapter;
import java.io.File;
import java.io.IOException;
import model.G4File;
import model.SourceType;
import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.Given;

public class APIDataGeneratorSteps extends APISteps {

    @Given("$sType - $rType data file with $rCount records was generated")
    public void generateEntityList(String sType, String rType, String rCount) {

        SourceType sourceType = SourceType.valueOf(sType);

        DockerService dockerService = null;
        switch (sourceType) {
            case Strategic:
                dockerService = new DockerService(new SSMSDockerAdapter());
                break;
            case Tactical:
                dockerService = new DockerService(new TSMSDockerAdapter());
                break;
            case F:
                dockerService = new DockerService(new FSMSDockerAdapter());
                break;
            default:
                log.error("Unknown source type: " + sType);
                break;
        }
        assertNotNull("Can't create docker service", dockerService);
        dockerService.generateDataInContainer(rCount);

        G4File file = dockerService.getGeneratedFile();
        context.put("g4file", file);
    }

    @Given("Ingestion directory is clean")
    public void cleanIngestionDir() {
        String ingestionDir = IDockerAdapter.VOLUME_MOUNT_POINT.toString();
        FileUtils.deleteQuietly(new File(ingestionDir));
        try {
            FileUtils.forceMkdir(new File(ingestionDir));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
