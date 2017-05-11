package ingestion;

import static ingestion.docker.DockerConfig.getDockerClient;
import static ingestion.IngestionService.INGESTION_DIR;
import static org.junit.Assert.assertNotNull;
import static utils.FileHelper.getFilesByWildcards;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerCreation;
import ingestion.docker.adapters.IDockerAdapter;
import error_reporter.ErrorReporter;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.MediaType;
import model.G4File;
import model.PegasusMediaType;
import org.apache.log4j.Logger;

public class DockerDataGenerator implements IIngestionDataGenerator {

  private static final Logger log = Logger.getLogger(DockerDataGenerator.class);
  private DockerClient docker;
  private final IDockerAdapter dockerAdapter;

  public DockerDataGenerator(IDockerAdapter dockerAdapter) {
    this.dockerAdapter = dockerAdapter;
    this.docker = getDockerClient();
  }

  private void generateDataInContainer(String recordsCount) {
    assertNotNull("Can't init container config", dockerAdapter.getContainerConfig(recordsCount));
    try {
      final ContainerCreation creation = docker.createContainer(dockerAdapter.getContainerConfig(recordsCount));
      final String id = creation.id();
      // Start container, wait for execution and delete after that
      docker.startContainer(id);
      docker.waitContainer(id);
      docker.removeContainer(id);
      docker.close();

    } catch (DockerException | InterruptedException e) {
      log.error(e.getMessage());
    }
  }

  @Override
  public G4File generateIngestionFile(String recordsCount) {

    generateDataInContainer(recordsCount);

    String path = INGESTION_DIR.normalize().toString();
    List<File> files = getFilesByWildcards(path, dockerAdapter.getFilemasks());
    if (files.isEmpty()) {
      ErrorReporter.raiseError(String.format("Can't find files by mask %s in %s",
          Arrays.toString(dockerAdapter.getFilemasks()), path));
    }
    log.info("List of found files: " + files);

    MediaType mediaType;
    String newFilename = files.get(0).getPath();
    if (newFilename.endsWith(".csv")) {
      mediaType = PegasusMediaType.TEXT_CSV_TYPE;
    } else {
      mediaType = PegasusMediaType.MS_EXCEL_TYPE;
    }

    G4File g4File = new G4File(newFilename);
    g4File.setMediaType(mediaType);
    return g4File;
  }
}
