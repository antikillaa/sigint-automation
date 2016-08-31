package model.bulders;

import abs.EntityList;
import errors.NullReturnException;
import model.SSMS;
import model.Target;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class SSMSGenerator {

    private SSMSBuilder ssmsBuilder;
    private Target target;

    public SSMSGenerator() {
        ssmsBuilder = new SSMSRandom();
    }

    /**
     *
     * @param target Target object, used for generate records 'to' and 'from' the target
     * @return this
     */
    public SSMSGenerator setTarget(Target target) {
        this.target = target;
        return this;
    }

    public SSMSGenerator random() {
        ssmsBuilder = new SSMSRandom();
        return this;
    }

    public SSMSGenerator toTarget() {
        ssmsBuilder = new SSMSToNumber(getTargetPhone());
        return this;
    }

    public SSMSGenerator toNumber(String number) {
        ssmsBuilder = new SSMSToNumber(number);
        return this;
    }

    public SSMSGenerator fromTarget() {
        ssmsBuilder = new SSMSFromNumber(getTargetPhone());
        return this;
    }

    public SSMSGenerator fromNumber(String number) {
        ssmsBuilder = new SSMSFromNumber(number);
        return this;
    }

    public SSMSGenerator withTextMention(String pattern) {
        ssmsBuilder = new SSMSWithTextMention(pattern);
        return this;
    }

    public SSMS getSSMS() {
        return ssmsBuilder.getSSMS();
    }

    public SSMS produce() {
        ssmsBuilder.createNewSSMS();
        ssmsBuilder.buildFromNumber();
        ssmsBuilder.buildToNumber();
        ssmsBuilder.buildText();

        return ssmsBuilder.getSSMS();
    }

    /**
     * Produce EntityList of S-SMS
     *
     * @param total total records
     * @param fromCount number records of them 'from' target number
     * @param toCount number records of them 'to' target number
     * @return EntityList
     */
    public EntityList<SSMS> produceList(int total, int fromCount, int toCount) {
        List<SSMS> ssmsList = new ArrayList<>();

        int from = 0;
        int to = 0;
        for (int i = 0; i < total; i++ ) {
            SSMS ssms;
            if (from < fromCount) {
                ssms = fromTarget().produce();
                from++;
            } else if (to < toCount) {
                ssms = toTarget().produce();
                to++;
            } else {
                ssms = random().produce();
            }
            ssmsList.add(ssms);
        }

        return new EntityList<SSMS>(ssmsList) {
            @Override
            public SSMS getEntity(String param) throws NullReturnException {
                return null;
            }
        };
    }

    private String getTargetPhone() {
        return RandomGenerator.getRandomItemFromList(new ArrayList<>(target.getPhones()));
    }
}
