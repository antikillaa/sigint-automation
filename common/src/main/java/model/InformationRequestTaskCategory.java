package model;

import data_for_entity.data_providers.EntityDataProvider;

import java.util.*;

public enum InformationRequestTaskCategory implements EntityDataProvider{
    
    
    

    TARGET_DEVELOPMENT, ANALYSIS_OSINT, TALENT_DEVELOPMENT, OPERATIONAL_REQUIREMENTS;
    
    @Override
    public Object generate(int length) {
        Set<InformationRequestTaskCategory> set = new HashSet<>();
        int maxNum = RANDOM.nextInt(SIZE);
        for(int i=0; i< maxNum;i++) {
            set.add(VALUES.get(RANDOM.nextInt(SIZE)));
        }
        return new ArrayList<>(set);
    }
    

    private static final List<InformationRequestTaskCategory> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();

    
}
