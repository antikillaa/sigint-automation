package ingestion.docker.adapters;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

import static ingestion.docker.adapters.DockerImage.dataGeneratorImage;

public class EtisalatSMS4DockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"*.dat"};
  private static final DockerImage dockerImage = dataGeneratorImage();

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return dockerImage.getConfig("etisalat-sms-format4", "-n", recordsCount, "-d", dockerImage.getDataPath());
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