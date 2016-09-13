package model.bulders;

import abs.EntityList;
import errors.NullReturnException;
import model.GenerationMatrix;
import model.GenerationMatrixRow;
import model.SSMS;
import model.Target;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class SSMSGenerator {

    private SSMSBuilder ssmsBuilder;

    public SSMSGenerator() {
        ssmsBuilder = new SSMSRandom();
    }

    /**
     * Set SSMSRandom S-SMS builder
     * @return SSMSGenerator
     */
    public SSMSGenerator random() {
        ssmsBuilder = new SSMSRandom();
        return this;
    }

    /**
     * Set SSMSToNumber S-SMS builder
     * @param number target number
     * @return SSMSGenerator
     */
    public SSMSGenerator toNumber(String number) {
        ssmsBuilder = new SSMSToNumber(number);
        return this;
    }

    /**
     * Set SSMSFromNumber S-SMS builder
     * @param number target number
     * @return SSMSGenerator
     */
    public SSMSGenerator fromNumber(String number) {
        ssmsBuilder = new SSMSFromNumber(number);
        return this;
    }

    /**
     * Set SSMSWithTextMention S-SMS builder
     * @param pattern target mention string (Target name, phone or keyword)
     * @return SSMSGenerator
     */
    public SSMSGenerator withTextMention(String pattern) {
        ssmsBuilder = new SSMSWithTextMention(pattern);
        return this;
    }

    public SSMS getSSMS() {
        return ssmsBuilder.getSSMS();
    }

    /**
     * Produce one S-SMS, according SSMSBuilder
     * @return S-SMS
     */
    public SSMS produce() {
        ssmsBuilder.createNewSSMS();
        ssmsBuilder.buildFromNumber();
        ssmsBuilder.buildToNumber();
        ssmsBuilder.buildText();

        return ssmsBuilder.getSSMS();
    }

    /**
     * Produce EntityList of S-SMS according GenerationMatrix
     * @return EntityList of S-SMS
     */
    public EntityList<SSMS> produceSSMSListByMatrix(GenerationMatrix generationMatrix) {
        List<SSMS> ssmsList = new ArrayList<>();

        /*
            Generate S-SMS list for each target from GenerationMatrix,
            according to the current Generation matrix row with target and 'from/to' target records,
            or any mention about this target parameters for him
         */
        for (GenerationMatrixRow row : generationMatrix.getRows()) {
            String phone = getTargetPhone(row.getTarget());

            // generate S-SMS list 'from' current target phone
            int from = 0;
            while (from < row.getFromNumberCount()) {
                ssmsList.add(fromNumber(phone).produce());
                from++;
            }

            // generate S-SMS list 'to' current target phone
            int to = 0;
            while (to < row.getToNumberCount()) {
                ssmsList.add(toNumber(phone).produce());
                to++;
            }

            // generate S-SMS list with 'target phone' mention in the text message
            int fromMention = 0;
            while (fromMention < row.getNumberMention()) {
                ssmsList.add(withTextMention(phone).produce());
                fromMention++;
            }

            // generate S-SMS list with 'target keyword' mention in the text message
            int keywordMention = 0;
            while (keywordMention < row.getKeywordMention()) {
                List<String> keywords = new ArrayList<>(row.getTarget().getKeywords());
                String mention = RandomGenerator.getRandomItemFromList(keywords);

                ssmsList.add(withTextMention(mention).produce());
                keywordMention++;
            }

            // generate S-SMS list with 'target name' mention in the text message
            int nameMention = 0;
            while (nameMention < row.getNameMention()) {
                ssmsList.add(withTextMention(row.getTarget().getName()).produce());
                nameMention++;
            }

            // generate randomly S-SMS list without any target mention in the text message
            int withoutHitMention = 0;
            while (withoutHitMention < row.getWithoutHitMention()) {
                ssmsList.add(random().produce());
                withoutHitMention++;
            }
        }

        return new EntityList<SSMS>(ssmsList) {
            @Override
            public SSMS getEntity(String param) throws NullReturnException {
                return null;
            }
        };
    }

    /**
     * Generate EntityList of random S-SMS.
     *
     * @param count S-SMS count
     * @return EntityList of S-SMS
     */
    public EntityList<SSMS> produceSSMSListRandomly(int count) {
        EntityList<SSMS> ssmsEntityList = new EntityList<SSMS>(new ArrayList<SSMS>()) {
            @Override
            public SSMS getEntity(String param) throws NullReturnException {
                return null;
            }
        };

        for (int i = 0; i < count; i++) {
            ssmsEntityList.getEntities().add(random().produce());
        }
        return ssmsEntityList;
    }

    private String getTargetPhone(Target target) {
        return RandomGenerator.getRandomItemFromList(new ArrayList<>(target.getPhones()));
    }
}
