package docker;

import static docker.DockerConfig.getDockerClient;
import static org.junit.Assert.assertNotNull;
import static utils.FileHelper.getFilesByWildcards;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerCreation;
import docker.adapters.IDockerAdapter;
import error_reporter.ErrorReporter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.MediaType;
import model.G4File;
import model.PegasusMediaType;
import org.apache.log4j.Logger;

public class DockerService {

  private static final Logger log = Logger.getLogger(DockerService.class);
  private DockerClient docker;
  private final IDockerAdapter dockerAdapter;

  public DockerService(IDockerAdapter dockerAdapter) {
    this.dockerAdapter = dockerAdapter;
    this.docker = getDockerClient();
  }

  public void generateDataInContainer(String recordsCount) {
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

  public G4File getGeneratedFile() {
    String path = IDockerAdapter.VOLUME_MOUNT_POINT.normalize().toString();
    List<File> files = getFilesByWildcards(path, dockerAdapter.getFilemasks());
    if (files.isEmpty()) {
      ErrorReporter.raiseError(String.format("Can't find files by mask %s in %s",
          Arrays.toString(dockerAdapter.getFilemasks()), path));
    }
    log.info("List of found files: " + files);

    MediaType mediaType;
    String newFilename = renameFile(files.get(0));
    if (newFilename.endsWith(".csv")) {
      mediaType = PegasusMediaType.TEXT_CSV_TYPE;
    } else {
      mediaType = PegasusMediaType.MS_EXCEL_TYPE;
    }

    G4File g4File = new G4File(newFilename);
    g4File.setMediaType(mediaType);
    return g4File;
  }

  private String renameFile(File sourceFile) {
    String epochTime = String.valueOf(System.currentTimeMillis());
    Path source  = sourceFile.toPath();
    Path target = Paths.get(source.getParent().toString(), epochTime + dockerAdapter.getFileSuffix());

    log.info(String.format("Renaming %s to %s", source.getFileName(), target.getFileName()));
    try {
      Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    return target.toString();
  }
}
