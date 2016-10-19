package data_generator;

import data_for_entity.RandomEntities;
import data_for_entity.data_providers.target.SMSTextProvider;
import model.*;
import model.bulders.SSMSGenerator;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator implements DataGeneratorService {

    private Class<?> aClass;
    private DataGeneratorService dataGeneratorService;

    public DataGenerator(Class<?> entityClass) {
        aClass = entityClass;
    }

    @Override
    public List produceListByMatrix(GenerationMatrix matrix) {

        if (aClass.equals(XSMS.class)) {
            dataGeneratorService = new XSMSGenerator();
        } else if (aClass.equals(XVoiceMetadata.class)) {
            dataGeneratorService = new XVoiceMetadataGenerator();
        } else {
            throw new Error("Unknown TeelaEntity. Unable to initialize DataGenerator instance!");
        }

        return dataGeneratorService.produceListByMatrix(matrix);
    }

    public List produceList(int size) {
        if (aClass == SSMS.class) {
            return new SSMSGenerator().produceSSMSListRandomly(size);
        }
        return new RandomEntities().randomEntities(aClass, size);
    }

    public Object produce() {
        return new RandomEntities().randomEntity(aClass);
    }

    public Object produceSMSWithMention(String mention) {
        SMSTextProvider.setMention(mention);
        Object sms = new RandomEntities().randomEntity(aClass);
        SMSTextProvider.setMention("");
        return sms;
    }

    String getTargetPhone(Target target) {
        return RandomGenerator.getRandomItemFromList(new ArrayList<>(target.getPhones()));
    }
}
