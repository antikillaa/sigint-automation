package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PermissionType {

    AUTH_USER_ADMIN,

    UM_ADMIN, UM_USER,

    STREAMS_DELETE, STREAMS_UPDATE, STREAMS_READ,

    RFI_UPDATE, RFI_READ, RFI_LIST_DRAFT, RFI_LIST, RFI_PROCESS, RFI_UPDATE_SENT,

    PHONEBOOK_UPDATE, PHONEBOOK_READ,

    REPORT_READ, REPORT_UPDATE_ANALYST, REPORT_UPDATE_APPROVER, REPORT_LIST, REPORT_UPDATE, REPORT_EXPORT,
    REPORT_TEAM_READ, REPORT_TEAM_UPDATE,
    REPORT_CATEGORIES_DELETE, REPORT_CATEGORIES_UPDATE, REPORT_CATEGORIES_LIST,

    RECORDS_READ, RECORDS_ASSIGN, RECORDS_CREATE, RECORDS_PROCESS,

    UPLOAD_SOURCE_UPDATE, UPLOAD_UPLOAD,

    SEARCHES_READ,

    TARGETS_READ, TARGETS_UPDATE, TARGETS_INSIGHTS_READ, TARGETS_DELETE, TARGETS_ACTIVATION_UPDATE,
    TARGET_GROUPS_UPDATE, TARGET_GROUPS_DELETE, TARGET_GROUPS_READ, TARGET_GROUPS_SUBGROUP_UPDATE;

    private static final List<PermissionType> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();

    public static PermissionType getRandom() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static String random() {
        return VALUES.get(RANDOM.nextInt(SIZE)).toString();
    }

}
