package data_for_entity.data_providers.profiler;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import model.TargetStatus;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static utils.RandomGenerator.getRandomItemFromList;

public class TargetStatusProvider extends DependencyDataProvider {

    private static Logger log = Logger.getLogger(TargetStatusProvider.class);

    @Override
    public TargetStatus generate(int length) {
        DependencyData dependencyData = getDependencyData();
        String activeUntil = (String) dependencyData.getData("activeUntil");

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:s z yyyy");
        Date date;
        try {
            date = sdf.parse(activeUntil);
        } catch (ParseException e) {
            log.error(e);
            throw new AssertionError(e);
        }

        List<TargetStatus> expired = new ArrayList<>(asList(TargetStatus.INACTIVE, TargetStatus.ARCHIVED));
        return date.before(new Date()) ? getRandomItemFromList(expired) : TargetStatus.ACTIVE;
    }
}
