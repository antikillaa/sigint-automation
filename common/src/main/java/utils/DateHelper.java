package utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    private static Logger log = Logger.getLogger(DateHelper.class);

    public static Date yesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    public static Date dateToFormat(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        if (date != null) {
            try {
                return dateFormat.parse(dateFormat.format(date));
            } catch (ParseException e) {
                log.error(e.getMessage());
                return null;
            }
        }
        return null;
    }
}
