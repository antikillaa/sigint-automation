package services;

import errors.OperationResultError;
import http.G4Response;
import http.OperationResult;
import http.requests.ProfileDraftRequest;
import model.*;
import model.entities.Entities;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static json.JsonConverter.toJsonString;

public class ProfileDraftService implements EntityService<Profile> {

    private static final ProfileDraftRequest request = new ProfileDraftRequest();
    private static final Logger log = Logger.getLogger(ProfileDraftService.class);


    @Override
    public OperationResult<Profile> add(Profile entity) {
        log.info("Creating new ProfileDraft: " + toJsonString(entity));
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
        log.info("code:" + response.getCode() + ", message:" + response.getMessage());

        OperationResult<Profile[]> operationResult = new OperationResult<>(response, Profile[].class, "data");
        if (operationResult.isSuccess()) {
            Profile[] profiles = operationResult.getEntity();
            return new OperationResult<>(response, Arrays.asList(profiles));
        } else {
            log.error("Unable to get list of profile drafts!");
            throw new OperationResultError(operationResult);
        }
    }

    @Override
    public OperationResult<Profile> update(Profile entity) {
        log.info("Updating profile id:" + entity.getId() + " profile:" + toJsonString(entity));
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

    public OperationResult<FileMetaData> uploadImage(Profile profile, File file) {
        log.info("Uploading image to profile id: " + profile.getId() + ", name: " + profile.getName());
        G4Response response = g4HttpClient.sendRequest(request.imageUpload(profile.getId(), file));

        OperationResult<FileMetaData> result = new OperationResult<>(response, FileMetaData.class, "data");
        if (result.isSuccess()) {
            return result;
        } else {
            throw new OperationResultError(result);
        }
    }

    public OperationResult<List<CBEntity>> getVoiceEvents(Profile profile) {
        log.info("Get voice events for profileId: " + profile.getId() + " name:" + profile.getName());
        G4Response response = g4HttpClient.sendRequest(request.voiceEvents(profile.getId()));
        log.info(response.getCode() + " " + response.getMessage());

        OperationResult<CBEntity[]> result = new OperationResult<>(response, CBEntity[].class, "data");
        if (result.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(result.getEntity()));
        } else {
            throw new OperationResultError(result);
        }
    }

    public OperationResult<Voice> getVoiceModel(String profileId) {
        log.info("Get voice print for profileId: " + profileId);
        G4Response response = g4HttpClient.sendRequest(request.voice(profileId));

        OperationResult<Voice> result = new OperationResult<>(response, Voice.class, "data");
        if (result.isSuccess()) {
            return result;
        } else {
            throw new OperationResultError(result);
        }
    }

    public OperationResult<Voice> createVoiceModel(Profile profile, Voice voice) {
        log.info("Create VoiceID for profile id:" + profile.getId() + " name: " + profile.getName() + ", payload:" + toJsonString(voice));
        G4Response response = g4HttpClient.sendRequest(request.createVoice(profile.getId(), voice));

        OperationResult<Voice> result = new OperationResult<>(response, Voice.class, "data");
        if (result.isSuccess()) {
            profile.setVoiceModelId(result.getEntity().getId());
            return result;
        } else {
            throw new OperationResultError(result);
        }
    }

    public OperationResult<VoiceFile> uploadAudioFile(Profile profile, File file) {
        log.info("Create voice print for profile id:" + profile.getId() + ", name:" + profile.getName());
        log.info("Uploading file:" + file.getAbsolutePath());
        G4Response response = g4HttpClient.sendRequest(request.uploadAudioFile(profile.getId(), file));
        log.info(response.getCode() + " " + response.getMessage());

        OperationResult<VoiceFile> result = new OperationResult<>(response, VoiceFile.class, "data");
        if (result.isSuccess()) {
            profile.getVoiceFiles().add(result.getEntity());
            return result;
        } else {
            throw new OperationResultError(result);
        }
    }
}
