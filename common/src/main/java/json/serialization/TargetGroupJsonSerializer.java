package json.serialization;

import model.Target;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class TargetGroupJsonSerializer extends JsonSerializer<List<Target>> {
    @Override
    public void serialize(List<Target> value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartArray();
        for (Target target:value){
            jgen.writeString(target.getId());
        }
        jgen.writeEndArray();

    }
}
