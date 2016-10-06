package data_for_entity.data_providers;

import utils.RandomGenerator;

public class SMSTextProvider implements EntityDataProvider {

    private static String mention = "";

    public static void setMention(String mention) {
        SMSTextProvider.mention = mention;
    }

    @Override
    public Object generate(int length) {
        return RandomGenerator.generateSMSText(mention);
    }


}
