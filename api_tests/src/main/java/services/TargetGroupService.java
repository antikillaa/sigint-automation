package services;

import abs.EntityList;
import abs.SearchFilter;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import http.requests.targetGroups.TargetGroupRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Result;
import model.TargetGroup;
import model.targetGroup.TargetGroupSearchResult;
import org.apache.log4j.Logger;
import utils.Parser;

import javax.ws.rs.core.Response;
import java.util.List;

public class TargetGroupService implements EntityService<TargetGroup> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(TargetGroupService.class);
    private final String sigintHost = context.environment().getSigintHost();


    public int add(TargetGroup entity) {
        log.info("Creating new target group");
        log.debug(Parser.entityToString(entity));

        TargetGroupRequest request = new TargetGroupRequest();

        Response response = rsClient.put(sigintHost + request.getURI(), entity, request.getCookie());
        TargetGroup group = JsonCoverter.readEntityFromResponse(response, TargetGroup.class, "id");
        if (group != null) {
            context.entities().getTargetGroups().addOrUpdateEntity(group);
        }
        return response.getStatus();
    }

    public int remove(TargetGroup entity) {
        log.info("Deleting target group id:" + entity.getId());
        log.debug(Parser.entityToString(entity));
        TargetGroupRequest request = new TargetGroupRequest().delete(entity.getId());
        Response response = rsClient.delete(sigintHost + request.getURI(), request.getCookie());

        Result result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Result.class);
        log.debug(Parser.entityToString(result));
        if (response.getStatus() == 200) {
            try {
                Verify.isTrue(Conditions.equals.elements(result.getResult(), "ok"));
                context.entities().getTargetGroups().removeEntity(entity);
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
        log.info("Updating target group" );
        log.debug(Parser.entityToString(entity));

        TargetGroupRequest request = new TargetGroupRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());

        TargetGroup targetGroup = JsonCoverter.readEntityFromResponse(response, TargetGroup.class, "result");
        log.debug(Parser.entityToString(targetGroup));
        if (targetGroup != null) {
            context.entities().getTargetGroups().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update targetGroup process was failed");
            throw new AssertionError("Error! Update targetGroup process was failed");
        }
        return response.getStatus();
    }

    public TargetGroup view(String id) {
        log.info("View target group id:" + id);
        TargetGroupRequest request = new TargetGroupRequest().get(id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        TargetGroup resultTargetGroup = JsonCoverter.readEntityFromResponse(response, TargetGroup.class, "result");
        log.debug(Parser.entityToString(resultTargetGroup));
        return resultTargetGroup;
    }

    public List<TargetGroup> view() {
        log.info("Get list of target groups");

        TargetGroupRequest request = new TargetGroupRequest();
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        TargetGroupSearchResult result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), TargetGroupSearchResult.class);
        if (result != null) {
            log.debug("Size of list: " + result.getResult().size());
            return result.getResult();
        } else {
            throw new AssertionError("Unable to get list of target groups");
        }
    }
}
