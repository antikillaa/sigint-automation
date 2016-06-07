package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TeelaDate extends Date {

    public TeelaDate(Date date) {
        super(date.getTime());
    }

    public TeelaDate() {
        super();
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("EEE MMM dd HH:mm").format(this);
    }

}
