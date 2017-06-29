package data_for_entity.data_providers.record;

import app_context.AppContext;
import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import model.SourceType;
import utils.RandomGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class RecordTypeProvider extends DependencyDataProvider {

    @Override
    public String generate(int length) {

        DependencyData dependencyData = getDependencyData();
        String sSourceType = (String) dependencyData.getData("type");

        if (sSourceType != null) {
            List<SourceType> typeList = AppContext.get().getDictionary()
                    .getSourceTypes().stream()
                    .filter(type -> type.getType() != null && type.getType().equals(sSourceType))
                    .collect(Collectors.toList());

            return typeList.isEmpty() ? null : RandomGenerator.getRandomItemFromList(typeList).getSubSource();
        } else {
            return null;
        }
    }
}