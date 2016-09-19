package model;

import data_for_entity.data_providers.EntityDataProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by dm on 4/27/16.
 */
public enum InformationRequestSearchType implements EntityDataProvider {

    ST, S;

    private static final List<InformationRequestSearchType> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();
    
    @Override
    public Object generate(int length) {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
    
}
