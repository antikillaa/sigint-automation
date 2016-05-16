package model;

import java.util.*;

public enum InformationRequestDistribution {

    SIGINT, CIO, ANALYSIS, OA;

    private static final List<InformationRequestDistribution> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();



    public static List<InformationRequestDistribution> getRandom() {
        HashSet<InformationRequestDistribution> set = new HashSet<>();
        int maxNumber = RANDOM.nextInt(SIZE);
        for(int i=0; i< maxNumber;i++) {
            set.add(VALUES.get(RANDOM.nextInt(SIZE)));
        }
        return new ArrayList<InformationRequestDistribution>(set);
    }

}

