package ingestion.docker.adapters;

import app_context.properties.DockerProperties;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.HostConfig;

import static ingestion.IngestionService.INGESTION_DIR;

class DockerImage {

  private static DockerProperties props = new DockerProperties();

  private String image;
  private String dataPath;

  private DockerImage(String image, String dataPath) {
    this.image = image;
    this.dataPath = dataPath;
  }

  String getImage() {
    return image;
  }

  String getDataPath() {
    return dataPath;
  }

  ContainerConfig getConfig(String... cmd) {

    final HostConfig hostConfig = HostConfig.builder()
        .appendBinds(String.format("%s:%s", INGESTION_DIR, dataPath))
        .build();

    ContainerConfig.Builder containerBuilder = ContainerConfig.builder();
    containerBuilder
        .hostConfig(hostConfig)
        .image(getImage())
        .cmd(cmd);

    if (!props.getUser().isEmpty()) {
      containerBuilder.user(props.getUser());
    }
    return containerBuilder.build();
  }

  static DockerImage dataGeneratorImage() {
    final String IMAGE = "docker-registry.pegasus.ae/data-generator:1.02";
    final String DATA_PATH = "/src/test_data";

    return new DockerImage(IMAGE, DATA_PATH);
  }
}
