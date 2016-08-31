package steps;

import abs.EntityList;
import file_generator.FileGenerator;
import model.G4File;
import model.RecordType;
import model.SSMS;
import model.SourceType;
import model.bulders.SSMSGenerator;
import model.lists.TargetsList;
import org.jbehave.core.annotations.Given;

public class APIDataGeneratorSteps extends APISteps {

    @Given("$sType - $rType data file with: total $nTotal records, $numTo of them toTarget, $numFrom of them fromTarget was generated")
    public void generateEntityList(String sType, String rType, String numTotal, String numTo, String numFrom) {

        SourceType sourceType = SourceType.valueOf(sType);
        RecordType recordType = RecordType.valueOf(rType);
        Integer numRecords = Integer.valueOf(numTotal);
        Integer numToTarget = Integer.valueOf(numTo);
        Integer numFromTarget = Integer.valueOf(numFrom);

        FileGenerator fileGenerator = new FileGenerator();
        switch (sourceType) {
            case Strategic:
                switch (recordType) {
                    case SMS:
                        TargetsList targetList = context.entities().getTargets();

                        SSMSGenerator ssmsGenerator = new SSMSGenerator();
                        EntityList<SSMS> ssmsList = ssmsGenerator
                                .setTarget(targetList.random())
                                .produceList(numRecords, numFromTarget, numToTarget);

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
