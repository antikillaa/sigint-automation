package ingestion.docker.adapters;

import com.spotify.docker.client.messages.ContainerConfig;

public interface IDockerAdapter {

  ContainerConfig getContainerConfig(String recordsCount);

  String[] getFilemasks();

}
