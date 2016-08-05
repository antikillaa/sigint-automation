package model.bulders;

class SSMSToTarget extends SSMSBuilder {

    SSMSToTarget(String pattern) {
        setPattern(pattern);
    }

    @Override
    public SSMSBuilder buildText() {
        return this;
    }

    @Override
    public SSMSBuilder buildToNumber() {
        ssms.setCalled_mod(pattern);
        return this;
    }

    @Override
    public SSMSBuilder buildFromNumber() {
        return this;
    }
}
