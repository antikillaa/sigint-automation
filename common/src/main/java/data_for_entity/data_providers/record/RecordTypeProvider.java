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

        List<SourceType> sourceTypes = AppContext.get().getDictionary().getSourceTypes();
        if (sourceTypes == null) {
            throw new AssertionError("Got null sourceTypes list in Context");
        }

        if (sSourceType != null) {
            List<SourceType> typeList = sourceTypes.stream()
                    .filter(type -> type.getType() != null && type.getType().equals(sSourceType))
                    .collect(Collectors.toList());

            if (typeList.isEmpty()) {
                throw new AssertionError("Unable get subSource from dictionary for sourceType:" + sSourceType);
            } else {
                return RandomGenerator.getRandomItemFromList(typeList).getSubSource();
            }
        } else {
            throw new AssertionError("Unable generate subSource due sourceType is NULL");
        }
    }
}