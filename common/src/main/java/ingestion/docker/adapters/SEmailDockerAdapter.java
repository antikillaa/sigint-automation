package ingestion.docker.adapters;

import com.spotify.docker.client.messages.ContainerConfig;
import ingestion.docker.IDockerAdapter;

import static ingestion.docker.adapters.DockerImage.dataManagerImage;

public class SEmailDockerAdapter implements IDockerAdapter {

    private static final String[] filemasks = {"*.eml", "*.xml"};
    private static final DockerImage dockerImage = dataManagerImage();

    @Override
    public ContainerConfig getContainerConfig(String recordsCount) {
        return dockerImage.getConfig("-n", recordsCount, "S-Email_ZDF_XML_Simulation.json");
    }

    @Override
    public String[] getFilemasks() {
        return filemasks;
    }

    @Override
    public String getImageName() {
        return dockerImage.getImage();
    }

}
