package model;

import data_for_entity.data_providers.EntityDataProvider;

import java.util.*;

public enum Permission implements EntityDataProvider {

    UM_ADMIN,
    RECORDS_READ, RECORDS_ASSIGN, RECORDS_PROCESS, RECORDS_CREATE,
    REPORT_READ, REPORT_LIST, REPORT_EXPORT, REPORT_UPDATE, REPORT_DELETE,
    REPORT_UPDATE_ANALYST, REPORT_UPDATE_APPROVER,
    REPORT_CATEGORIES_LIST, REPORT_CATEGORIES_UPDATE, REPORT_CATEGORIES_DELETE,
    REPORT_TEAM_READ, REPORT_TEAM_UPDATE,
    UPLOAD_UPLOAD, UPLOAD_SOURCE_UPDATE,
    RFI_READ, RFI_LIST, RFI_UPDATE, RFI_LIST_DRAFT, RFI_PROCESS, RFI_UPDATE_SENT,
    TARGETS_READ, TARGETS_UPDATE, TARGETS_DELETE,
    STREAMS_READ, STREAMS_UPDATE, STREAMS_DELETE,
    PHONEBOOK_READ, PHONEBOOK_UPDATE;


    private static final List<Permission> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();


    public static Permission getRandom() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
    
    @Override
    public Object generate(int length) {
    
        Set<String> set = new HashSet<>();
        int maxNum = RANDOM.nextInt(SIZE);
        for(int i=0; i< maxNum;i++) {
            set.add(VALUES.get(RANDOM.nextInt(SIZE)).toString());
        }
        return set;
    }
    
}
