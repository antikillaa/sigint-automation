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

    /**
     * Checks if param date is already passed in comparision to now
     *
     * @param deadline this date will be verified against now
     * @return True if deadline occurred, False otherwise
     */
    public static boolean isTimeout(Date deadline) {
        Calendar now = Calendar.getInstance();
        return now.after(deadline);

    }

    /**
     * Thread sleep for the specified number of secs.
     *
     * @param secs Seconds to sleep.
     */
    public static void waitTime(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            log.debug("Thread sleep was interrupted");
        }
    }

    /**
     * Helps to receive Date with the passed shift in secs.
     *
     * @param shiftInSecs shift to the current date in secs.
     * @return Date with applied time shift in secs.
     */
    public static Date getDateWithShift(int shiftInSecs) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.SECOND, shiftInSecs);
        return date.getTime();
    }

}
