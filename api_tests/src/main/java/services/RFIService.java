package services;

import errors.NullReturnException;
import http.requests.rfi.*;
import model.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.type.MapType;
import rs.client.JsonCoverter;
import rs.client.RsClient;
import service.EntityService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by dm on 3/31/16.
 */
public class RFIService implements EntityService<InformationRequest> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    Logger log = Logger.getRootLogger();

    public Response addNew(InformationRequest entity) {
        RFIUploadRequest request = new RFIUploadRequest();
        try {
            log.debug("Writing InformationRequest to json file...");
            File file = File.createTempFile("blob", ".json");
            JsonCoverter.mapper.writeValue(file, entity);
            request.addBodyFile("json", file, MediaType.APPLICATION_JSON_TYPE);
            file.deleteOnExit();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AssertionError("Failed to create json file");
        }
        for (FileAttachment attachment: entity.getFileAttachments()) {
            log.debug("Attaching files to request");
            request.addBodyFile(attachment.getName(), attachment.getFile(),
                    MediaType.APPLICATION_OCTET_STREAM_TYPE);
            attachment.getFile().deleteOnExit();
        }
        Entity payload = Entity.entity(request.getBody(), request.getMediaType());
        log.debug("Sending request to " + context.getHost()+ request.getURI());
        Response response = rsClient.client().target(context.getHost()+ request.getURI())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(request.getCookie())
                .post(payload);
        return response;
    }

    public Response remove(InformationRequest entity) {
        RFIDeleteRequest deleteRequest = new RFIDeleteRequest(entity.getId());
        Response response = rsClient.delete(context.getHost()+ deleteRequest.getURI(), deleteRequest.getCookie());
        return response;


    }

    public EntityList<InformationRequest> list(SearchFilter filter) {
        RFISearchRequest request = new RFISearchRequest();
        String json;
        try {
            json = JsonCoverter.toJsonString(filter);
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("JSON string wasn't generated");
        }
        Response response = rsClient.post(context.getHost()+request.getURI(), json, request.getCookie());
        String responseJson = response.readEntity(String.class);
        MapType mapType = JsonCoverter.constructMapTypeToValue(RFISearchResults.class);
        try {
            HashMap<String, RFISearchResults> searchResults = JsonCoverter.mapper.readValue(responseJson, mapType);
            return new EntityList<InformationRequest>(searchResults.get("result").getContent()) {
                public InformationRequest getEntity(String param) throws NullReturnException {
                    return null;
                }
            };
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AssertionError("Unable to read search results from RFI search");
        }

    }

    public void update(InformationRequest entity) {

    }

    public InformationRequest view(String id) {
        log.info("Getting details of RFI by id:"+id);
        RFIDetailsRequest request = new RFIDetailsRequest(id);
        Response response = rsClient.get(context.getHost()+ request.getURI(), request.getCookie());
        String responseJson = response.readEntity(String.class);
        try {
            InformationRequest RFI = JsonCoverter.fromJsonToObject(responseJson, RFIDetailsResponse.class).getResult();
            return RFI;
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
