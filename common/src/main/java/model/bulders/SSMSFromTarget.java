package model.bulders;

class SSMSFromTarget extends SSMSBuilder {

    SSMSFromTarget(String targetPhoneNumber) {
        setPattern(targetPhoneNumber);
    }

    @Override
    public SSMSBuilder buildText() {
        return this;
    }

    @Override
    public SSMSBuilder buildToNumber() {
        return this;
    }

    public SSMSBuilder buildFromNumber() {
        ssms.setCaller_mod(pattern);
        return this;
    }
}
