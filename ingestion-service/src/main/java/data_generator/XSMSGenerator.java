package data_generator;


import model.GenerationMatrix;
import model.GenerationMatrixRow;
import model.XSMS;
import utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

class XSMSGenerator extends DataGenerator {

    XSMSGenerator() {
        super(XSMS.class);
    }

    @Override
    public List<XSMS> produceListByMatrix(GenerationMatrix generationMatrix) {
        List<XSMS> list = new ArrayList<>();

        /*
            Generate X-SMS list for each target from GenerationMatrix,
            according to the current Generation matrix row with target and 'from/to' target records,
            or any mention about this target parameters for him
         */
        for (GenerationMatrixRow row : generationMatrix.getRows()) {
            String phone = getTargetPhone(row.getTarget());
            XSMS xsms;

            // generate X-SMS list 'from' current target phone
            int from = 0;
            while (from < row.getFromNumberCount()) {
                xsms = (XSMS) produce();
                xsms.setCallerMod(phone);
                list.add(xsms);
                from++;
            }

            // generate X-SMS list 'to' current target phone
            int to = 0;
            while (to < row.getToNumberCount()) {
                xsms = (XSMS) produce();
                xsms.setCallerMod(phone);
                list.add(xsms);
                to++;
            }

            // generate X-SMS list with 'target phone' mention in the text message
            int fromMention = 0;
            while (fromMention < row.getNumberMention()) {
                xsms = (XSMS) produceSMSWithMention(phone);
                list.add(xsms);
                fromMention++;
            }

            // generate X-SMS list with 'target keyword' mention in the text message
            int keywordMention = 0;
            while (keywordMention < row.getKeywordMention()) {
                List<String> keywords = new ArrayList<>(row.getTarget().getKeywords());
                String mention = RandomGenerator.getRandomItemFromList(keywords);

                xsms = (XSMS) produceSMSWithMention(mention);
                list.add(xsms);
                keywordMention++;
            }

            // generate X-SMS list with 'target name' mention in the text message
            int nameMention = 0;
            while (nameMention < row.getNameMention()) {
                String mention = row.getTarget().getName();

                xsms = (XSMS) produceSMSWithMention(mention);
                list.add(xsms);
                nameMention++;
            }

            // generate randomly X-SMS list without any target mention in the text message
            int withoutHitMention = 0;
            while (withoutHitMention < row.getWithoutHitMention()) {
                xsms = (XSMS) produce();
                list.add(xsms);
                withoutHitMention++;
            }
        }

        return list;
    }

}
