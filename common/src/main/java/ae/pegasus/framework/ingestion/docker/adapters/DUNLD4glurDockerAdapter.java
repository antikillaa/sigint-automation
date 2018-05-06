package ae.pegasus.framework.ingestion.docker.adapters;

import ae.pegasus.framework.ingestion.docker.IDockerAdapter;
import com.spotify.docker.client.messages.ContainerConfig;

import static ae.pegasus.framework.ingestion.docker.adapters.DockerImage.dataManagerImage;

public class DUNLD4glurDockerAdapter implements IDockerAdapter {

    private static final String[] filemasks = {"*.csv"};
    private static final DockerImage dockerImage = dataManagerImage();

    @Override
    public ContainerConfig getContainerConfig(String recordsCount) {
        return dockerImage.getConfig("-n", recordsCount, "origin/D/du-nld-4glur-simulation.json");
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
