package ae.pegasus.framework.json.serialization;

import ae.pegasus.framework.model.ProfileCategory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class ProfileCategoryDeserializer extends JsonDeserializer<ProfileCategory> {

    @Override
    public ProfileCategory deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        JsonNode node = jp.getCodec().readTree(jp);
        String displayName = node.getTextValue();

        for (ProfileCategory category : ProfileCategory.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        return null;
    }

}
