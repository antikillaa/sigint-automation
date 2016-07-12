package services;

import abs.EntityList;
import abs.SearchFilter;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import http.requests.targets.TargetRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Result;
import model.Target;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class TargetService implements EntityService<Target> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(TargetService.class);
    private final String sigintHost = context.environment().getSigintHost();


    public int add(Target entity) {
        log.info("Creating new target");
        TargetRequest request = new TargetRequest();
        Response response = rsClient.put(sigintHost + request.getURI(), entity, request.getCookie());
        Target target = JsonCoverter.readEntityFromResponse(response, Target.class, "id");
        if (target != null) {
            context.entities().getTargets().addOrUpdateEntity(target);
        } else {
            log.error("Add new target process was failed");
            throw new AssertionError("Add new target process was failed");
        }
        return response.getStatus();
    }

    public int remove(Target entity) {
        log.info("Deleting target id:" + entity.getId());
        TargetRequest request = new TargetRequest().delete(entity.getId());
        Response response = rsClient.delete(sigintHost + request.getURI(), request.getCookie());

        if (response.getStatus() == 200) {
            try {
                context.entities().getTargets().removeEntity(entity);
            } catch (NullReturnException e) {
                log.warn("Was unable to remove entity with id:" + entity.getId() + " as it doesn't in the list");
            }
        }
        return response.getStatus();
    }

    public EntityList<Target> list(SearchFilter filter) {
        return null;
    }

    public int update(Target entity) {
        log.info("Updating target");
        TargetRequest request = new TargetRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());

        Result result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Result.class);
        if (result != null) {
            Verify.isTrue(Conditions.equals.elements(result.getResult(), "ok"));
            context.entities().getTargets().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update target process was failed");
            throw new AssertionError("Error! Update target process was failed");
        }
        return response.getStatus();
    }

    public Target view(String id) {
        log.info("View target entry id:" + id);
        TargetRequest request = new TargetRequest().get(id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        Target resultTarget = JsonCoverter.readEntityFromResponse(response, Target.class, "result");
        try {
            log.info("Result target: " + JsonCoverter.toJsonString(resultTarget));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
        }
        return resultTarget;
    }
}
