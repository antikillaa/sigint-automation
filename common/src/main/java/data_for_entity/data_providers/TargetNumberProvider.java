package data_for_entity.data_providers;

public class TargetNumberProvider extends DependencyDataProvider {

    @Override
    public String generate(int length) {
        DependencyData dependencyData = getDependencyData();
        return (String) dependencyData.getData("fromNumber");
    }
}
