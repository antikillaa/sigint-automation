package services;

import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.OperationResult;
import http.requests.ProfileRequest;
import model.Profile;
import model.RequestResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.List;

public class ProfileService implements EntityService<Profile> {

    private static final ProfileRequest request = new ProfileRequest();
    private static final Logger log = Logger.getLogger(ProfileService.class);

    /* TODO
        GET /profiles getProfiles
        GET /profiles/{profileId}/activity getActivity
        GET /profiles/{profileId}/audit getProfileAudit

        POST /profiles/{profileId}/draft getOrCreateDraft
     */

    @Override
    public OperationResult<?> add(Profile entity) {
        return null;
    }

    @Override
    public OperationResult remove(Profile entity) {
        log.info("Deleting profile id: " + entity.getId());
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

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

}
