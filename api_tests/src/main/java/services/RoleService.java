package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4HttpClient;
import http.G4Response;
import http.requests.roles.RoleRequest;
import json.JsonCoverter;
import model.Role;
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
    public int add(Role entity) {
        log.info("Creating new Role");
        log.debug(Parser.entityToString(entity));

        RoleRequest request = new RoleRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Role createdRole = JsonCoverter.readEntityFromResponse(response, Role.class);
        log.debug(Parser.entityToString(createdRole));
        if (createdRole != null) {
            Entities.getRoles().addOrUpdateEntity(createdRole);
        }
        return response.getStatus();
    }

    @Override
    public int remove(Role entity) {
        return 0;
    }

    @Override
    public EntityList<Role> list(SearchFilter filter) {
        return null;
    }

    @Override
    public int update(Role entity) {
        return 0;
    }

    @Override
    public Role view(String id) {
        return null;
    }
}
