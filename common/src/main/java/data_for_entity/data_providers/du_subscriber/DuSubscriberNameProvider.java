package data_for_entity.data_providers.du_subscriber;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;

public class DuSubscriberNameProvider extends DependencyDataProvider {
    
    @Override
    public Object generate(int length) {
        DependencyData dependencyData = getDependencyData();
        String firstName = (String)dependencyData.getData("firstName");
        String middleName = (String)dependencyData.getData("middleName");
        String lastName = (String)dependencyData.getData("lastName");
        return firstName + " " + middleName + " " + lastName;
    }
}
