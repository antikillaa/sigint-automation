package services;

import abs.EntityList;
import abs.SearchFilter;
import http.requests.targetGroups.TargetGroupNewRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.TargetGroup;
import org.apache.log4j.Logger;
import service.EntityService;

import javax.ws.rs.core.Response;

public class TargetGroupService implements EntityService<TargetGroup> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    Logger log = Logger.getLogger(TargetGroupService.class);
    private final String sigintHost = context.environment().getSigintHost();


    public int addNew(TargetGroup entity) {
        log.info("Creating new target group");
        TargetGroupNewRequest request = new TargetGroupNewRequest();
        Response response = rsClient.put(sigintHost + request.getURI(), entity, request.getCookie());
        TargetGroup group = JsonCoverter.readEntityFromResponse(response, TargetGroup.class, "id");
        if (group != null) {
            context.entities().getTargetGroups().addOrUpdateEntity(group);
        }
        return response.getStatus();
    }

    public int remove(TargetGroup entity) {
        return 0;
    }

    public EntityList<TargetGroup> list(SearchFilter filter) {
        return null;
    }

    public int update(TargetGroup entity) {
        return 0;
    }

    public TargetGroup view(String id) {
        return null;
    }
}
