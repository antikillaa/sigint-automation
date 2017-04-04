package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.TargetGroupRequest;
import model.TargetGroup;
import model.TargetGroupSearchResult;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;

public class TargetGroupService implements EntityService<TargetGroup> {

    private Logger log = Logger.getLogger(TargetGroupService.class);
    private TargetGroupRequest request = new TargetGroupRequest();

    @Override
    public OperationResult<TargetGroup> add(TargetGroup entity) {
        log.info("Creating new target group");
        log.debug(Parser.entityToString(entity));

        G4Response response = g4HttpClient.sendRequest(request.add(entity));

        OperationResult<TargetGroup> operationResult = new OperationResult<>(response, TargetGroup.class, "data");
        if (operationResult.isSuccess()) {
            Entities.getTargetGroups().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
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

    @Override
    public OperationResult<EntityList<TargetGroup>> list(SearchFilter filter) {
        log.info("Get list of target groups");
        G4Response response = g4HttpClient.sendRequest(request.list(filter));

        List<TargetGroup> targetGroupList =
                JsonConverter.jsonToObjectsList(response.getMessage(), TargetGroup[].class, "data");

        return new OperationResult<>(response, new EntityList<>(targetGroupList));
    }

    @Override
    public OperationResult<TargetGroup> update(TargetGroup entity) {
        log.info("Updating target group");
        log.debug(Parser.entityToString(entity));

        G4Response response = g4HttpClient.sendRequest(request.update(entity));

        OperationResult<TargetGroup> operationResult = new OperationResult<>(response, TargetGroup.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getTargetGroups().addOrUpdateEntity(operationResult.getEntity());
        } else {
            log.error("Error! Update targetGroup process was failed");
            throw new AssertionError("Error! Update targetGroup process was failed");
        }
        return operationResult;
    }

    @Override
    public OperationResult<TargetGroup> view(String id) {
        log.info("View target group id:" + id);
        G4Response response = g4HttpClient.sendRequest(request.get(id));

        return new OperationResult<>(response, TargetGroup.class, "result");
    }

    /**
     * Get target groups list. Target Group API for G4 backward compatibility
     * GET /target-groups getTargetGroups
     *
     * @return list of TargetGroup
     */
    @Deprecated
    public OperationResult<EntityList<TargetGroup>> listG4Compatibility() {
        log.info("Get list of target groups");
        G4Response response = g4HttpClient.sendRequest(request.listG4Compatibility());

        List<TargetGroup> targetGroupList =
                JsonConverter.jsonToObjectsList(response.getMessage(), TargetGroup[].class, "result");
        return new OperationResult<>(response, new EntityList<>(targetGroupList));
    }

    /**
     * Search target-groups by filter
     * POST /target-groups/search search
     *
     * @param filter search filter for payload
     * @return {@link OperationResult<EntityList<TargetGroup>> }
     */
    @Deprecated
    public OperationResult<EntityList<TargetGroup>> searchG4Compatibility(SearchFilter filter) {
        log.info("Search targetGroups by filter:" + JsonConverter.toJsonString(filter));

        G4Response response = g4HttpClient.sendRequest(request.searchG4Compatibility(filter));

        OperationResult<TargetGroupSearchResult> operationResult =
                new OperationResult<>(response, TargetGroupSearchResult.class, "result");

        if (operationResult.isSuccess()) {
            List<TargetGroup> targetGroups = new ArrayList<>(operationResult.getEntity().getContent());
            return new OperationResult<>(response, new EntityList<>(targetGroups));
        } else {
            throw new RuntimeException("Unable to read list of target group from response");
        }
    }
}
