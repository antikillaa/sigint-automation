package rs.client;

import errors.NullReturnException;
import model.EntityList;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;

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
            throws NullReturnException {
        log.debug("Converting json string:"+jsonString+" to user class:"+userClass);
        try {
            return mapper.readValue(jsonString, userClass);
        } catch (IOException e) {
            log.error("Error when converting json string:"+jsonString+" to object:"+userClass);
            e.printStackTrace();
        }
        throw new NullReturnException("Error converting json string to object");
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
