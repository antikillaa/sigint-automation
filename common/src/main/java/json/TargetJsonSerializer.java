package json;

import model.TargetGroup;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class TargetJsonSerializer extends JsonSerializer<List<TargetGroup>> {
    public void serialize(List<TargetGroup> value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeArrayFieldStart("groups");
        for (TargetGroup group: value) {
            jgen.writeObjectField("id", group.getId());
        }
        jgen.writeEndArray();

    }
}
