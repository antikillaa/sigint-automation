package data_for_entity.data_providers.record;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import utils.RandomGenerator;

public class RecordBodyVoiceProvider extends DependencyDataProvider {
    
    @Override
    public Integer generate(int length) {
        DependencyData dependencyData = getDependencyData();
        String type = (String) dependencyData.getData("type");
        if (type.equals("Voice")) {
            return RandomGenerator.getRandomDuration();
        } else  return null;
    }
}
