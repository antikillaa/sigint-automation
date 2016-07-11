package json;

import abs.EntityList;
import errors.NullReturnException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dm on 3/24/16.
 */
public class JsonCoverter {

    private static Logger log = Logger.getRootLogger();
    public static ObjectMapper mapper = new ObjectMapper();

    public static <T> T readEntityFromResponse(Response response, Class<T> entityClass, String id) {
        String jsonString = response.readEntity(String.class);
        log.debug("Response: " + jsonString);

        if (response.getStatus() < 200 || response.getStatus() >= 300) {
            log.warn("Entity was not found in json due to error in response");
            return null;
        }
        T entity;
        MapType mapType = JsonCoverter.constructMapTypeToValue(entityClass);
        try {
            HashMap<String, T> map = mapper.readValue(jsonString, mapType);
            entity = map.get(id);
        } catch (java.io.IOException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }
        return entity;
    }


    public static MapType constructMapTypeToValue( Class<?> valueClass){
        TypeFactory typeFactory = mapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, valueClass);
        return mapType;
    }


    public static String toJsonString(Object object) throws NullReturnException {
        log.debug("Converting to json string Object "+ object);
        try {
            return mapper.writeValueAsString(object);

        } catch (IOException e) {
            log.error("Error occurred when trying to convert object with class"
                    +object.getClass()+"to JSON string");
            e.printStackTrace();
        }
        throw new NullReturnException("Error converting json string to object");
    }

    public static <T> T fromJsonToObject(String jsonString, Class<T> userClass)
             {
        log.debug("Converting json string:"+jsonString+" to user class:"+userClass);
        try {
            return mapper.readValue(jsonString, userClass);
        } catch (IOException e) {
            log.error("Error when converting json string:"+jsonString+" to object:"+userClass);
            e.printStackTrace();
        }
        throw new AssertionError("Error converting json string to object");
    }

    public  static <T >List<T> fromJsonToObjectsList(String jsonString, Class<T[]> userClass)
            throws NullReturnException {
        log.debug("Converting json:"+jsonString+" to object list:"+userClass);
        try {
            return Arrays.asList(mapper.readValue(jsonString, userClass));
        } catch (IOException e) {
            log.error("Error occurred when converting json:"+jsonString+" to object list:"+userClass);
            e.printStackTrace();
        }
        throw new NullReturnException("Error when converting json string to user class");
    }

    public  static <T extends EntityList>T fromJsonToObjectsList(InputStream stream, Class<T> userClass)
            throws NullReturnException {
        log.debug("Converting from Input stream to user:" + userClass);
        try {
            return mapper.readValue(stream, userClass);
        } catch (IOException e) {
            log.error("Error occurred when converting from Input stream to user:" + userClass);
            e.printStackTrace();

        }
        throw new NullReturnException("Error occurred when converting to user class:"+userClass);
    }

}
