package utils;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static Date yesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }
}
