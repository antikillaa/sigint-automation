package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4HttpClient;
import http.G4Response;
import http.OperationResult;
import http.requests.roles.RoleRequest;
import model.Role;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

public class RoleService implements EntityService<Role> {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private Logger log = Logger.getLogger(RoleService.class);

    /**
     * API: POST /api/auth/roles
     * @param entity Role entity for payload
     * @return HTTP status code
     */
    @Override
    public OperationResult<Role> add(Role entity) {
        log.info("Creating new Role");
        log.debug(Parser.entityToString(entity));

        RoleRequest request = new RoleRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult<Role> roleOperationResult = new OperationResult<>(response, Role.class);
        if (roleOperationResult.isSuccess()) {
            Entities.getRoles().addOrUpdateEntity(roleOperationResult.getResult());
        }
        return roleOperationResult;
    }

    @Override
    public OperationResult remove(Role entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<EntityList<Role>> list(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Role> update(Role entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Role> view(String id) {
        throw new NotImplementedException();
    }
}
