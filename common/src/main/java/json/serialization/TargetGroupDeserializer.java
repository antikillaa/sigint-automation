package json.serialization;

import model.Target;
import model.entities.Entities;
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

            Target target = Entities.getTargets().getEntity(targetId);
            targets.add(target);
        }
        return targets;
    }
}
