package http;

import abs.EntityList;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConverter {

    private static Logger log = Logger.getRootLogger();
    public static ObjectMapper mapper = new ObjectMapper();

    public static <T> T readEntityFromResponse(G4Response response, Class<T> entityClass, String id) {
        String message = response.getMessage();
        log.debug("Response: " + message);
        T entity;
        MapType mapType = JsonConverter.constructMapTypeToValue(entityClass);
        try {
            HashMap<String, T> map = mapper.readValue(message, mapType);
            entity = map.get(id);
            return entity;
        } catch (IOException | NullPointerException e) {
            String error = "Unable to parse entity from response: " + message + ", status: " + response.getCode();
            log.error(error);
            log.trace(e.getMessage(), e);
            throw new Error(error);
        }
    }
    
    public static <T> List<T> readEntitiesFromResponse(G4Response response, Class<T[]> userClass) {
        String jsonString = response.getMessage();
        return fromJsonToObjectsList(jsonString, userClass);
    }

    public static <T> T readEntityFromResponse(G4Response response, Class<T> entityClass) {
        String message = response.getMessage();
        log.debug("Response: " + message);
        return fromJsonToObject(message, entityClass);
    }

    public static Map<String, String> loadJsonToStringMap(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        InputStream entityList = classloader.getResourceAsStream(filename);
        if (entityList == null) {
            String error = "Unable to get list of entities from file:" + filename;
            log.error(error);
            throw new Error(error);
        }

        MapType mapType = JsonConverter.constructMapTypeToValue(String.class);
        try {
            return JsonConverter.mapper.readValue(entityList, mapType);
        } catch (IOException e) {
            String error = "Cannot load list of entities";
            log.error(error);
            log.trace(e.getMessage(), e);
            throw new Error(error);
        }
    }

    private static MapType constructMapTypeToValue(Class<?> valueClass) {
        TypeFactory typeFactory = mapper.getTypeFactory();
        return typeFactory.constructMapType(HashMap.class, String.class, valueClass);
    }

    public static String toJsonString(Object object) {
        log.debug("Converting to json string Object " + object);
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException | NullPointerException e) {
            String error = "Error occurred when trying to convert object with class:"
                    + object.getClass() + " to JSON string";
            log.error(error);
            log.trace(e.getMessage(), e);
            throw new Error(error, e);
        }
    }

    public static <T> T fromJsonToObject(String jsonString, Class<T> userClass) {
        log.debug("Converting json string: " + jsonString + " to user class: " + userClass);
        try {
            return mapper.readValue(jsonString, userClass);
        } catch (IOException | NullPointerException e) {
            String error = "Error when converting json string:" + jsonString + " to object:" + userClass;
            log.error(error);
            log.error(e.getMessage());
            log.trace(e.getMessage(), e);
            throw new RuntimeException(error);
        }
    }

    private static <T> List<T> fromJsonToObjectsList(String jsonString, Class<T[]> userClass) {
        log.debug("Converting json: " + jsonString + " to object list: " + userClass);
        try {
            return Arrays.asList(mapper.readValue(jsonString, userClass));
        } catch (IOException | NullPointerException e) {
            String error = "Error occurred when converting json: " + jsonString + " to object list: " + userClass;
            log.error(error);
            log.trace(e.getMessage(), e);
            throw new RuntimeException(error);
        }
    }

    public static <T extends EntityList> T fromJsonToObjectsList(InputStream stream, Class<T> userClass) {
        log.debug("Converting from Input stream to user:" + userClass);
        try {
            return mapper.readValue(stream, userClass);
        } catch (IOException | NullPointerException e) {
            String error = "Error occurred when converting from Input stream to user: " + userClass;
            log.error(error);
            log.trace(e.getMessage(), e);
            throw new Error(error);
        }
    }

}
