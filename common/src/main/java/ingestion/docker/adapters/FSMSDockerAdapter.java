package ingestion.docker.adapters;

import static ingestion.docker.adapters.DockerImage.tDataImage;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

public class FSMSDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"F-SMS*"};
  private static final DockerImage dockerImage = tDataImage();

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return dockerImage.getConfig("-l", recordsCount, "-t", "FSMS");
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
