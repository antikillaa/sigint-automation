package ae.pegasus.framework.data_for_entity.data_providers.whitelist;

import ae.pegasus.framework.data_for_entity.data_providers.DependencyData;
import ae.pegasus.framework.data_for_entity.data_providers.DependencyDataProvider;
import ae.pegasus.framework.model.WhiteListType;
import ae.pegasus.framework.utils.RandomGenerator;



public class WhiteListIdentifierProvider extends DependencyDataProvider {

    @Override
    public String generate(int length) {
        DependencyData dependencyData = getDependencyData();
        WhiteListType type = WhiteListType.valueOf((String) dependencyData.getData("type"));
        String identifier;
        switch (type) {
            case PHONE_NUMBER:
                identifier = RandomGenerator.generatePhone();
                break;
            case EMAIL_ADDRESS:
                identifier = RandomGenerator.generateEmail();
                break;
            case YOUTUBE_CHANNEL_ID:
                identifier = RandomGenerator.generateYoutubeChannelId();
                break;
            case TWITTER_HANDLE:
                identifier = RandomGenerator.generateTwitterHandle();
                break;
            case TWITTER_ID:
            case INSTAGRAM_ID:
            case DARK_WEB_AUTHOR_ID:
                identifier = RandomGenerator.generateID();
                break;
            default:
                identifier = "default_value";
                break;

        }
        return identifier;
    }
}
