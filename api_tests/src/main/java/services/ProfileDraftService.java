package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.ProfileDraftRequest;
import model.Profile;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

public class ProfileDraftService implements EntityService<Profile> {

    private static final ProfileDraftRequest request = new ProfileDraftRequest();
    private static final Logger log = Logger.getLogger(TargetService.class);

    @Override
    public OperationResult<Profile> add(Profile entity) {
        log.info("Creating new ProfileDraft: " + JsonConverter.toJsonString(entity));
        G4Response response = g4HttpClient.sendRequest(request.create(entity));

        Profile profile = JsonConverter.readEntityFromResponse(response, Profile.class, "data");

        OperationResult<Profile> operationResult = new OperationResult<>(response, profile);
        if (operationResult.isSuccess()) {
            Entities.getProfiles().addOrUpdateEntity(operationResult.getResult());
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
    public OperationResult<EntityList<Profile>> list(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Profile> update(Profile entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Profile> view(String id) {
        throw new NotImplementedException();
    }
}
