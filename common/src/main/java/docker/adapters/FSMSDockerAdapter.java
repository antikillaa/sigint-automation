package docker.adapters;

import static docker.DockerConfig.getTDataConfig;

import com.spotify.docker.client.messages.ContainerConfig;

public class FSMSDockerAdapter implements IDockerAdapter {

  private static final String[] filemasks = {"F-SMS*"};
  private static final String fileSuffix = "_FSMS.csv";

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return getTDataConfig("-l", recordsCount, "-t", "FSMS");
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
