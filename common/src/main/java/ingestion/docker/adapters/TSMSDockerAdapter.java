package ingestion.docker.adapters;

import static ingestion.docker.DockerConfig.getTDataConfig;

import com.spotify.docker.client.messages.ContainerConfig;

public class TSMSDockerAdapter implements IDockerAdapter  {

  private static final String[] filemasks = {"tactical*"};

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return getTDataConfig("-l", recordsCount, "-s", recordsCount, "-t", "tactical");
  }

  @Override
  public String[] getFilemasks() {
    return filemasks;
  }

}
