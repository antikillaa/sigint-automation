package steps;

import abs.EntityList;
import errors.NullReturnException;
import file_generator.FileGenerator;
import model.RecordType;
import model.SSMS;
import model.SourceType;
import model.Target;
import model.bulders.SSMSGenerator;
import org.jbehave.core.annotations.Given;
import utils.Parser;
import utils.RandomGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class APIDataGeneratorSteps extends APISteps {

    @Given("$sType - $rType data file with: total $nTotal records, $numTo of them toTarget, $numFrom of them fromTarget was generated")
    public void generateEntityList(String sType, String rType, String numTotal, String numTo, String numFrom) {

        SourceType sourceType = SourceType.valueOf(sType);
        RecordType recordType = RecordType.valueOf(rType);
        Integer numRecords = Integer.valueOf(numTotal);
        Integer numToTarget = Integer.valueOf(numTo);
        Integer numFromTarget = Integer.valueOf(numFrom);

        FileGenerator generator = new FileGenerator();
        switch (sourceType) {
            case Strategic:
                switch (recordType) {
                    case SMS:
                        EntityList<SSMS> ssmsList = generateSSMSList(numRecords, numFromTarget, numToTarget);
                        File file = generator.SSMS().write(ssmsList);
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

    private EntityList<SSMS> generateSSMSList(int total, int fromTarget, int toTarget) {
        List<SSMS> ssmsList = new LinkedList<>();

        int from = 0;
        int to = 0;
        for (int i = 0; i < total; i++ ) {
            SSMSGenerator generator = new SSMSGenerator();

            if (from < fromTarget) {
                String targetPhone = getPhoneFromTargetList();
                generator = generator.fromNumber(targetPhone);
                from++;
            } else if (to < toTarget) {
                String targetPhone = getPhoneFromTargetList();
                generator = generator.toNumber(targetPhone);
                to++;
            }

            SSMS ssms = generator.generateSSMS();
            log.debug(Parser.entityToString(ssms));
            ssmsList.add(ssms);
        }

        return new EntityList<SSMS>(ssmsList) {
            @Override
            public SSMS getEntity(String param) throws NullReturnException {
                return null;
            }
        };
    }

    private String getPhoneFromTargetList() {
        List<Target> targetList = context.get("targetList", List.class);
        if (targetList.isEmpty()) {
            throw new AssertionError("TargetList is empty");
        }
        Target target = RandomGenerator.getRandomItemFromList(new ArrayList<>(targetList));
        if (target.getPhones().isEmpty()) {
            throw new AssertionError("PhoneList is empty");
        }
        return RandomGenerator.getRandomItemFromList(new ArrayList<>(target.getPhones()));
    }

}
