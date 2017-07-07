package ingestion.docker.adapters;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

import static ingestion.docker.adapters.DockerImage.dataGeneratorImage;

public class SVoiceDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"*.md"};
  private static final DockerImage dockerImage = dataGeneratorImage();

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return dockerImage.getConfig("s-voice", "-n", recordsCount, "-d", dockerImage.getDataPath());
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
