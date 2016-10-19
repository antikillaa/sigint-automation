package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4HttpClient;
import http.G4Response;
import http.requests.groups.GroupsRequest;
import json.JsonConverter;
import model.Group;
import org.apache.log4j.Logger;
import utils.Parser;

public class GroupService implements EntityService<Group> {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private Logger log = Logger.getLogger(GroupService.class);

    /**
     * ADD new Group
     * API: POST /api/auth/groups
     *
     * @param entity group
     * @return HTTP status code
     */
    @Override
    public int add(Group entity) {
        log.info("Creating new Group");
        log.debug(Parser.entityToString(entity));

        GroupsRequest request = new GroupsRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Group createdGroup = JsonConverter.readEntityFromResponse(response, Group.class);
        if (createdGroup != null) {
            Entities.getGroups().addOrUpdateEntity(createdGroup);
        }
        return response.getStatus();
    }

    @Override
    public int remove(Group entity) {
        return 0;
    }

    @Override
    public EntityList<Group> list(SearchFilter filter) {
        return null;
    }

    /**
     * UPDATE new Group
     * API: PUT /api/auth/groups
     *
     * @param entity group
     * @return HTTP status code
     */
    @Override
    public int update(Group entity) {
        log.info("Updating Group id" + entity.getId());
        log.debug(Parser.entityToString(entity));

        GroupsRequest request = new GroupsRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Group updatedGroup = JsonConverter.readEntityFromResponse(response, Group.class);
        if (updatedGroup != null) {
            Entities.getGroups().addOrUpdateEntity(updatedGroup);
        }
        return response.getStatus();
    }

    @Override
    public Group view(String id) {
        return null;
    }
}
