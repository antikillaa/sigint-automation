package ingestion.docker.adapters;

import static ingestion.docker.adapters.DockerImage.tDataImage;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

public class TVoiceDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"tactical*"};
  private static final DockerImage dockerImage = tDataImage();

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return dockerImage.getConfig("-l", recordsCount, "-v", recordsCount, "-t", "tactical");
  }

  @Override
  public String[] getFilemasks() {
    return filemasks;
  }

  @Override
  public String getImageName() {
    return dockerImage.getImage();
  }

}
