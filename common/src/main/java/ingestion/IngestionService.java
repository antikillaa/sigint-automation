package ingestion;

import static utils.FileHelper.getFilesByWildcards;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import model.G4File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

public class IngestionService {

  private static final Logger log = Logger.getLogger(IngestionService.class);

  public static final Path INGESTION_DIR = Paths.get(System.getProperty("user.dir"), "ingestionData");
  private final IIngestionDataGenerator ingestionDataGenerator;

  public IngestionService(IIngestionDataGenerator ingestionDataGenerator) {
    this.ingestionDataGenerator = ingestionDataGenerator;
  }

  public IIngestionDataGenerator getGenerator() {
    return ingestionDataGenerator;
  }

  public static void cleanIngestionDir() {
    String ingestionDir = INGESTION_DIR.toString();
    FileUtils.deleteQuietly(new File(ingestionDir));
    try {
      FileUtils.forceMkdir(new File(ingestionDir));
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  public static G4File renameFile(G4File sourceFile, String fileSuffix) {
    String epochTime = String.valueOf(System.currentTimeMillis());
    String fileExtension = FilenameUtils.getExtension(sourceFile.getPath());
    Path source  = sourceFile.toPath();
    Path target = Paths.get(
        source.getParent().toString(),
        String.format("%s_%s.%s", epochTime, fileSuffix, fileExtension)
    );

    log.info(String.format("Renaming %s to %s", source.getFileName(), target.getFileName()));
    try {
      Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    G4File renamedFile = new G4File(target.toString());
    renamedFile.setMediaType(sourceFile.getMediaType());
    return renamedFile;
  }

  public static List<File> getWavs() {
    String path = INGESTION_DIR.normalize().toString();
    return getFilesByWildcards(path, new String[]{"*.wav"});
  }
}
