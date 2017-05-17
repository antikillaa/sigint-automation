package ingestion.docker;

import static ingestion.IngestionService.INGESTION_DIR;
import static org.junit.Assert.assertNotNull;
import static utils.FileHelper.getFilesByWildcards;

import app_context.properties.DockerProperties;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DefaultDockerClient.Builder;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.RegistryAuth;
import error_reporter.ErrorReporter;
import ingestion.IIngestionDataGenerator;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import model.G4File;
import org.apache.log4j.Logger;

public class DockerDataGenerator implements IIngestionDataGenerator {

  private static final Logger log = Logger.getLogger(DockerDataGenerator.class);
  private static DockerProperties props =  new DockerProperties();

  private DockerClient docker;
  private final IDockerAdapter dockerAdapter;

  public DockerDataGenerator(IDockerAdapter dockerAdapter) {
    this.dockerAdapter = dockerAdapter;
    this.docker = getDockerClient();
  }

  private static DockerClient getDockerClient() {
    Builder dockerBuilder = DefaultDockerClient.builder();
    final RegistryAuth registryAuth = RegistryAuth.builder()
        .username(props.getRegistryLogin())
        .password(props.getRegistryPassword())
        .serverAddress(props.getRegistryAddress())
        .build();
    dockerBuilder.registryAuth(registryAuth);

    dockerBuilder.uri(props.getClientURI());

    if (props.getClientURI().startsWith("https")) {
      try {
        dockerBuilder.dockerCertificates(new DockerCertificates(Paths.get(props.getCertPath())));
      } catch (DockerCertificateException e) {
        log.error(e.getMessage());
      }
    }
    return dockerBuilder.build();
  }

  private void generateDataInContainer(String recordsCount) {
    assertNotNull("Can't init container config", dockerAdapter.getContainerConfig(recordsCount));
    try {
      log.info("Pulling image");
      docker.pull(dockerAdapter.getImageName());
      log.info("Create container");
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
    } else if (files.size() > 1) {
      log.info("List of found files: " + files);
    }
    log.info(String.format("Working with %s file", files.get(0).getPath()));

    return new G4File(files.get(0).getPath());
  }
}
