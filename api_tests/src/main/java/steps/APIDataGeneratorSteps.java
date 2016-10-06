package steps;

import data_generator.DataGenerator;
import file_generator.FileGenerator;
import model.*;
import model.bulders.SSMSGenerator;
import org.jbehave.core.annotations.Given;
import org.junit.Assert;

import java.util.List;

public class APIDataGeneratorSteps extends APISteps {

    @Given("$sType - $rType data file with records for test targets was generated")
    public void generateEntityList(String sType, String rType) {

        SourceType sourceType = SourceType.valueOf(sType);
        RecordType recordType = RecordType.valueOf(rType);

        GenerationMatrix matrix = context.get("generationMatrix", GenerationMatrix.class);

        G4File file = null;
        List list;

        switch (sourceType) {
            case Strategic:
                switch (recordType) {
                    case SMS:
                        list = new SSMSGenerator().produceSSMSListByMatrix(matrix);
                        file = new FileGenerator(SSMS.class).write(list);
                        break;
                    default:
                        break;
                }
                break;
            case X:
                switch (recordType) {
                    case SMS:
                        list = new DataGenerator(XSMS.class).produceListByMatrix(matrix);
                        file = new FileGenerator(XSMS.class).write(list);
                        break;
                    case Voice:
                        list = new DataGenerator(XVoiceMetadata.class).produceListByMatrix(matrix);
                        file = new FileGenerator(XVoiceMetadata.class).write(list);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        context.put("g4file", file);
        Assert.assertNotNull("Data file does not create!", file);
    }

}
