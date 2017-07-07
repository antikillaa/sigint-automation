package ingestion;

import java.io.File;
import java.util.List;

public interface IIngestionDataGenerator {

  List<File> generateIngestionFiles(String recordsCount);
}
