package services;

import abs.EntityList;
import abs.SearchFilter;
import http.requests.targets.TargetNewRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Target;
import org.apache.log4j.Logger;
import service.EntityService;

import javax.ws.rs.core.Response;

public class TargetService implements EntityService<Target> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(TargetService.class);
    private final String sigintHost = context.environment().getSigintHost();


    public int addNew(Target entity) {
        log.info("Creating new target");
        TargetNewRequest request = new TargetNewRequest();
        Response response = rsClient.put(sigintHost + request.getURI(), entity, request.getCookie());
        Target target = JsonCoverter.readEntityFromResponse(response, Target.class, "id");
        if (target !=null) {
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
        return 0;
    }

    public Target view(String id) {
        return null;
    }
}
