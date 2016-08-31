package json.serialization;

import app_context.entities.Entities;
import errors.NullReturnException;
import model.Target;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TargetGroupDeserializer extends JsonDeserializer<List<Target>> {
    @Override
    public List<Target> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Iterator<JsonNode> iter = node.getElements();
        List<Target> targets = new ArrayList<>();
        while (iter.hasNext()) {
            JsonNode nodeId = iter.next();
            String targetId = nodeId.getTextValue();
            try {
                Target target = Entities.getTargets().getEntity(targetId);
                targets.add(target);
            } catch (NullReturnException e) {
                throw new AssertionError("Target with id:"+targetId+" is not found!");
            }
        }

    return targets;

    }
}
