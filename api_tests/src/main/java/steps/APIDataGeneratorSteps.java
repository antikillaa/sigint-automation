package steps;

import file_generator.FileGenerator;
import model.*;
import model.bulders.SSMSGenerator;
import org.jbehave.core.annotations.Given;

import java.util.List;

public class APIDataGeneratorSteps extends APISteps {

    @Given("$sType - $rType data file with records for test targets was generated")
    public void generateEntityList(String sType, String rType) {

        SourceType sourceType = SourceType.valueOf(sType);
        RecordType recordType = RecordType.valueOf(rType);

        switch (sourceType) {
            case Strategic:
                switch (recordType) {
                    case SMS:
                        GenerationMatrix matrix = context.get("generationMatrix", GenerationMatrix.class);

                        List<SSMS> ssmsList = new SSMSGenerator().produceSSMSListByMatrix(matrix);
                        G4File file = new FileGenerator(SSMS.class).write(ssmsList);

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
