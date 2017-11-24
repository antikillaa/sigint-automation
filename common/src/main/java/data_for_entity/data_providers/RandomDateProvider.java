package data_for_entity.data_providers;

import utils.RandomGenerator;

import java.util.Date;

public class RandomDateProvider implements EntityDataProvider {

    @Override
    public Date generate(int length) {
        return RandomGenerator.randomMonthRangeDate();
    }
}
