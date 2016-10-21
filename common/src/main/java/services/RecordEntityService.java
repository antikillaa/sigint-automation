package services;

import data_for_entity.RandomEntities;
import model.Record;
import model.RecordType;
import org.apache.commons.lang.RandomStringUtils;
import utils.RandomGenerator;

public class RecordEntityService {
    
    
    
    public Record createVoiceRecord() {
    
        RandomEntities objectInitializer = new RandomEntities();
        Record record = objectInitializer.randomEntity(Record.class);
        if (record.getType() != RecordType.Voice) {
            record.setType(RecordType.Voice);
            record.setDuration(RandomGenerator.getRandomDuration());
            record.setText(null);
        }
        return record;
    }
    
    public Record createSMSRecord() {
        RandomEntities objectInitializer = new RandomEntities();
        Record record = objectInitializer.randomEntity(Record.class);
        if (record.getType() != RecordType.SMS) {
            record.setType(RecordType.SMS);
            record.setText(RandomStringUtils.randomAlphabetic(30));
            record.setDuration(0);
        }
        return record;
    }
    
    public Record createReportRecord() {
        RandomEntities objectInitializer = new RandomEntities();
        Record record = (Record)objectInitializer.randomEntity(Record.class);
        record.setTypeEnglishName(record.getType().toEnglishName());
        record.setTypeArabicName(record.getType().toArabicName());
        return record;
    }
    
    
    
    
    
}
