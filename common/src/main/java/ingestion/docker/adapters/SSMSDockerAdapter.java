package ingestion.docker.adapters;

import static ingestion.docker.DockerConfig.getTDataConfig;

import com.spotify.docker.client.messages.ContainerConfig;

public class SSMSDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"strategic*"};

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return getTDataConfig("-l", recordsCount, "-t", "strategic");
  }

  @Override
  public String[] getFilemasks() {
    return filemasks;
  }

}
