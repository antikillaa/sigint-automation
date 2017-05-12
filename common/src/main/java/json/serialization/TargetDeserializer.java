package json.serialization;

import model.entities.Entities;
import errors.NullReturnException;
import model.TargetGroup;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TargetDeserializer extends JsonDeserializer<List<TargetGroup>> {
    @Override
    public List<TargetGroup> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Iterator<JsonNode> iter = node.getElements();
        List<TargetGroup> targetGroups = new ArrayList<>();
        while (iter.hasNext()) {
            JsonNode groupNode = iter.next();
            String targetGroupId = groupNode.get("id").getTextValue();
            try {
                TargetGroup targetGroup = Entities.getTargetGroups().getEntity(targetGroupId);
                targetGroups.add(targetGroup);
            } catch (NullReturnException e) {
                throw new AssertionError("Target group with id:"+targetGroupId+" is not found!");
            }
        }

        return targetGroups;

    }
}
