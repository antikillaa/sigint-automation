package csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import model.AbstractEntity;
import model.DesignationMapping;
import model.G4File;
import org.supercsv.cellprocessor.FmtBool;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class CSVDesignationMappingWriter extends CSVFileWriter<DesignationMapping> {

  public CSVDesignationMappingWriter(String filename, boolean withHeader) {
    super(filename, withHeader);
  }

  private static CellProcessor[] getDesignationMappingProcessors() {
    return new CellProcessor[] {
        new DesignationProcessor(), // designation
        new NotNull(), // identifier
        new NotNull(), // type
        new FmtBool("Yes", "No"), // spam
    };
  }

  @Override
  public G4File writeEntitiesToCsv(List<DesignationMapping> entities) throws IOException {

    String outputFile = getOutputFile();

    ICsvBeanWriter beanWriter = null;
    try {
      beanWriter = new CsvBeanWriter(new FileWriter(outputFile), CsvPreference.STANDARD_PREFERENCE);

      // the header elements are used to map the bean values to each column (names must match)
      final String[] header = new String[] { "designations", "identifier", "type", "spam" };
      final CellProcessor[] processors = getDesignationMappingProcessors();

      // write the header
      if (isWithHeader()) {
        final String[] fileHeader = new String[] { "Designation", "Identifier", "Identifier Type", "Spam" };
        beanWriter.writeHeader(fileHeader);
      }
      // write the beans
      for (final AbstractEntity entity : entities) {
        beanWriter.write(entity, header, processors);
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