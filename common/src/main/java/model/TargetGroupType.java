package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TargetGroupType {

    TargetGroup;

    private static final List<TargetGroupType> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();


    public static TargetGroupType random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
