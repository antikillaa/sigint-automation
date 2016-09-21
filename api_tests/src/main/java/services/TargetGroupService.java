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
import json.JsonCoverter;
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


    public int add(TargetGroup entity) {
        log.info("Creating new target group");
        log.debug(Parser.entityToString(entity));

        TargetGroupRequest request = new TargetGroupRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroup group = JsonCoverter.readEntityFromResponse(response, TargetGroup.class, "id");
        if (group != null) {
            Entities.getTargetGroups().addOrUpdateEntity(group);
        }
        return response.getStatus();
    }

    public int remove(TargetGroup entity) {
        log.info("Deleting target group id:" + entity.getId());
        log.debug(Parser.entityToString(entity));

        TargetGroupRequest request = new TargetGroupRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);

        Result result = JsonCoverter.readEntityFromResponse(response, Result.class);
        log.debug(Parser.entityToString(result));
        if (response.getStatus() == 200) {
            try {
                Verify.isTrue(Conditions.equals.elements(result.getResult(), "ok"));
                Entities.getTargetGroups().removeEntity(entity);
            } catch (NullReturnException e) {
                log.warn("Was unable to remove entity with id:" + entity.getId() + " as it doesn't in the list");
            }
        }
        return response.getStatus();
    }

    public EntityList<TargetGroup> list(SearchFilter filter) {
        return null;
    }

    public int update(TargetGroup entity) {
        log.info("Updating target group");
        log.debug(Parser.entityToString(entity));

        TargetGroupRequest request = new TargetGroupRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroup targetGroup = JsonCoverter.readEntityFromResponse(response, TargetGroup.class, "result");
        log.debug(Parser.entityToString(targetGroup));
        if (targetGroup != null) {
            Entities.getTargetGroups().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update targetGroup process was failed");
            throw new AssertionError("Error! Update targetGroup process was failed");
        }
        return response.getStatus();
    }

    public TargetGroup view(String id) {
        log.info("View target group id:" + id);
        TargetGroupRequest request = new TargetGroupRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroup resultTargetGroup = JsonCoverter.readEntityFromResponse(response, TargetGroup.class, "result");
        log.debug(Parser.entityToString(resultTargetGroup));
        return resultTargetGroup;
    }

    public List<TargetGroup> view() {
        log.info("Get list of target groups");

        TargetGroupRequest request = new TargetGroupRequest();
        G4Response response = g4HttpClient.sendRequest(request);

        TargetGroupSearchResult result = JsonCoverter.readEntityFromResponse(response, TargetGroupSearchResult.class);
        context.put("code", response.getStatus());
        if (result != null) {
            log.debug("Size of list: " + result.getResult().size());
            return result.getResult();
        } else {
            throw new AssertionError("Unable to get list of target groups");
        }
    }
}
