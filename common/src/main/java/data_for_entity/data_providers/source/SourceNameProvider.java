package data_for_entity.data_providers.source;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import org.apache.commons.lang.RandomStringUtils;

public class SourceNameProvider extends DependencyDataProvider {

    @Override
    public Object generate(int length) {
        DependencyData dependencyData = getDependencyData();
        String sourceType = (String) dependencyData.getData("type");
        String recordType = (String) dependencyData.getData("recordType");

        if (sourceType == null || recordType == null) {
            return null;
        }
        return sourceType + "-" + recordType + "-" + RandomStringUtils.randomAlphabetic(10);
    }
}
