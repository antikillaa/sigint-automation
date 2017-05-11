package ingestion.docker.adapters;

import static ingestion.docker.DockerConfig.getTDataConfig;

import com.spotify.docker.client.messages.ContainerConfig;

public class FSMSDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"F-SMS*"};

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return getTDataConfig("-l", recordsCount, "-t", "FSMS");
  }

  @Override
  public String[] getFilemasks() {
    return filemasks;
  }

}
