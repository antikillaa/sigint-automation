package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.TargetGroupRequest;
import model.TargetGroup;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;

public class TargetGroupService implements EntityService<TargetGroup> {

    private Logger log = Logger.getLogger(TargetGroupService.class);
    private TargetGroupRequest request = new TargetGroupRequest();
    
    @Override
    public OperationResult<TargetGroup> add(TargetGroup entity) {
        log.info("Creating new target group");
        log.debug(Parser.entityToString(entity));

        G4Response response = g4HttpClient.sendRequest(request.add(entity));
        
        TargetGroup group = JsonConverter.readEntityFromResponse(response, TargetGroup.class, "data");
        if (group != null) {
            Entities.getTargetGroups().addOrUpdateEntity(group);
        }
        return new OperationResult<>(response, group);
    }

    @Override
    public OperationResult remove(TargetGroup entity) {
        log.info("Deleting target group id:" + entity.getId());
        log.debug(Parser.entityToString(entity));
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

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

    @Override
    public OperationResult<TargetGroup> view(String id) {
        log.info("View target group id:" + id);
        G4Response response = g4HttpClient.sendRequest(request.get(id));

        TargetGroup resultTargetGroup = JsonConverter.readEntityFromResponse(response, TargetGroup.class, "result");
        return new OperationResult<>(response, resultTargetGroup);
    }

    /**
     * Get target groups list. Target Group API for G4 backward compatibility
     * GET /target-groups getTargetGroups
     *
     * @return list of TargetGroup
     */
    public OperationResult<EntityList<TargetGroup>> listG4Compatibility() {
        log.info("Get list of target groups");
        G4Response response = g4HttpClient.sendRequest(request.listG4Compatibility());

        List<TargetGroup> targetGroupList =
                JsonConverter.readEntitiesFromResponse(response, TargetGroup[].class, "result");
        return new OperationResult<>(response, new EntityList<>(targetGroupList));
    }
}
