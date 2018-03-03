package services;

import app_context.AppContext;
import data_for_entity.RandomEntities;
import model.Record;
import model.SourceType;
import org.apache.commons.lang3.RandomStringUtils;
import utils.RandomGenerator;

import java.util.Objects;

public class RecordEntityService {

    public Record createVoiceRecord() {
        RandomEntities objectInitializer = new RandomEntities();
        Record record = objectInitializer.randomEntity(Record.class);

        if (!Objects.equals(record.getType(), "Voice")) {
            record.setType("Voice");
            record.setDuration(RandomGenerator.getRandomDuration());
            record.setText(null);
        }
        return record;
    }

    public Record createSMSRecord() {
        RandomEntities objectInitializer = new RandomEntities();
        Record record = objectInitializer.randomEntity(Record.class);
        if (!Objects.equals(record.getType(), "SMS")) {
            record.setType("SMS");
            record.setText(RandomStringUtils.randomAlphabetic(30));
            record.setDuration(0);
        }
        return record;
    }

    public Record createReportRecord() {
        RandomEntities objectInitializer = new RandomEntities();
        Record record = objectInitializer.randomEntity(Record.class);
        SourceType sourceType = AppContext.get().getDictionary().getByRecordType(record.getType());
        record.setTypeEnglishName(sourceType.getSubSource());
        record.setTypeArabicName(sourceType.getSubSource());
        return record;
    }
}
