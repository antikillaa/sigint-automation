package data_for_entity.data_providers.profiler;

import data_for_entity.data_providers.EntityDataProvider;
import org.apache.commons.lang3.RandomStringUtils;

public class ProfileAndTargetGroupNameProvider implements EntityDataProvider {

    @Override
    public String generate(int length) {
        return "QE auto " + RandomStringUtils.randomAlphanumeric(8);
    }
}
