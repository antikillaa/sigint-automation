package json;

import errors.NullReturnException;
import model.AppContext;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

public class RsClient {

    public static Logger log = Logger.getRootLogger();
    private Client client;
    private Properties connectionProperties = AppContext.getContext().getJiraConnection();

    public RsClient(){
        initClient();
    }

    public Client client() {
        return client;
    }


    /**
     * Initialization JAX-RS client with HttpAuthenticationFeature.basic(user, pass)
     * <br>user and pass it properties from jiraConnection.properties
     */
    public void initClient(){
        String user = connectionProperties.getProperty("username");
        String pass = connectionProperties.getProperty("password");
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, pass);
        client = ClientBuilder.newClient();
        client.register(feature);
        client.register(MultiPartFeature.class);
    }


    private Invocation.Builder buildRequest(String url) {
        log.debug("Building request to url:" + url);
        return client
                .target(url)
                .request(MediaType.APPLICATION_JSON_TYPE);

    }

    private Entity convertToJson(Object object) {
        Entity payload;
        try {
            payload = Entity.json(JsonCoverter.toJsonString(object));
        } catch (NullReturnException e) {
            throw new AssertionError("Cannot convert");
        }
        return payload;

    }

    /**
     * HTTP GET request
     *
     * @param url target URL
     * @return JAX-RS client response
     */
    public Response get(String url){
        log.debug("Sending get request...");
        return buildRequest(url).get();
    }

    public Response  get(String url, Cookie cookie) {
        log.debug("Sending get request");
        return buildRequest(url).cookie(cookie).get();
    }

    /**
     * HTTP PUT request
     *
     * @param url target URL
     * @return JAX-RS client response
     */
    public Response put(String url, Object object){

        Entity payload = convertToJson(object);
        log.debug("Sending PUT request with payload:"+ payload);
        return buildRequest(url).put(payload);
    }

    public Response put(String url, Object object, Cookie cookie) {
        Entity payload = convertToJson(object);
        log.debug("Sending PUT request with payload:"+ payload);
        return buildRequest(url).cookie(cookie).put(payload);
    }

    /**
     * HTTP POST request
     *
     * @param url target URL
     * @param jsonInString json for POST request
     * @return JAX-RS client response
     */
    public Response post(String url, String jsonInString){
        log.debug("Sending POST request with payload:"+jsonInString);
        Entity payload = Entity.json(jsonInString);
        return buildRequest(url).post(payload);
    }

    public Response post(String url, String jsonInString, Cookie cookie) {
        log.debug("Sending POST request with payload:"+jsonInString);
        Entity payload = Entity.json(jsonInString);
        return buildRequest(url).cookie(cookie).post(payload);


    }

    /**
     * HTTP DELETE request
     *
     * @param url target URL
     * @return JAX-RS client response
     */
    public Response delete(String url){
        log.debug("Sending Delete request");
        return buildRequest(url).delete();
    }

    public Response delete(String url, Cookie cookie) {
        log.debug("Sending Delete request");
        return buildRequest(url).cookie(cookie).delete();

    }


}
