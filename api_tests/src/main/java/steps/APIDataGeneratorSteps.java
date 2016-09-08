package steps;

import abs.EntityList;
import file_generator.FileGenerator;
import model.*;
import model.bulders.SSMSGenerator;
import org.jbehave.core.annotations.Given;

public class APIDataGeneratorSteps extends APISteps {

    @Given("$sType - $rType data file with records for test targets was generated")
    public void generateEntityList(String sType, String rType) {

        SourceType sourceType = SourceType.valueOf(sType);
        RecordType recordType = RecordType.valueOf(rType);

        FileGenerator fileGenerator = new FileGenerator();
        switch (sourceType) {
            case Strategic:
                switch (recordType) {
                    case SMS:
                        GenerationMatrix matrix = context.get("generationMatrix", GenerationMatrix.class);

                        EntityList<SSMS> ssmsList = new SSMSGenerator().setGenerationMatrix(matrix).produceList();
                        G4File file = fileGenerator.SSMS().write(ssmsList);
                        context.put("ssmsFile", file);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

}
