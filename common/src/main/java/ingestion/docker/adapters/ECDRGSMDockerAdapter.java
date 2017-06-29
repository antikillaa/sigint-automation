package ingestion.docker.adapters;

import static ingestion.docker.adapters.DockerImage.dataGeneratorImage;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

public class ECDRGSMDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"*CDF.FILT"};
  private static final DockerImage dockerImage = dataGeneratorImage();

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return dockerImage.getConfig("etisalat-cdr", "-n", recordsCount, "-d", dockerImage.getDataPath());
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