package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.RunContext;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import http.G4HttpClient;
import http.G4Response;
import http.requests.targetGroups.TargetGroupRequest;
import json.JsonConverter;
import model.Result;
import model.TargetGroup;
import model.targetGroup.TargetGroupSearchResult;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;

public class TargetGroupService implements EntityService<TargetGroup> {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private Logger log = Logger.getLogger(TargetGroupService.class);
    private RunContext context = RunContext.get();


    /**
     * PUT /target-groups createTargetGroup
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public int add(TargetGroup entity) {
        log.info("Creating new target group");
        log.debug(Parser.entityToString(entity));

        TargetGroupRequest request = new TargetGroupRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroup group = JsonConverter.readEntityFromResponse(response, TargetGroup.class, "id");
        if (group != null) {
            Entities.getTargetGroups().addOrUpdateEntity(group);
        }
        return response.getStatus();
    }

    /**
     * DELETE /target-groups/{id} removeTargetGroup
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public int remove(TargetGroup entity) {
        log.info("Deleting target group id:" + entity.getId());
        log.debug(Parser.entityToString(entity));

        TargetGroupRequest request = new TargetGroupRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);

        Result result = JsonConverter.readEntityFromResponse(response, Result.class);
        log.debug(Parser.entityToString(result));
        if (response.getStatus() == 200) {
            try {
                Verify.isTrue(Conditions.equals(result.getResult(), "ok"));
                Entities.getTargetGroups().removeEntity(entity);
            } catch (NullReturnException e) {
                log.warn("Was unable to remove entity with id:" + entity.getId() + " as it doesn't in the list");
            }
        }
        return response.getStatus();
    }

    // TODO POST /target-groups/search search
    @Override
    public EntityList<TargetGroup> list(SearchFilter filter) {
        return null;
    }

    /**
     * POST /target-groups updateTargetGroup
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public int update(TargetGroup entity) {
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
        return response.getStatus();
    }

    /**
     * GET /target-groups/{id}/details getTargetGroupDetails
     *
     * @param id id of entity
     * @return TargetGroup entity
     */
    @Override
    public TargetGroup view(String id) {
        log.info("View target group id:" + id);
        TargetGroupRequest request = new TargetGroupRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroup resultTargetGroup = JsonConverter.readEntityFromResponse(response, TargetGroup.class, "result");
        log.debug(Parser.entityToString(resultTargetGroup));
        return resultTargetGroup;
    }

    /**
     * GET /target-groups getTargetGroups
     *
     * @return list of TargetGroup
     */
    public List<TargetGroup> view() {
        log.info("Get list of target groups");

        TargetGroupRequest request = new TargetGroupRequest();
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroupSearchResult result = JsonConverter.readEntityFromResponse(response, TargetGroupSearchResult.class);
        context.put("code", response.getStatus());
        if (result != null) {
            log.debug("Size of list: " + result.getResult().size());
            return result.getResult();
        } else {
            throw new AssertionError("Unable to get list of target groups");
        }
    }
}
