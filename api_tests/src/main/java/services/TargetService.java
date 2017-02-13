package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import file_generator.FileGenerator;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.targets.TargetRequest;
import model.*;
import org.apache.log4j.Logger;

import java.util.List;

public class TargetService implements EntityService<Target> {

    private static final Logger log = Logger.getLogger(TargetService.class);
    private static final TargetRequest request = new TargetRequest();
    /*
        TODO
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
        G4Response response = g4HttpClient.sendRequest(request.add(entity));

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
        log.info("Writing Targets to file");
        G4File file = new FileGenerator(Target.class).write(targets);

        log.info("Upload file " + file.getAbsolutePath() + " with " + targets.size() + " targets");
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
        log.info("Deleting target id:" + entity.getId() + " name:" + entity.getName());
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

        OperationResult operationResult = new OperationResult(response);
        if (operationResult.isSuccess()) {
            Entities.getTargets().removeEntity(entity);
        }
        return operationResult;
    }

    /**
     * Search targets by filter
     * POST /targets/search search
     *
     * @param filter search filter for payload
     * @return EntityList of Targets
     */
    @SuppressWarnings("unchecked")
    @Override
    public OperationResult<EntityList<Target>> list(SearchFilter filter) {
        log.info("Search targets by filter:" + JsonConverter.toJsonString(filter));
        G4Response response = g4HttpClient.sendRequest(request.search(filter));

        TargetSearchResult targetSearchResult = JsonConverter.readEntityFromResponse(response, TargetSearchResult.class, "result");
        return new OperationResult<>(response, new EntityList<>(targetSearchResult.getContent()));
    }

    /**
     * Get list of targets
     *
     * @return {@link OperationResult<EntityList<Target>>}
     */
    public OperationResult<EntityList<Target>> list() {
        log.info("Get list of targets");
        G4Response response = g4HttpClient.sendRequest(request.list());

        List<Target> targets = JsonConverter.readEntitiesFromResponse(response, Target[].class, "result");
        log.info("received " + targets.size() + " targets");
        return new OperationResult<>(response, new EntityList<>(targets));
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
        G4Response response = g4HttpClient.sendRequest(request.update(entity));

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
        G4Response response = g4HttpClient.sendRequest(request.get(id));

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
        G4Response response = g4HttpClient.sendRequest(request.findTargetGroups(id));

        List<TargetGroup> targetGroups = JsonConverter.readEntitiesFromResponse(response, TargetGroup[].class);
        return new OperationResult<>(response, new EntityList<>(targetGroups));
    }

}
