package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import file_generator.FileGenerator;
import http.G4HttpClient;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.targets.TargetRequest;
import model.*;
import model.targetGroup.TargetGroupSearchResult;
import org.apache.log4j.Logger;

import java.util.List;

public class TargetService implements EntityService<Target> {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private Logger log = Logger.getLogger(TargetService.class);
    /*
        TODO
        GET /targets getTargets
        POST /targets/pageable getTargets
        POST /targets/{id}/groups updateTargetGroupsForTarget
     */

    /**
     * PUT /targets addTarget
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Target> add(Target entity) {
        log.info("Creating new target");

        TargetRequest request = new TargetRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Target target = JsonConverter.readEntityFromResponse(response, Target.class, "id");
        OperationResult<Target> operationResult = new OperationResult<>(response, target);
        if (operationResult.isSuccess()) {
            Entities.getTargets().addOrUpdateEntity(target);
        } else {
            log.error("Add new target process was failed");
        }
        return operationResult;
    }

    /**
     * POST /targets/upload importTargets, multipartUpload
     *
     * @param targets list of Targets
     * @return HTTP status code
     */
    public OperationResult<UploadResult> upload(List<Target> targets) {
        log.info("Writing Targets to file..");
        G4File file = new FileGenerator(Target.class).write(targets);

        log.info("Upload file with " + targets.size() + " targets..");
        TargetRequest request = new TargetRequest().upload(file);
        G4Response response = g4HttpClient.sendRequest(request);
        UploadResult uploadResult = JsonConverter.readEntityFromResponse(response, UploadResult.class, "result");
        return new OperationResult<>(response, uploadResult);
        
    }

    /**
     * DELETE /targets/{id} removeTarget
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult remove(Target entity) {
        log.info("Deleting target id:" + entity.getId());
        TargetRequest request = new TargetRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult operationResult = new OperationResult(response);
        if (operationResult.isSuccess()) {
            Entities.getTargets().removeEntity(entity);
        }
        
        return operationResult;
    }

    /**
     * POST /targets/search search
     *
     * @param filter search filter for payload
     * @return EntityList of Targets
     */
    @Override
    public OperationResult<EntityList<Target>> list(SearchFilter filter) {
        TargetRequest request = new TargetRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);
        TargetSearchResults searchResults = JsonConverter.readEntityFromResponse(
                response, TargetSearchResults.class, "result");
        EntityList<Target> targets;
        if (searchResults != null) {
            targets = new EntityList<>(searchResults.getContent());
        } else {
            targets = null;
                }
        return new OperationResult<>(response, targets);
    }

    /**
     * POST /targets updateTarget
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Target> update(Target entity) {
        log.info("Updating target id: " + entity.getId());
        TargetRequest request = new TargetRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        OperationResult<Target> operationResult = new OperationResult<>(response, entity);
        if (operationResult.isSuccess()) {
            Entities.getTargets().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update target process was failed");
            
        }
        return operationResult;
    }

    /**
     * GET /targets/{id}/details getTargetDetails
     *
     * @param id id of entity
     * @return Target entity
     */
    @Override
    public OperationResult<Target> view(String id) {
        log.info("View target entry id:" + id);
        TargetRequest request = new TargetRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);
        Target target = JsonConverter.readEntityFromResponse(response, Target.class, "result");
        return new OperationResult<>(response, target);
    }

    /**
     * GET /targets/{id}/groups findTargetGroups
     *
     * @param id Target id
     * @return List of Target groups
     */
    public OperationResult<EntityList<TargetGroup>> getTargetGroups(String id) {
        log.info("Get targetGroups of target id:" + id);

        TargetRequest request = new TargetRequest().findTargetGroups(id);
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroupSearchResult result = JsonConverter.readEntityFromResponse(response, TargetGroupSearchResult.class);
        EntityList<TargetGroup> targetGroups;
        if (result != null) {
            log.debug("Count of found groups: " + result.getResult().size());
            targetGroups = result.getResult();
        } else {
            log.debug("Unable to get list of target groups");
            targetGroups = null;
        }
        return new OperationResult<>(response, targetGroups);
    }

}
