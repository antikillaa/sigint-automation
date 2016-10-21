package data_for_entity.data_providers.record;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import model.RecordType;
import utils.RandomGenerator;

public class RecordBodyVoiceProvider extends DependencyDataProvider {
    
    @Override
    public Object generate(int length) {
        DependencyData dependencyData = getDependencyData();
        String type = (String) dependencyData.getData("type");
        if (type.equals(RecordType.Voice)) {
            return RandomGenerator.getRandomDuration();
        } else  return null;
    }
}
