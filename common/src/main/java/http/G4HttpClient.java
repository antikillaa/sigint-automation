package http;

import app_context.properties.G4Properties;
import error_reporter.ErrorReporter;
import errors.NullReturnException;
import http.requests.HttpRequest;
import json.JsonCoverter;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import utils.DateHelper;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD;
import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME;

public class G4HttpClient {

    private static Logger log = Logger.getLogger(G4HttpClient.class);
    private String host = G4Properties.getRunProperties().getApplicationURL();
    private final int requestTimeout = 30;
    private final int waitTime = 15;
    private final int maxTryCount = 3;

    /**
     * G4HttpClient.
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

        Cookie cookie = request.getCookie();
        if (request.getCookie() != null) {
            builder.cookie(cookie);
            log.debug("Cookie: " + cookie.getName() + "=" + cookie.getValue());
        } else {
            log.debug("Without authentication.");
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
        log.debug("http basic authentication, username: " + username + " password: " + password);

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
     * Internal method to invoke passed request within the given time frame.
     * If 503 error occurred (usually due to server restart), request will be
     * sent again after waitTime unless try counter reaches maxTryCount.
     *
     * @param invocation request that should be invoked.
     * @return {@link G4Response} formed from request
     */
    private G4Response invokeRequest(Invocation invocation) {
        int tryCount = 0;
        Response response;
        Date timeoutDate = DateHelper.getDateWithShift(requestTimeout);
        do {
            tryCount++;
            response = invocation.invoke();
            if (response.getStatus() == 503) {
                DateHelper.waitTime(waitTime);
            }
        }
        while ((response.getStatus() == 503) && (tryCount <= maxTryCount) && (!DateHelper.isTimeout(timeoutDate)));

        return new G4Response(response);
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
        Invocation invocation;

        switch (request.getHttpMethod()) {
            case GET:
                log.debug("Sending GET request");
                invocation = builder.buildGet();
                break;
            case PUT:
                log.debug("Sending PUT request with payload: " + payload);
                invocation = builder.buildPut(payload);
                break;
            case POST:
                log.debug("Sending POST request with payload: " + payload);
                invocation = builder.buildPost(payload);
                break;
            case DELETE:
                log.debug("Sending DELETE request");
                invocation = builder.buildDelete();
                break;
            default:
                ErrorReporter.raiseError("Unknown request type passed: " + request.getHttpMethod());
                invocation = null;
                break;
        }
        return invokeRequest(invocation);
    }

    /**
     * Send an GET/PUT/POST/DELETE http request with basic http authentication.
     *
     * @param request HttpRequest
     * @return G4Response with message string and http status code
     */
    public G4Response sendRequest(HttpRequest request, String username, String password) {

        Builder builder = buildRequest(request, username, password);
        Entity payload = convertToEntity(request.getPayload());
        Invocation invocation;

        switch (request.getHttpMethod()) {
            case GET:
                log.debug("Sending GET request");
                invocation = builder.buildGet();
                break;
            case PUT:
                log.debug("Sending PUT request with payload: " + payload);
                invocation = builder.buildPut(payload);
                break;
            case POST:
                log.debug("Sending POST request with payload: " + payload);
                invocation = builder.buildPost(payload);
                break;
            case DELETE:
                log.debug("Sending DELETE request");
                invocation = builder.buildDelete();
                break;
            default:
                ErrorReporter.raiseError("Unknown request type passed: " + request.getHttpMethod());
                invocation = null;
                break;
        }
        return invokeRequest(invocation);
    }

}
