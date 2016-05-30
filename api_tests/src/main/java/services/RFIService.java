package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.rfi.*;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.FileAttachment;
import model.InformationRequest;
import model.RFISearchResults;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import service.EntityService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RFIService implements EntityService<InformationRequest> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    Logger log = Logger.getRootLogger();
    private final String sigintHost = context.environment().getSigintHost();


    private void readRFIFromResponse(Response response) {
        InformationRequest createdRFI = JsonCoverter.readEntityFromResponse(response, InformationRequest.class, "result");
        if (createdRFI != null) {
            context.entities().getRFIs().addOrUpdateEntity(createdRFI);
        }
    }

    public int add(InformationRequest entity) {
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
        List<FileAttachment> attachments = new ArrayList<FileAttachment>();
        if (entity.getApprovedCopy() != null) {
            attachments.add(entity.getApprovedCopy());

        }
        if (entity.getOriginalDocument() != null) {
            attachments.add(entity.getOriginalDocument());

        }
        for (FileAttachment attachment : attachments) {
            log.debug("Attaching files to request");
            request.addBodyFile(attachment.getTitle(), attachment.getFile(),
                    MediaType.APPLICATION_OCTET_STREAM_TYPE);
            attachment.getFile().deleteOnExit();
        }
        Entity payload = Entity.entity(request.getBody(), request.getMediaType());
        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.client().target(sigintHost + request.getURI())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(request.getCookie())
                .post(payload);
        readRFIFromResponse(response);
        return response.getStatus();
    }

    public int remove(InformationRequest entity) {
        RFIDeleteRequest deleteRequest = new RFIDeleteRequest(entity.getId());
        Response response = rsClient.delete(sigintHost + deleteRequest.getURI(), deleteRequest.getCookie());
        if (response.getStatus() == 200) {
            try {
                context.entities().getRFIs().removeEntity(entity);
            } catch (NullReturnException e) {
                log.warn("Was unable to remove entity with id " + entity.getId() + " as it" +
                        "doesn't in the list");
            }
        }
        return response.getStatus();
    }

    public EntityList<InformationRequest> list(SearchFilter filter) {
        RFISearchRequest request = new RFISearchRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());
        RFISearchResults searchResults = JsonCoverter.readEntityFromResponse(response, RFISearchResults.class, "result");
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from RFI search");
        } else {
            return new EntityList<InformationRequest>(searchResults.getContent()) {
                public InformationRequest getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        }
    }

    public int update(InformationRequest entity) {
        return add(entity);
    }

    public InformationRequest view(String id) {
        log.info("Getting details of RFI by id:" + id);
        RFIDetailsRequest request = new RFIDetailsRequest(id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());
        String responseJson = response.readEntity(String.class);
        InformationRequest RFI = JsonCoverter.fromJsonToObject(responseJson, RFIDetailsResponse.class).getResult();
        return RFI;
    }

    public int cancel(InformationRequest entity) {
        RFICancelRequest request = new RFICancelRequest(entity.getId());
        Response response = rsClient.post(sigintHost + request.getURI(), null, request.getCookie());
        readRFIFromResponse(response);
        return response.getStatus();
    }

    public int assign(InformationRequest entity) {
        RFIAssignRequest request = new RFIAssignRequest(entity.getId());
        Response response = rsClient.post(sigintHost + request.getURI(), null, request.getCookie());
        readRFIFromResponse(response);
        return response.getStatus();
    }

}
