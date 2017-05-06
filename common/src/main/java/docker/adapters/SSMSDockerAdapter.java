package docker.adapters;

import static docker.DockerConfig.getTDataConfig;

import com.spotify.docker.client.messages.ContainerConfig;

public class SSMSDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"strategic*"};
  private static final String fileSuffix = "_SSMS.csv";

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return getTDataConfig("-l", recordsCount, "-t", "strategic");
  }

  @Override
  public String[] getFilemasks() {
    return filemasks;
  }

  @Override
  public String getFileSuffix() {
    return fileSuffix;
  }
}
