package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.groups.GroupsRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Group;
import model.PegasusMediaType;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class GroupService implements EntityService<Group> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(GroupService.class);
    private final String sigintHost = context.environment().getSigintHost();

    public int add(Group entity) {
        log.info("Creating new Group");
        try {
            log.info("Group: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.info(e.getMessage());
        }

        GroupsRequest request = new GroupsRequest();
        Response response = rsClient
                .post(sigintHost + request.getURI(), entity, request.getCookie(), PegasusMediaType.PEGASUS_JSON);
        String jsonString = response.readEntity(String.class);
        log.info("Response: " + jsonString);

        Group createdGroup = JsonCoverter.fromJsonToObject(jsonString, Group.class);
        if (createdGroup != null) {
            context.entities().getGroups().addOrUpdateEntity(createdGroup);
        }
        return response.getStatus();
    }

    public int remove(Group entity) {
        return 0;
    }

    public EntityList<Group> list(SearchFilter filter) {
        return null;
    }

    public int update(Group entity) {
        log.info("Updating Group id" + entity.getId());
        try {
            log.info("Group: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.info(e.getMessage());
        }

        GroupsRequest request = new GroupsRequest().update(entity.getId());
        Response response = rsClient
                .put(sigintHost + request.getURI(), entity, request.getCookie(), PegasusMediaType.PEGASUS_JSON);
        String jsonString = response.readEntity(String.class);
        log.info("Response: " + jsonString);

        Group updatedGroup = JsonCoverter.fromJsonToObject(jsonString, Group.class);
        if (updatedGroup != null) {
            context.entities().getGroups().addOrUpdateEntity(updatedGroup);
        }
        return response.getStatus();
    }

    public Group view(String id) {
        return null;
    }
}
