package ingestion.docker.adapters.tdata;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

public class FSMSDockerAdapter extends TDataImage implements IDockerAdapter {

  private static final String[] filemasks = {"F-SMS*"};

  @Override
  public ContainerConfig getContainerConfig(String recordsCount) {
    return getConfig("-l", recordsCount, "-t", "FSMS");
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
