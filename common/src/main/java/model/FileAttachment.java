package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dm on 4/22/16.
 */
public class FileAttachment {


    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public FileAttachment(String name) {

        try {
            createAttachment(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String name;
    private File file;

    private void createAttachment(String name) throws IOException {
        File attachFile;
        this.name = name;
        attachFile = File.createTempFile(name, ".txt");
        FileOutputStream os = new FileOutputStream(attachFile);
        os.write("test file".getBytes());
        os.close();
        this.file = attachFile;


    }
}
