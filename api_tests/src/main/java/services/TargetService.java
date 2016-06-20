package services;

import abs.EntityList;
import abs.SearchFilter;
import conditions.Conditions;
import conditions.Verify;
import http.requests.targets.TargetRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Result;
import model.Target;
import org.apache.log4j.Logger;
import service.EntityService;

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
        }
        return response.getStatus();
    }

    public int remove(Target entity) {
        return 0;
    }

    public EntityList<Target> list(SearchFilter filter) {
        return null;
    }

    public int update(Target entity) {
        log.info("Updating new target");
        TargetRequest request = new TargetRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());

        Result result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Result.class);
        if (result != null) {
            Verify.isTrue(Conditions.equals.elements(result.getResult(), "ok"));
        } else {
            log.error("Error! Update target process was failed");
            throw new AssertionError("Error! Update target process was failed");
        }
        return response.getStatus();
    }

    public Target view(String id) {
        TargetRequest request = new TargetRequest().get(id);
        log.info("View target entry id:" + id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());
        return JsonCoverter.readEntityFromResponse(response, Target.class, "result");
    }
}
