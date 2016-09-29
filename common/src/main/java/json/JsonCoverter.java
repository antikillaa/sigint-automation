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
import java.util.Map;

public class JsonCoverter {

    private static Logger log = Logger.getRootLogger();
    public static ObjectMapper mapper = new ObjectMapper();

    public static <T> T readEntityFromResponse(Response response, Class<T> entityClass, String id) {
        String jsonString = readJsonStringFromResponse(response);
        T entity;
        MapType mapType = JsonCoverter.constructMapTypeToValue(entityClass);
        try {
            HashMap<String, T> map = mapper.readValue(jsonString, mapType);
            entity = map.get(id);
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
            throw new AssertionError("Unable to parse entity from response");
        }
        return entity;
    }
    
    public static<T>T readEntityFromResponse(Response response, Class<T> entityClass) {
        String jsonString = readJsonStringFromResponse(response);
        return fromJsonToObject(jsonString, entityClass);
    }
    
    private static String readJsonStringFromResponse(Response response) {
        String jsonString = response.readEntity(String.class);
        log.debug("Response: " + jsonString);

        if (response.getStatus() < 200 || response.getStatus() >= 300) {
            log.warn("Entity was not found in json due to error in response, status code: " + response.getStatus());
            return null;
        }
        return jsonString;
    }
    
    public static Map<String,String > loadJsonToStringMap(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream entityList = classloader.getResourceAsStream(filename);
        if (entityList == null) {
            log.warn("Unable to get list of entities from file:" + filename);
            return null;
        }
        MapType mapType = JsonCoverter.constructMapTypeToValue(String.class);
        try {
            return JsonCoverter.mapper.readValue(entityList, mapType);
            
        } catch (IOException e) {
            log.warn("Cannot load list of entities");
            return null;
        }
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
        } catch (IOException | NullPointerException e) {
            log.error("Error occurred when trying to convert object with class"
                    +object.getClass()+"to JSON string");
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJsonToObject(String jsonString, Class<T> userClass) {
        log.debug("Converting json string:"+jsonString+" to user class:"+userClass);
        try {
            return mapper.readValue(jsonString, userClass);
        } catch (IOException | NullPointerException e) {
            log.error("Error when converting json string:"+jsonString+" to object:"+userClass);
            log.trace(e.getMessage(), e);
        }
        return null;
    }

    public  static <T >List<T> fromJsonToObjectsList(String jsonString, Class<T[]> userClass)
            throws NullReturnException {
        log.debug("Converting json:"+jsonString+" to object list:"+userClass);
        try {
            return Arrays.asList(mapper.readValue(jsonString, userClass));
        } catch (IOException | NullPointerException e) {
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
        } catch (IOException | NullPointerException e) {
            log.error("Error occurred when converting from Input stream to user:" + userClass);
            e.printStackTrace();

        }
        throw new NullReturnException("Error occurred when converting to user class:"+userClass);
    }

}
