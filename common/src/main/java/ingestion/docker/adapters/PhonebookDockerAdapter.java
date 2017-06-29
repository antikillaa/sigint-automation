package ingestion.docker.adapters;

import static ingestion.docker.adapters.DockerImage.dataGeneratorImage;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

public class PhonebookDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"phonebook*"};
  private static final DockerImage dockerImage = dataGeneratorImage();

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return dockerImage.getConfig("phonebook", "-n", recordsCount, "-o", dockerImage.getDataPath() + "/phonebook.csv");
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
