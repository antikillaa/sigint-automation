package data_for_entity.data_providers.source;

import data_for_entity.data_providers.EntityDataProvider;
import model.SourceType;

public class SourceTypeProvider implements EntityDataProvider {
    
    @Override
    public SourceType generate(int length) {
        SourceType sourceType;
        int i = 0;
        do {
            sourceType = SourceType.random();
            i++;
        } while (sourceType.equals(SourceType.X) || i < SourceType.values().length);
        return sourceType;
    }
}
