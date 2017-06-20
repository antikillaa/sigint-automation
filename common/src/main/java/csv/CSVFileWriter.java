package csv;

import ingestion.IngestionService;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import model.G4File;
import model.Whitelist;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class CSVFileWriter {

  private String filename;
  private boolean withHeader;

  public CSVFileWriter(String filename, boolean withHeader) {
    this.filename = filename;
    this.withHeader = withHeader;
  }

  private static CellProcessor[] getWhitelistProcessors() {
    return new CellProcessor[] {
        new NotNull(), // identifier
        new NotNull(), // type
        new NotNull(), // description
    };
  }

  public G4File writeEntitiesToCsv(List<Whitelist> whitelists) throws IOException {

    String outputFile = Paths.get(IngestionService.IMPORT_DIR.toString(), filename).toString();

    ICsvBeanWriter beanWriter = null;
    try {
      beanWriter = new CsvBeanWriter(new FileWriter(outputFile), CsvPreference.STANDARD_PREFERENCE);

      // the header elements are used to map the bean values to each column (names must match)
      final String[] header = new String[] { "identifier", "type", "description" };
      final CellProcessor[] processors = getWhitelistProcessors();

      // write the header
      if (withHeader) {
        beanWriter.writeHeader(header);
      }

      // write the beans
      for (final Whitelist whitelist : whitelists) {
        beanWriter.write(whitelist, header, processors);
      }

    }
    finally {
      if( beanWriter != null ) {
        beanWriter.close();
      }
    }
    return new G4File(outputFile);
  }
}
