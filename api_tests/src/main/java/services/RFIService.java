package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import app_context.properties.G4Properties;
import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import http.requests.rfi.*;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.FileAttachment;
import model.InformationRequest;
import model.rfi.RFISearchResults;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RFIService implements EntityService<InformationRequest> {

    private static RsClient rsClient = new RsClient();
    private static G4Client g4Client = new G4Client();
    private static AppContext context = AppContext.getContext();
    Logger log = Logger.getRootLogger();
    private final String sigintHost = G4Properties.getRunProperties().getApplicationURL();;


    private void readRFIFromResponse(G4Response response) {
        InformationRequest createdRFI = JsonCoverter.readEntityFromResponse(response, InformationRequest.class, "result");
        if (createdRFI != null) {
            Entities.getRFIs().addOrUpdateEntity(createdRFI);
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
        List<FileAttachment> attachments = new ArrayList<>();
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

        log.debug("Sending request to " + sigintHost + request.getURI());
        G4Response response = g4Client.post(sigintHost + request.getURI(), request.getBody(), request.getCookie());
        readRFIFromResponse(response);
        return response.getStatus();
    }

    public int remove(InformationRequest entity) {
        RFIDeleteRequest deleteRequest = new RFIDeleteRequest(entity.getId());
        G4Response response = g4Client.delete(sigintHost + deleteRequest.getURI(), deleteRequest.getCookie());
        if (response.getStatus() == 200) {
            try {
                Entities.getRFIs().removeEntity(entity);
            } catch (NullReturnException e) {
                log.warn("Was unable to remove entity with id " + entity.getId() + " as it" +
                        "doesn't in the list");
            }
        }
        return response.getStatus();
    }

    public EntityList<InformationRequest> list(SearchFilter filter) {
        RFISearchRequest request = new RFISearchRequest();
        G4Response response = g4Client.post(sigintHost + request.getURI(), filter, request.getCookie());
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
        G4Response response = g4Client.get(sigintHost + request.getURI(), request.getCookie());
        return JsonCoverter.fromJsonToObject(response.getMessage(), RFIDetailsResponse.class).getResult();
    }

    public int cancel(InformationRequest entity) {
        RFICancelRequest request = new RFICancelRequest(entity.getId());
        G4Response response = g4Client.post(sigintHost + request.getURI(), request.getCookie());
        readRFIFromResponse(response);
        return response.getStatus();
    }

    public int assign(InformationRequest entity) {
        RFIAssignRequest request = new RFIAssignRequest(entity.getId());
        G4Response response = g4Client.post(sigintHost + request.getURI(), request.getCookie());
        readRFIFromResponse(response);
        return response.getStatus();
    }

}
