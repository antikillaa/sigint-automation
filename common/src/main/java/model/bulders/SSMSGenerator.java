package model.bulders;

import model.SSMS;

public class SSMSGenerator {

    private SSMSBuilder ssmsBuilder;

    public SSMSGenerator() {
        ssmsBuilder = new SSMSRandom();
    }

    public SSMSGenerator toNumber(String number) {
        ssmsBuilder = new SSMSToTarget(number);
        return this;
    }

    public SSMSGenerator fromNumber(String number) {
        ssmsBuilder = new SSMSFromTarget(number);
        return this;
    }

    public SSMSGenerator withTextMention(String pattern) {
        ssmsBuilder = new SSMSWithTextMention(pattern);
        return this;
    }

    public SSMS getSSMS() {
        return ssmsBuilder.getSSMS();
    }

    public SSMS generateSSMS() {
        ssmsBuilder.createNewSSMS();
        ssmsBuilder.buildFromNumber();
        ssmsBuilder.buildToNumber();
        ssmsBuilder.buildText();

        return ssmsBuilder.getSSMS();
    }
}
