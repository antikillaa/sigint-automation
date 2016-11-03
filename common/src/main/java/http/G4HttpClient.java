package http;

import app_context.properties.G4Properties;
import error_reporter.ErrorReporter;
import http.requests.HttpRequest;
import json.JsonConverter;
import org.apache.log4j.Logger;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import utils.DateHelper;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
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

    private static final String TRUSTORE_CLIENT_FILE = "truststore_client";
    private static final String TRUSTSTORE_CLIENT_PWD = "123456";
    private static final String KEYSTORE_CLIENT_FILE = "keystore_client";
    private static final String KEYSTORE_CLIENT_PWD = "123456";

    private Protocol protocol;

    /**
     * G4HttpClient, by default use http client
     */
    public G4HttpClient() {
        http();
    }

    private enum Protocol {
        HTTP, HTTPS
    }

    public G4HttpClient http() {
        protocol = Protocol.HTTP;
        return this;
    }

    public G4HttpClient https() {
        protocol = Protocol.HTTPS;
        return this;
    }

    private Client buildClient() {
        switch (protocol) {
            case HTTP:
                return httpClient();
            case HTTPS:
                return httpsClient();
            default:
                log.warn("Http/Https protocol not defined in G4HttpClient. Use http client");
                return httpClient();
        }
    }

    private Client httpClient() {
        return ClientBuilder.newClient();
    }

    private Client httpsClient() {
        ClientConfig clientConfig = new ClientConfig().connectorProvider(new HttpUrlConnectorProvider());

        SslConfigurator sslConfig = SslConfigurator.newInstance()
                .trustStoreFile(TRUSTORE_CLIENT_FILE)
                .trustStorePassword(TRUSTSTORE_CLIENT_PWD)
                .keyStoreFile(KEYSTORE_CLIENT_FILE)
                .keyPassword(KEYSTORE_CLIENT_PWD);

        final SSLContext sslContext = sslConfig.createSSLContext();
        return ClientBuilder.newBuilder()
                .withConfig(clientConfig)
                .sslContext(sslContext)
                .build();
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

        Builder builder = buildClient()
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

        return buildClient()
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
            payload = Entity.json(JsonConverter.toJsonString(object));
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
        return sendRequest(builder, request);
    }

    /**
     * Send an GET/PUT/POST/DELETE http request with basic http authentication.
     *
     * @param request HttpRequest
     * @return G4Response with message string and http status code
     */
    public G4Response sendRequest(HttpRequest request, String username, String password) {
        Builder builder = buildRequest(request, username, password);
        return sendRequest(builder, request);
    }


    /**
     * Internal method to invoke passed request within the given time frame.
     * If 503 error occurred (usually due to server restart), request will be
     * sent again after waitTime unless try counter reaches maxTryCount.
     *
     * @param builder {@link Builder} instance with set options.
     * @param request {@link HttpRequest} instance.
     * @return {@link G4Response} formed from request
     */
    private G4Response sendRequest(Builder builder, HttpRequest request) {
        Entity payload = convertToEntity(request.getPayload());
        Invocation invocation;
        int tryCount = 0;
        Response response;
        Date timeoutDate = DateHelper.getDateWithShift(requestTimeout);
        do {
            invocation = buildInvocation(request.getHttpMethod(), payload, builder);
            tryCount++;
            response = invocation.invoke();
            if (response.getStatus() == 503) {
                DateHelper.waitTime(waitTime);
            }

        } while ((response.getStatus() == 503) && (tryCount <= maxTryCount) && (!DateHelper.isTimeout(timeoutDate)));

        return new G4Response(response.readEntity(String.class), response.getStatus());
    }


    /**
     * Internal method to build request that can be executed
     *
     * @param httpMethod {@link HttpMethod}. Based on this creates appropriate {@link Invocation}
     * @param payload    String representation of request's body.
     * @param builder    {@link Builder} instance. Used to build {@link Invocation}.
     * @return {@link Invocation} instance.
     */
    private Invocation buildInvocation(HttpMethod httpMethod, Entity payload, Builder builder) {
        Invocation invocation;
        switch (httpMethod) {
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
                ErrorReporter.raiseError("Unknown request type passed: " + httpMethod);
                invocation = null;
                break;
        }
        return invocation;

    }

}
