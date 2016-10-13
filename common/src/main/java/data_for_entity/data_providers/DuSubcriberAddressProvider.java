package data_for_entity.data_providers;

public class DuSubcriberAddressProvider extends DependencyDataProvider {
    @Override
    public Object generate(int length) {
        DependencyData dependencyData = getDependencyData();
        String city = (String)dependencyData.getData("city");
        String poBox = (String)dependencyData.getData("poBox");
        return city + ", " + poBox;
                
    }
}
