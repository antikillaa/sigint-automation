package services;

import errors.NullReturnException;
import http.G4Response;
import http.OperationResult;
import http.requests.HttpRequest;
import http.requests.ProfileDraftRequest;
import http.requests.ProfileRequest;
import model.*;
import model.entities.Entities;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static json.JsonConverter.toJsonString;

public class ProfileService implements EntityService<Profile> {

    private static final ProfileRequest request = new ProfileRequest();
    private static final Logger log = Logger.getLogger(ProfileService.class);

    /* TODO
        GET /profiles getProfiles
        GET /profiles/{profileId}/activity getActivity
        GET /profiles/{profileId}/audit getProfileAudit
        POST /profiles/namesAndGroups getTargetNamesAndGroups
        POST /profiles/profilesByGroups getProfilesInGroups
        GET /profiles/{profileId}/aggregations getTargetAggregations
        GET /profiles/{profileId}/audit getProfileAudit
        GET /profiles/{profileId}/communication/eventstream getCommunicationStream
        GET /profiles/{profileId}/eventLocations getTargetEventLocations
        GET /profiles/{profileId}/geotagged/eventstream getGeoTaggedEventStream
        POST /profiles/{profileId}/historicalData queryHistoricalData
        GET /profiles/{profileId}/mentions getTargetMentions
        GET /profiles/{profileId}/network getProfileNetwork
        GET /profiles/{profileId}/readable isReadable
        GET /profiles/{profileId}/threatScore threatScore
        GET /profiles/{profileId}/voice getVoiceModel
        GET /profiles/{profileId}/voice/events getVoiceEvents
     */

    @Override
    public OperationResult<?> add(Profile entity) {
        return null;
    }

    @Override
    public OperationResult remove(Profile entity) {
        log.info("Deleting profile id: " + entity.getId());

        HttpRequest request = Objects.equals(entity.getJsonType(), ProfileJsonType.Draft) ?
                new ProfileDraftRequest().delete(entity.getId()) :
                new ProfileRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);

        OperationResult operationResult = new OperationResult<>(response, RequestResult.class);
        if (operationResult.isSuccess()) {
            Entities.getProfiles().removeEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<List<Profile>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Profile>> list() {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Profile> update(Profile entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Profile> view(String id) {
        log.info("Getting profile details id: " + id);
        G4Response response = g4HttpClient.sendRequest(request.view(id));

        OperationResult<Profile> operationResult = new OperationResult<>(response, Profile.class, "data");
        if (operationResult.isSuccess()) {
            Entities.getProfiles().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    public OperationResult<Profile> merge(Profile firstProfile, Profile secondProfile) {
        log.info("Merge two profile into one");
        log.info("Create merged profile draft");

        G4Response response = g4HttpClient.sendRequest(request.merge(firstProfile.getId(), secondProfile.getId()));

        OperationResult<Profile> operationResult = new OperationResult<>(response, Profile.class, "data");
        if (operationResult.isSuccess()) {
            Entities.getProfiles().addOrUpdateEntity(operationResult.getEntity());
        }

        return operationResult;
    }

    public OperationResult<List<IdentifierSummary>> getIdentifierAggregations(String profileId) {
        log.info("GET /api/profiler/profiles/" + profileId + "/identifierAggregations");
        G4Response response = g4HttpClient.sendRequest(request.identifiersSummary(profileId));

        OperationResult<IdentifierSummary[]> operationResult = new OperationResult<>(response, IdentifierSummary[].class, "data");
        if (operationResult.isSuccess()) {
            try {
                Entities.getProfiles().getEntity(profileId).setIdentifiersSummary(new ArrayList<>(Arrays.asList(operationResult.getEntity())));
            } catch (NullReturnException e) {
                log.error(e);
            }
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            throw new AssertionError("GET /api/profiler/profiles/" + profileId + "/identifierAggregations return:\n" + toJsonString(response));
        }
    }

    public Profile getByName(String name) {
        for (Profile entity : Entities.getProfiles()) {
            if (entity.getName().equals(name)) {
                return entity;
            }
        }

        log.error("Can't find profile by name:" + name);
        throw new AssertionError("Can't find profile by name:" + name);
    }

    public OperationResult<Profile> summary(String id) {
        log.info("Getting profile summary, id:" + id);
        G4Response response = g4HttpClient.sendRequest(request.summary(id));

        OperationResult<Profile> result = new OperationResult<>(response, Profile.class, "data");
        if (result.isSuccess()) {
            Entities.getProfiles().addOrUpdateEntity(result.getEntity());
        }
        return result;
    }

    public OperationResult<Profile> getOrCreateDraft(String id) {
        log.info("GetOrCreateDraft profile, id:" + id);
        G4Response response = g4HttpClient.sendRequest(request.getOrCreateDraft(id));

        OperationResult<Profile> result = new OperationResult<>(response, Profile.class, "data");
        if (result.isSuccess()) {
            Entities.getProfiles().addOrUpdateEntity(result.getEntity());
        }
        return result;
    }
}
