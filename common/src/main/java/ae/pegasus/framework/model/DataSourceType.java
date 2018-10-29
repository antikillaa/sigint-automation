package ae.pegasus.framework.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DataSourceType {

    // SIGINT
    DU, E, F, S, T, O, PHONEBOOK, J1, J2, H,
    // FININT
    CentralBank,
    // EID
    EID,
    // GOVINT
    SITA, UDB,
    // OSINT
    DARK_WEB, DARK_WEB_REPORTS, INSTAGRAM, NEWS, TWITTER, YOUTUBE,
    // TRAFFIC
    MCC,
    // CIO
    FORUM, KARMA, ODD_JOBS, ZELZAL;

    private static final List<DataSourceType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static Random RANDOM = new Random();

    public static DataSourceType random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
