package services;

import abs.EntityList;
import abs.SearchFilter;
import http.requests.roles.RoleRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.PegasusMediaType;
import model.Role;
import org.apache.log4j.Logger;
import utils.Parser;

import javax.ws.rs.core.Response;

public class RoleService implements EntityService<Role> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(RoleService.class);
    private final String sigintHost = context.environment().getSigintHost();

    public int add(Role entity) {
        log.info("Creating new Role");
        log.debug(Parser.entityToString(entity));

        RoleRequest request = new RoleRequest();
        Response response = rsClient
                .post(sigintHost + request.getURI(), entity, request.getCookie(), PegasusMediaType.PEGASUS_JSON);
        String jsonString = response.readEntity(String.class);

        Role createdRole = JsonCoverter.fromJsonToObject(jsonString, Role.class);
        log.debug(Parser.entityToString(createdRole));
        if (createdRole != null) {
            context.entities().getRoles().addOrUpdateEntity(createdRole);
        }
        return response.getStatus();
    }

    public int remove(Role entity) {
        return 0;
    }

    public EntityList<Role> list(SearchFilter filter) {
        return null;
    }

    public int update(Role entity) {
        return 0;
    }

    public Role view(String id) {
        return null;
    }
}
