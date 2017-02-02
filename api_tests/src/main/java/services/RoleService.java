package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.RoleRequest;
import model.RequestResult;
import model.Role;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;

public class RoleService implements EntityService<Role> {

    private Logger log = Logger.getLogger(RoleService.class);

    /**
     * Add new Role
     *
     * API: POST /api/auth/roles
     * @param entity Role entity for payload
     * @return HTTP status code
     */
    @Override
    public OperationResult<Role> add(Role entity) {
        log.info("Creating new Role, name:" + entity.getName());
        log.debug(Parser.entityToString(entity));

        RoleRequest request = new RoleRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult<Role> roleOperationResult = new OperationResult<>(response, Role.class);
        if (roleOperationResult.isSuccess()) {
            Entities.getRoles().addOrUpdateEntity(roleOperationResult.getResult());
        }
        return roleOperationResult;
    }

    /**
     * Remove role
     *
     * @param entity role entity for deleting
     * @return {@link OperationResult}
     */
    @Override
    public OperationResult<RequestResult> remove(Role entity) {
        log.info("Deleting Role, name:" + entity.getName());

        RoleRequest request = new RoleRequest().delete(entity.getName());
        G4Response response = g4HttpClient.sendRequest(request);

        OperationResult<RequestResult> operationResult = new OperationResult<>(response, RequestResult.class);
        if (operationResult.isSuccess()) {
            Entities.getRoles().removeEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<EntityList<Role>> list(SearchFilter filter) {
        throw new NotImplementedException();
    }

    public OperationResult<EntityList<Role>> list() {
        RoleRequest request = new RoleRequest().list();
        G4Response response = g4HttpClient.sendRequest(request);
        List<Role> roles = JsonConverter.readEntitiesFromResponse(response, Role[].class);
        return new OperationResult<>(response, new EntityList<>(roles));
    }

    @Override
    public OperationResult<Role> update(Role entity) {
        log.info("Updating Role, name: " + entity.getName());
        log.debug(Parser.entityToString(entity));

        RoleRequest request = new RoleRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        OperationResult<Role> operationResult = new OperationResult<>(response, Role.class);
        if (operationResult.isSuccess()) {
            Entities.getRoles().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update role was failed");
        }
        return operationResult;
    }

    @Override
    public OperationResult<Role> view(String id) {
        throw new NotImplementedException();
    }
}
