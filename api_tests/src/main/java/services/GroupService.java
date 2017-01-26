package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4HttpClient;
import http.G4Response;
import http.OperationResult;
import http.requests.GroupsRequest;
import model.Group;
import model.RequestResult;
import org.apache.commons.lang.NotImplementedException;
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
    public OperationResult<Group> add(Group entity) {
        log.info("Creating new Group");
        log.debug(Parser.entityToString(entity));

        GroupsRequest request = new GroupsRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult<Group> operationResult = new OperationResult<>(response, Group.class);
        if (operationResult.isSuccess()) {
            Entities.getGroups().addOrUpdateEntity(operationResult.getResult());
        }
        return operationResult;
    }

    @Override
    public OperationResult<RequestResult> remove(Group entity) {
        log.info("Deleting Group, id:" + entity.getId());

        GroupsRequest request = new GroupsRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);

        OperationResult<RequestResult> operationResult = new OperationResult<>(response, RequestResult.class);
        if (operationResult.isSuccess()) {
            Entities.getGroups().removeEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<EntityList<Group>> list(SearchFilter filter) {
        throw new NotImplementedException();
    }

    /**
     * UPDATE new Group
     * API: PUT /api/auth/groups
     *
     * @param entity group
     * @return HTTP status code
     */
    @Override
    public OperationResult<Group> update(Group entity) {
        log.info("Updating Group id" + entity.getId());
        log.debug(Parser.entityToString(entity));

        GroupsRequest request = new GroupsRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult<Group> operationResult = new OperationResult<Group>(response, Group.class);
        if (operationResult.isSuccess()) {
            Entities.getGroups().addOrUpdateEntity(operationResult.getResult());
        }
        return operationResult;
    }

    @Override
    public OperationResult<Group> view(String id) {
        throw new NotImplementedException();
    }
}
