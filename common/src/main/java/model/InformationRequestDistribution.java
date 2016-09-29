package model;

import java.util.*;

public enum InformationRequestDistribution{

    SIGINT, CIO, ANALYSIS, OA;
    
    private static final List<InformationRequestDistribution> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();
    
   
    public static Object random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
    

}

