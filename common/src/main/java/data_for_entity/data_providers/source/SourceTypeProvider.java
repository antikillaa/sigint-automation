package data_for_entity.data_providers.source;

import app_context.AppContext;
import data_for_entity.data_providers.EntityDataProvider;
import model.SourceType;
import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;

import static utils.RandomGenerator.getRandomItemFromList;

public class SourceTypeProvider implements EntityDataProvider {

    private AppContext context = AppContext.get();

    @Override
    public String generate(int length) {
        List<SourceType> sourceTypes = context.getDictionary().getSourceTypes();
        Assert.assertNotNull("Got null sourceTypes list from dictionary", sourceTypes);

        List<SourceType> typeList = sourceTypes.stream()
                .filter(sType -> sType.getDataSource() != null &&
                        sType.getEventFeed().equals("SIGINT") &&
                        sType.getSubSource() != null
                ).collect(Collectors.toList());

        if (typeList.isEmpty()) throw new AssertionError("Unable get any data source from dictionary!");
        return getRandomItemFromList(typeList).getDataSource();
    }
}
