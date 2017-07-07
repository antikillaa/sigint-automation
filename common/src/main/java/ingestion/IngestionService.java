package ingestion;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static utils.FileHelper.getFilesByWildcards;

public class IngestionService {

  private static final Logger log = Logger.getLogger(IngestionService.class);

  public static final Path INGESTION_DIR = Paths.get(System.getProperty("user.dir"), "ingestionData");
  public static final Path IMPORT_DIR = Paths.get(System.getProperty("user.dir"), "importData");
  private final IIngestionDataGenerator ingestionDataGenerator;

  public IngestionService(IIngestionDataGenerator ingestionDataGenerator) {
    this.ingestionDataGenerator = ingestionDataGenerator;
  }

  public IIngestionDataGenerator getGenerator() {
    return ingestionDataGenerator;
  }

  public static void cleanIngestionDir() {
    cleanDirectory(INGESTION_DIR.toString());
  }

  public static void cleanImportDir() {
    cleanDirectory(IMPORT_DIR.toString());
  }

  private static void cleanDirectory(String directory) {
    FileUtils.deleteQuietly(new File(directory));
    try {
      FileUtils.forceMkdir(new File(directory));
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  public static List<File> getWavs() {
    String path = INGESTION_DIR.normalize().toString();
    return getFilesByWildcards(path, new String[]{"*.wav"});
  }
}
