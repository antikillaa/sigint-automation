package ingestion.docker.adapters;

import static ingestion.docker.adapters.DockerImage.dataGeneratorImage;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

public class SCDRDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"s_cdr*"};
  private static final DockerImage dockerImage = dataGeneratorImage();

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return dockerImage.getConfig("s-cdr", "-n", recordsCount, "-o", dockerImage.getDataPath() + "/s_cdr.csv");
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