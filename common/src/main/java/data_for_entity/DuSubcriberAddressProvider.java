package data_for_entity;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;

public class DuSubcriberAddressProvider extends DependencyDataProvider {
    @Override
    public Object generate(int length) {
        DependencyData dependencyData = getDependencyData();
        String city = (String)dependencyData.getData("city");
        String poBox = (String)dependencyData.getData("poBox");
        return city + ", " + poBox;
                
    }
}
