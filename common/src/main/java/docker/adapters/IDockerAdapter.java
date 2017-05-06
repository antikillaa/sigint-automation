package docker.adapters;

import com.spotify.docker.client.messages.ContainerConfig;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.log4j.Logger;

public interface IDockerAdapter {

  Logger log = Logger.getLogger(IDockerAdapter.class);
  Path VOLUME_MOUNT_POINT = Paths.get(System.getProperty("user.dir"), "ingestionData");

  ContainerConfig getContainerConfig(String recordsCount);

  String[] getFilemasks();

  String getFileSuffix();
}
