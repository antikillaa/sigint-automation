package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import http.requests.groups.GroupsRequest;
import json.JsonCoverter;
import model.AppContext;
import model.Group;
import model.PegasusMediaType;
import org.apache.log4j.Logger;

public class GroupService implements EntityService<Group> {

    private static G4Client g4Client = new G4Client();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(GroupService.class);
    private final String sigintHost = context.environment().getSigintHost();

    public int add(Group entity) {
        log.info("Creating new Group");
        try {
            log.debug("Group: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("Unable to parse Group entity");
        }

        GroupsRequest request = new GroupsRequest();
        G4Response response = g4Client
                .post(sigintHost + request.getURI(), entity, request.getCookie(), PegasusMediaType.PEGASUS_JSON);

        Group createdGroup = JsonCoverter.readEntityFromResponse(response, Group.class);
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
            log.debug("Group: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("Unable to parse Group entity");
        }

        GroupsRequest request = new GroupsRequest().update(entity.getId());
        G4Response response = g4Client
                .put(sigintHost + request.getURI(), entity, request.getCookie(), PegasusMediaType.PEGASUS_JSON);

        Group updatedGroup = JsonCoverter.readEntityFromResponse(response, Group.class);
        if (updatedGroup != null) {
            context.entities().getGroups().addOrUpdateEntity(updatedGroup);
        }
        return response.getStatus();
    }

    public Group view(String id) {
        return null;
    }
}
