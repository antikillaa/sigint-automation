package ingestion.docker.adapters;

import static ingestion.docker.adapters.DockerImage.dataGeneratorImage;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

public class EtisalatSubscriberDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"*tar.gz"};
  private static final DockerImage dockerImage = dataGeneratorImage();

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return dockerImage.getConfig("etisalat-subscriber-format1", "-n", recordsCount, "-d", dockerImage.getDataPath());
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