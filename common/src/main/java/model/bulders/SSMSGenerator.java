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
    private GenerationMatrix generationMatrix;

    public SSMSGenerator() {
        ssmsBuilder = new SSMSRandom();
    }

    /**
     * Set GenerationMatrix for generation list of test data according to matrix
     * @param generationMatrix GenerationMatrix
     * @return SSMSGenerator
     */
    public SSMSGenerator setGenerationMatrix(GenerationMatrix generationMatrix) {
        this.generationMatrix = generationMatrix;
        return this;
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
    public EntityList<SSMS> produceList() {
        List<SSMS> ssmsList = new ArrayList<>();
        GenerationMatrix matrix = generationMatrix;

        for (GenerationMatrixRow row : matrix.getRows()) {
            String phone = getTargetPhone(row.getTarget());

            int from = 0;
            while (from < row.getFromNumberCount()) {
                ssmsList.add(fromNumber(phone).produce());
                from++;
            }

            int to = 0;
            while (to < row.getToNumberCount()) {
                ssmsList.add(toNumber(phone).produce());
                to++;
            }

            int toMention = 0;
            while (toMention < row.getToNumberMention()) {
                ssmsList.add(withTextMention(phone).produce());
                toMention++;
            }

            int fromMention = 0;
            while (fromMention < row.getFromNumberMention()) {
                ssmsList.add(withTextMention(phone).produce());
                fromMention++;
            }

            int keywordMention = 0;
            while (keywordMention < row.getKeywordMention()) {
                List<String> keywords = new ArrayList<>(row.getTarget().getKeywords());
                String mention = RandomGenerator.getRandomItemFromList(keywords);

                ssmsList.add(withTextMention(mention).produce());
                keywordMention++;
            }

            int nameMention = 0;
            while (nameMention < row.getNameMention()) {
                ssmsList.add(withTextMention(row.getTarget().getName()).produce());
                nameMention++;
            }

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
    public EntityList<SSMS> produceList(int count) {
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
