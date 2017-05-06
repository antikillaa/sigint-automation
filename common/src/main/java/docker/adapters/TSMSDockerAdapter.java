package docker.adapters;

import static docker.DockerConfig.getTDataConfig;

import com.spotify.docker.client.messages.ContainerConfig;

public class TSMSDockerAdapter implements IDockerAdapter  {

  private static final String[] filemasks = {"tactical*"};
  private static final String fileSuffix = "_TSMS.xls";

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return getTDataConfig("-l", recordsCount, "-s", recordsCount, "-t", "tactical");
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
