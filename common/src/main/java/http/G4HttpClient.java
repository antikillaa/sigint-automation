package http;

import app_context.properties.G4Properties;
import errors.NullReturnException;
import http.requests.HttpRequest;
import json.JsonCoverter;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD;
import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME;

public class G4HttpClient {

    private static Logger log = Logger.getLogger(G4HttpClient.class);
    private String host = G4Properties.getRunProperties().getApplicationURL();

    /**
     * G4HttpClient client.
     */
    public G4HttpClient() {
    }

    /**
     * Set host for API requests.
     * By Default used 'sigintURL' from general.properties file.
     *
     * @param host host for API requests
     * @return G4HttpClient instance
     */
    public G4HttpClient setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Build request with initialization: URL, MediaType, [Cookie]
     *
     * @param request HTTP request model
     * @return org.glassfish.jersey.client Builder
     */
    private Builder buildRequest(HttpRequest request) {
        String URL = host + request.getURI();
        log.debug("Building request to url:" + URL);

        Builder builder = ClientBuilder.newClient()
                .register(MultiPartFeature.class)
                .target(URL)
                .request(request.getMediaType());

        if (request.getCookie() != null) {
            builder.cookie(request.getCookie());
        }
        return builder;
    }

    /**
     * Build request with initialization: URL, MediaType and Username/Password for http basic authentication
     *
     * @param request HTTP request model
     * @return org.glassfish.jersey.client Builder
     */
    private Builder buildRequest(HttpRequest request, String username, String password) {
        String URL = host + request.getURI();
        log.debug("Building request to url:" + URL);

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        return ClientBuilder.newClient()
                .register(feature)
                .target(URL)
                .request(request.getMediaType())
                .property(HTTP_AUTHENTICATION_BASIC_USERNAME, username)
                .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, password);
    }

    /**
     * Convert object to Entity for payload.
     *
     * @param object object for payload
     * @return Entity instance for payload
     */
    private Entity convertToEntity(Object object) {

        Entity payload;
        if (object == null) {
            return null;
        }

        if (object.getClass().equals(MultiPart.class)) {
            payload = Entity.entity(object, MediaType.MULTIPART_FORM_DATA_TYPE);
        } else {
            try {
                payload = Entity.json(JsonCoverter.toJsonString(object));
            } catch (NullReturnException e) {
                throw new AssertionError("Cannot convert payload to JSON");
            }
        }

        return payload;
    }

    /**
     * Send an GET/PUT/POST/DELETE http request [with cookie authentication (optional)].
     *
     * @param request HttpRequest
     * @return G4Response with message string and http status code
     */
    public G4Response sendRequest(HttpRequest request) {

        Builder builder = buildRequest(request);
        Entity payload = convertToEntity(request.getPayload());
        Response response = null;

        switch (request.getHttpMethod()) {
            case GET:
                log.debug("Sending GET request");
                response = builder.get();
                break;
            case PUT:
                log.debug("Sending PUT request with payload: " + payload);
                response = builder.put(payload);
                break;
            case POST:
                log.debug("Sending POST request with payload: " + payload);
                response = builder.post(payload);
                break;
            case DELETE:
                log.debug("Sending DELETE request");
                response = builder.delete();
                break;
        }

        return response == null ? null : new G4Response(response);
    }

    /**
     * Send an GET http request with basic http authentication.
     *
     * @param request HttpRequest
     * @return G4Response with message string and http status code
     */
    public G4Response sendRequest(HttpRequest request, String username, String password) {

        Builder builder = buildRequest(request, username, password);
        Entity payload = convertToEntity(request.getPayload());
        Response response = null;

        switch (request.getHttpMethod()) {
            case GET:
                log.debug("Sending GET request");
                response = builder.get();
                break;
            case PUT:
                log.debug("Sending PUT request with payload: " + payload);
                response = builder.put(payload);
                break;
            case POST:
                log.debug("Sending POST request with payload: " + payload);
                response = builder.post(payload);
                break;
            case DELETE:
                log.debug("Sending DELETE request");
                response = builder.delete();
                break;
        }
        return response == null ? null : new G4Response(response);
    }

}
