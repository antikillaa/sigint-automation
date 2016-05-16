package zapi.deserializer;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import zapi.model.Cycle;
import zapi.model.CyclesList;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dm on 4/6/16.
 */
public class CyclesDeserialize extends JsonDeserializer<CyclesList> {


    @Override
    public CyclesList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        CyclesList cycles = new CyclesList();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = new ObjectMapper();
        Iterator<Map.Entry<String, JsonNode>> iter = node.getFields();
        Cycle cycle;
        while (iter.hasNext()) {
            try {
                Map.Entry<String, JsonNode> foundNode = iter.next();
                cycle = mapper.readValue(foundNode.getValue(), Cycle.class);
                cycle.setId(Integer.parseInt(foundNode.getKey()));
                cycles.addtoCycle(cycle);
            } catch (JsonMappingException e) {
                continue;
            }
        }
        return cycles;
    }
}
