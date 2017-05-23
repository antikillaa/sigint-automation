package ingestion.docker.adapters.tdata;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

public class TSMSDockerAdapter extends TDataImage implements IDockerAdapter {

  private static final String[] filemasks = {"tactical*"};

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return getConfig("-l", recordsCount, "-s", recordsCount, "-t", "tactical");
  }

  @Override
  public String[] getFilemasks() {
    return filemasks;
  }

  @Override
  public String getImageName() {
    return getImage();
  }
}
