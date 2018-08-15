package ae.pegasus.framework.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DataSourceType {

    DU,
    E,
    F,
    S,
    T,
    O,
    PHONEBOOK,
    //SUBSCRIBER,
    //SY,
    DARK_WEB,
    DARK_WEB_REPORTS,
    INSTAGRAM,
    NEWS,
    TWITTER,
    YOUTUBE,
    SITA,
    UDB,
    FORUM,
    KARMA,
    ODD_JOBS,
    ZELZAL,
    //TARGET,
    //TARGET_GROUP,
    //FLASHPOINT,
    H;

    private static final List<DataSourceType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();

    public static DataSourceType random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
