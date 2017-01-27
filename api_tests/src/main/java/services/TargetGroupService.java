package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.targetGroups.TargetGroupRequest;
import model.TargetGroup;
import model.targetGroup.TargetGroupSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

public class TargetGroupService implements EntityService<TargetGroup> {

    private Logger log = Logger.getLogger(TargetGroupService.class);
    
    /**
     * PUT /target-groups createTargetGroup
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<TargetGroup> add(TargetGroup entity) {
        log.info("Creating new target group");
        log.debug(Parser.entityToString(entity));

        TargetGroupRequest request = new TargetGroupRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        
        TargetGroup group = JsonConverter.readEntityFromResponse(response, TargetGroup.class, "id");
        if (group != null) {
            Entities.getTargetGroups().addOrUpdateEntity(group);
        }
        return new OperationResult<>(response, group);
    }

    /**
     * DELETE /target-groups/{id} removeTargetGroup
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult remove(TargetGroup entity) {
        log.info("Deleting target group id:" + entity.getId());
        log.debug(Parser.entityToString(entity));
        TargetGroupRequest request = new TargetGroupRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult<String> deleteResult = new OperationResult<>(response);
        if (deleteResult.isSuccess()) {
            Entities.getTargetGroups().removeEntity(entity);
            
        }
        return deleteResult;
    }

    // TODO POST /target-groups/search search
    @Override
    public OperationResult<EntityList<TargetGroup>> list(SearchFilter filter) {
        throw new NotImplementedException();
    }

    /**
     * POST /target-groups updateTargetGroup
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<TargetGroup> update(TargetGroup entity) {
        log.info("Updating target group");
        log.debug(Parser.entityToString(entity));

        TargetGroupRequest request = new TargetGroupRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        TargetGroup targetGroup = JsonConverter.readEntityFromResponse(response, TargetGroup.class, "result");
        log.debug(Parser.entityToString(targetGroup));
        if (targetGroup != null) {
            Entities.getTargetGroups().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update targetGroup process was failed");
            throw new AssertionError("Error! Update targetGroup process was failed");
        }
        return new OperationResult<>(response, targetGroup);
    }

    /**
     * GET /target-groups/{id}/details getTargetGroupDetails
     *
     * @param id id of entity
     * @return TargetGroup entity
     */
    @Override
    public OperationResult<TargetGroup> view(String id) {
        log.info("View target group id:" + id);
        TargetGroupRequest request = new TargetGroupRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroup resultTargetGroup = JsonConverter.readEntityFromResponse(response, TargetGroup.class, "result");
        log.debug(Parser.entityToString(resultTargetGroup));
        return new OperationResult<>(response, resultTargetGroup);
    }

    /**
     * GET /target-groups getTargetGroups
     *
     * @return list of TargetGroup
     */
    public OperationResult<TargetGroupSearchResult> list() {
        log.info("Get list of target groups");
        TargetGroupRequest request = new TargetGroupRequest();
        G4Response response = g4HttpClient.sendRequest(request);
        return new OperationResult<>(response, TargetGroupSearchResult.class);
    }
}
