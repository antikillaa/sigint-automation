package data_for_entity.data_providers.source;

import data_for_entity.data_providers.EntityDataProvider;
import model.SourceType;

public class SourceTypeProvider implements EntityDataProvider {
    
    @Override
    public Object generate(int length) {
        return SourceType.random();
    }
}
