package data_for_entity.data_providers.provision;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import utils.RandomGenerator;

public class ProvisionRegionCode extends DependencyDataProvider {
    @Override
    public Object generate(int length) {
        DependencyData dependencyData = getDependencyData();
        return RandomGenerator.getCountryName((String)dependencyData.getData("provisionedRegionCode"));
    }
}
