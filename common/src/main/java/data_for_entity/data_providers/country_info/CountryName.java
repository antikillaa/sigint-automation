package data_for_entity.data_providers.country_info;

import data_for_entity.data_providers.EntityDataProvider;
import utils.RandomGenerator;

public class CountryName implements EntityDataProvider {
    @Override
    public Object generate(int length) {
        return RandomGenerator.getCountryName(RandomGenerator.generateCountryCode());
    }
}
