package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import errors.NullReturnException;
import http.G4HttpClient;
import http.G4Response;
import http.requests.rfi.*;
import json.JsonCoverter;
import model.InformationRequest;
import model.rfi.RFISearchResults;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

public class RFIService implements EntityService<InformationRequest> {

    private Logger log = Logger.getLogger(RFIService.class);
    private static G4HttpClient g4HttpClient = new G4HttpClient();


    private void readRFIFromResponse(G4Response response) {
        InformationRequest createdRFI = JsonCoverter.readEntityFromResponse(response, InformationRequest.class, "result");
        if (createdRFI != null) {
            Entities.getRFIs().addOrUpdateEntity(createdRFI);
        }
    }

    public int add(InformationRequest entity) {
        RFIUploadRequest request = new RFIUploadRequest(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        readRFIFromResponse(response);
        return response.getStatus();
    }

    public int remove(InformationRequest entity) {
        RFIDeleteRequest deleteRequest = new RFIDeleteRequest(entity.getId());
        G4Response response = g4HttpClient.sendRequest(deleteRequest);

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
        RFISearchRequest request = new RFISearchRequest(filter);
        G4Response response = g4HttpClient.sendRequest(request);

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
        G4Response response = g4HttpClient.sendRequest(request);
        return JsonCoverter.fromJsonToObject(response.getMessage(), RFIDetailsResponse.class).getResult();
    }

    public int cancel(InformationRequest entity) {
        RFICancelRequest request = new RFICancelRequest(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);
        readRFIFromResponse(response);
        return response.getStatus();
    }

    public int assign(InformationRequest entity) {
        RFIAssignRequest request = new RFIAssignRequest(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);
        readRFIFromResponse(response);
        return response.getStatus();
    }

}
