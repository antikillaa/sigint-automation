package data_for_entity.data_providers.source;

import app_context.AppContext;
import data_for_entity.data_providers.EntityDataProvider;
import model.SourceType;
import utils.RandomGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class SourceTypeProvider implements EntityDataProvider {

    @Override
    public String generate(int length) {
        SourceType sourceType;
        int ATTEMPT_NUMBER = 3;
        int i = 0;
        do {
            i++;
            List<SourceType> typeList = AppContext.get().getDictionary()
                    .getSourceTypes().stream()
                    .filter(sType -> sType.getType() != null)
                    .collect(Collectors.toList());
            if (typeList.isEmpty()) return null;
            sourceType = RandomGenerator.getRandomItemFromList(typeList);
        } while (sourceType.getType().equals("X") && i < ATTEMPT_NUMBER);
        return sourceType.getType();
    }
}
