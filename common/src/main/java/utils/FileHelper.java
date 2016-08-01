package utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;

public class FileHelper {

    private static Logger log = Logger.getLogger(FileHelper.class);

    public static void writeLineToFile(File file, String line) {
        Writer writer = null;
        try {
            writer = new FileWriter(file, true);
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
    
    public static void writeToFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[8 * 1024];
        int length;
        try {
            while ((length = in.read(buffer)) >= 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            log.error("Exception occurred writing to output stream");
            throw  e;
        }
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
    }

    public static String readTxtFile(String fileName) {
        ClassLoader classLoader = FileHelper.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try {
            FileReader reader = new FileReader(file);
            char[] buffer = new char[(int)file.length()];
            reader.read(buffer);
            return new String(buffer);
        }
        catch(IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
