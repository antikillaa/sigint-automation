package data_for_entity.data_providers.record;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import org.apache.commons.lang3.RandomStringUtils;

public class RecordBodySMSProvider extends DependencyDataProvider {

    @Override
    public String generate(int length) {
        DependencyData dependencyData = getDependencyData();
        String type = (String) dependencyData.getData("type");

        if (type.equals("SMS")) {
            return RandomStringUtils.randomAlphabetic(30);
        } else return null;
    }
}
