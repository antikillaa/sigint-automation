package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dm on 4/22/16.
 */

public class FileAttachment {

    private String filename;
    private File file;

    public String getFilename() {
        return filename;
    }

    public File getFile() {
        return file;
    }

    public FileAttachment(String filename) {
            createAttachment(filename);
    }

    private void createAttachment(String name){
        File attachFile;
        this.filename = name;
        try {
            attachFile = File.createTempFile(name, ".txt");
            FileOutputStream os = new FileOutputStream(attachFile);
            os.write("test file".getBytes());
            os.close();
            this.file = attachFile;
        } catch (IOException e) {
            throw new AssertionError("Error occurred creating attachment file");
        }
    }
}
