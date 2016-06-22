package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.targetGroups.TargetGroupRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.TargetGroup;
import model.targetGroup.TargetGroupSearchResult;
import org.apache.log4j.Logger;
import service.EntityService;

import javax.ws.rs.core.Response;
import java.util.List;

public class TargetGroupService implements EntityService<TargetGroup> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(TargetGroupService.class);
    private final String sigintHost = context.environment().getSigintHost();


    public int add(TargetGroup entity) {
        log.info("Creating new target group");
        try {
            log.info("TargetGroup: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
        }
        TargetGroupRequest request = new TargetGroupRequest();

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
        log.info("View target group id:" + id);
        TargetGroupRequest request = new TargetGroupRequest().get(id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        TargetGroup resultTargetGroup = JsonCoverter.readEntityFromResponse(response, TargetGroup.class, "result");
        try {
            log.info("Result target group: " + JsonCoverter.toJsonString(resultTargetGroup));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
        }
        return resultTargetGroup;
    }

    public List<TargetGroup> view() {
        log.info("Get list of target groups");
        TargetGroupRequest request = new TargetGroupRequest();
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        TargetGroupSearchResult result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), TargetGroupSearchResult.class);
        if (result != null) {
            log.info("Size of list: " + result.getResult().size());
            return result.getResult();
        } else {
            throw new AssertionError("Unable to get list of target groups");
        }
    }
}
