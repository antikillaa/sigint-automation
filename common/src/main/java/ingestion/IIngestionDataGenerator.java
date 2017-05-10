package ingestion;

import model.G4File;

public interface IIngestionDataGenerator {

  G4File generateIngestionFile(String recordsCount);
}
