package services;

import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import json.JsonConverter;
import http.OperationResult;
import http.requests.ProfileDraftRequest;
import model.Profile;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class ProfileDraftService implements EntityService<Profile> {

    private static final ProfileDraftRequest request = new ProfileDraftRequest();
    private static final Logger log = Logger.getLogger(ProfileDraftService.class);

    // TODO POST /profiles/{profileId}/draft getOrCreateDraft

    @Override
    public OperationResult<Profile> add(Profile entity) {
        log.info("Creating new ProfileDraft: " + JsonConverter.toJsonString(entity));
        G4Response response = g4HttpClient.sendRequest(request.create(entity));

        OperationResult<Profile> operationResult = new OperationResult<>(response, Profile.class, "data");
        if (operationResult.isSuccess()) {
            Entities.getProfiles().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    @Override
    public OperationResult remove(Profile entity) {
        log.info("Deleting profile id:" + entity.getId() + " name:" + entity.getName());
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

        OperationResult operationResult = new OperationResult(response);
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
        log.info("Get all profiles");
        G4Response response = g4HttpClient.sendRequest(request.list());

        OperationResult<Profile[]> operationResult = new OperationResult<>(response, Profile[].class, "data");
        if (operationResult.isSuccess()) {
            Profile[] profiles = operationResult.getEntity();
            return new OperationResult<>(response, Arrays.asList(profiles));
        } else {
            throw new AssertionError("Unable to get list of profile drafts!");
        }
    }

    @Override
    public OperationResult<Profile> update(Profile entity) {
        log.info("Updating profile");
        G4Response response = g4HttpClient.sendRequest(request.update(entity));

        OperationResult<Profile> operationResult = new OperationResult<>(response, Profile.class, "data");
        if (operationResult.isSuccess()) {
            Entities.getProfiles().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    @Override
    public OperationResult<Profile> view(String id) {
        log.info("Getting profile details, id:" + id);
        G4Response response = g4HttpClient.sendRequest(request.view(id));

        OperationResult<Profile> operationResult = new OperationResult<>(response, Profile.class, "data");
        if (operationResult.isSuccess()) {
            Entities.getProfiles().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    public OperationResult<Profile> publish(Profile profile) {
        log.info("Publishing profile id: " + profile.getId() + ", name: " + profile.getName());
        G4Response response = g4HttpClient.sendRequest(request.publish(profile));

        OperationResult<Profile> operationResult = new OperationResult<>(response, Profile.class, "data");

        if (operationResult.isSuccess()) {
            // if it's merging then remove two source profiles
            if (profile.getMergingProfilesIDs() != null && !profile.getMergingProfilesIDs().isEmpty()) {
                Entities.getProfiles().removeEntity(profile.getMergingProfilesIDs().get(0));
                Entities.getProfiles().removeEntity(profile.getMergingProfilesIDs().get(1));
            }

            Entities.getProfiles().addOrUpdateEntity(operationResult.getEntity());
            Entities.getProfiles().removeEntity(profile);
        }
        return operationResult;
    }
}
