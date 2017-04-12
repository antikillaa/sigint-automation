package data_for_entity.data_providers.whitelist;

import data_for_entity.data_providers.DependencyData;
import data_for_entity.data_providers.DependencyDataProvider;
import model.WhiteListType;
import org.apache.commons.lang.RandomStringUtils;
import utils.RandomGenerator;



public class WhiteListIdentifierProvider extends DependencyDataProvider {
    @Override
    public String generate(int length) {
        DependencyData dependencyData = getDependencyData();
        WhiteListType type = WhiteListType.valueOf((String)dependencyData.getData("type"));
        String identifier;
        switch(type){
            case PHONE_NUMBER:
                identifier = RandomGenerator.generatePhone();
                break;
            case EMAIL:
                identifier = RandomGenerator.generateEmail();
                break;
            case TWITTER_ID:
                identifier = "@"+RandomStringUtils.randomAlphanumeric(10);
                break;
            default:
                identifier = "default_value";
                break;

        }
        return identifier;
    }
}
