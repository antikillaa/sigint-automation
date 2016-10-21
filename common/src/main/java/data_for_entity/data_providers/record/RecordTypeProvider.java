package data_for_entity.data_providers.record;

import data_for_entity.data_providers.EntityDataProvider;
import model.RecordType;

public class RecordTypeProvider implements EntityDataProvider {
    @Override
    public Object generate(int length) {
        return RecordType.random();
    }
}
