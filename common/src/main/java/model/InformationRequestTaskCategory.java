package model;

import java.util.*;

public enum InformationRequestTaskCategory {

    TARGET_DEVELOPMENT, ANALYSIS_OSINT, TALENT_DEVELOPMENT, OPERATIONAL_REQUIREMENTS;

    private static final List<InformationRequestTaskCategory> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();



    public static List<InformationRequestTaskCategory> getRandom() {
        Set<InformationRequestTaskCategory> set = new HashSet<>();
        int maxNum = RANDOM.nextInt(SIZE);
        for(int i=0; i< maxNum;i++) {
            set.add(VALUES.get(RANDOM.nextInt(SIZE)));
        }
        return new ArrayList<InformationRequestTaskCategory>(set);
    }

}
