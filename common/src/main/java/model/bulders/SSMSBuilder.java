package model.bulders;

import model.SSMS;

/**
 * S-SMS Builder
 */
abstract class SSMSBuilder {

    SSMS ssms;
    String pattern;

    SSMSBuilder createNewSSMS() {
        ssms = new SSMS().generate();
        return this;
    }

    SSMSBuilder setPattern(String value) {
        pattern = value;
        return this;
    }

    SSMS getSSMS() {
        return ssms;
    }

    public abstract SSMSBuilder buildText();

    public abstract SSMSBuilder buildToNumber();

    public abstract SSMSBuilder buildFromNumber();

}
