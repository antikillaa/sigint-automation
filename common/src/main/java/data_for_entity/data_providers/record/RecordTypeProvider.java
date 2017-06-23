package data_for_entity.data_providers.record;

import app_context.AppContext;
import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import model.RecordType;
import utils.RandomGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class RecordTypeProvider extends DependencyDataProvider {

    @Override
    public String generate(int length) {
        RecordType recordType;

        DependencyData dependencyData = getDependencyData();
        String sourceType = (String) dependencyData.getData("type");

        List<RecordType> typeList = AppContext.get().getDictionary()
                .getRecordTypes().stream()
                .filter(type -> type.getType() != null && type.getType().equals(sourceType))
                .collect(Collectors.toList());

        if (typeList.isEmpty()) return null;
        recordType = RandomGenerator.getRandomItemFromList(typeList);
        return recordType.getSubSource();
    }
}