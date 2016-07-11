package reporter;

import com.google.common.io.Files;
import docker.Docker;
import docker.model.DockerContainer;
import docker.model.DockerContainers;
import errors.NullReturnException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by dm on 4/12/16.
 */
public class ServicesLogs {

    static Logger log = Logger.getRootLogger();
    private Docker docker = new Docker();


    public File collectLogs() throws IOException {
        log.info("Collecting logs of G4 services...");
        File outputDir;
        DockerContainer dockerContainer;
        outputDir = Files.createTempDir();
        for (DockerContainers container: DockerContainers.values()) {
            try {
                dockerContainer = docker.getContainerByRole(container.toString());
                docker.putLogFileToDir(outputDir, dockerContainer);
            } catch (NullReturnException e) {
                log.warn("Container with name:"+container + " wasn't found!");
            }
        }
        return outputDir;
    }

    public File getOutputLog() throws IOException {
        log.info("Getting output log");
        File outputDir = Files.createTempDir();
        File file = File.createTempFile("log", ".txt", outputDir);
        InputStream is = new FileInputStream("log4j/log.out");
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(is.read());
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(stream);
        return outputDir;

    }
}


class Zipper {

    ByteArrayOutputStream  zip(File directory) throws IOException {
        ServicesLogs.log.debug("Archiving files from directory:"+directory);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
         ZipOutputStream zipOut = new ZipOutputStream(output);
         zipOut.setLevel(Deflater.DEFAULT_COMPRESSION);
        for (File file: directory.listFiles()) {
            addToZip(directory, file, zipOut);

        }
         zipOut.close();
         return output;
    }

    private void addToZip(File directory, File file, ZipOutputStream stream) throws IOException {
        ServicesLogs.log.debug("Adding file to zip output stream");
        FileInputStream in = new FileInputStream(file);
        String zipFilePath = file.getCanonicalPath().substring(directory.getCanonicalPath().length() + 1,
                file.getCanonicalPath().length());
        ZipEntry zipEntry = new ZipEntry(zipFilePath);
        stream.putNextEntry(zipEntry);
        byte[] buffer = new byte[8 * 1024];
        int length;
        while ((length = in.read(buffer)) >= 0) {
            stream.write(buffer, 0, length);
        }
        stream.closeEntry();
        in.close();

    }

        }