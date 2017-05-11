package utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.Logger;

public class FileHelper {

    private static Logger log = Logger.getLogger(FileHelper.class);

    public static void writeLineToFile(File file, String line) {
        Writer writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(line);
            writer.write("\r\n");
            //writer.write(System.getProperty("line.separator"));
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
    
    public static String writeToFile(final String filename, String context) {
        File file = new File( System.getProperty("user.home")+"/"+filename);
        writeLineToFile(file, context);
        return filename;
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
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(fileName).getFile());
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

    public static List<File> getFilesByWildcards(String directoryName, String[] extensions) {
        File directory = new File(directoryName);
        return (List<File>)
            FileUtils.listFiles(directory, new WildcardFileFilter(extensions), TrueFileFilter.INSTANCE);
    }

}
