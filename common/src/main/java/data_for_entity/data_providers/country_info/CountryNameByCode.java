package data_for_entity.data_providers.country_info;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import utils.RandomGenerator;

public class CountryNameByCode extends DependencyDataProvider {
    
    @Override
    public Object generate(int length) {
        DependencyData dependencyData = getDependencyData();
        return RandomGenerator.getCountryName((String)dependencyData.getData("countryCodeOriginal"));
        
    }
}
