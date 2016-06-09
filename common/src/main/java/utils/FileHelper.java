package utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileHelper {

    private static Logger log = Logger.getLogger(FileHelper.class);

    public static void writeLineToFile(File file, String line) {
        Writer writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(line);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                }
            }
        }
    }

}
