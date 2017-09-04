package data_for_entity.data_providers.source;

import app_context.AppContext;
import data_for_entity.data_providers.EntityDataProvider;
import model.SourceType;
import utils.RandomGenerator;

import java.util.List;
import java.util.stream.Collectors;

import static utils.StringUtils.stringContainsAny;

public class SourceTypeProvider implements EntityDataProvider {

    @Override
    public String generate(int length) {
        SourceType sourceType;
        int ATTEMPT_NUMBER = 5;
        int i = 0;

        List<SourceType> sourceTypes = AppContext.get().getDictionary().getSourceTypes();
        if (sourceTypes == null) {
            throw new AssertionError("Got null sourceTypes list in Context");
        }
        do {
            i++;
            List<SourceType> typeList = sourceTypes.stream()
                    .filter(sType -> sType.getType() != null)
                    .collect(Collectors.toList());
            if (typeList.isEmpty()) throw new AssertionError("Unable get any data source from dictionary!");
            sourceType = RandomGenerator.getRandomItemFromList(typeList);
        } while (stringContainsAny(sourceType.getType(), "X", "H", "SY") && i < ATTEMPT_NUMBER); //FIXME after update dictionary
        return sourceType.getType();
    }
}
