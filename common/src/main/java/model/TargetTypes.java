package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TargetTypes {
    SUSPECT, CRIME, INTEREST;


    private static final List<TargetTypes> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();


    public static TargetTypes getRandom() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
