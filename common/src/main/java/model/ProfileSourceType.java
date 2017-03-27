package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ProfileSourceType {

    MANUAL, T, S, TWEET,
    Tactical, Strategic, F, C, O, J, HumanInput, Etisalat, X;

    private static final List<ProfileSourceType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();

    public static ProfileSourceType random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
