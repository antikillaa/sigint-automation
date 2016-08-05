package model.bulders;

import model.SSMS;

abstract class SSMSBuilder {

    protected SSMS ssms;
    protected String pattern;

    public SSMSBuilder createNewSSMS() {
        ssms = new SSMS().generate();
        return this;
    }

    public SSMSBuilder setPattern(String value) {
        pattern = value;
        return this;
    }

    public SSMS getSSMS() {
        return ssms;
    }

    public abstract SSMSBuilder buildText();

    public abstract SSMSBuilder buildToNumber();

    public abstract SSMSBuilder buildFromNumber();

}
