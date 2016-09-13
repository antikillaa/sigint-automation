package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import app_context.properties.G4Properties;
import http.G4Response;
import http.client.G4Client;
import http.requests.roles.RoleRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.PegasusMediaType;
import model.Role;
import org.apache.log4j.Logger;
import utils.Parser;

public class RoleService implements EntityService<Role> {

    private static RsClient rsClient = new RsClient();
    private static G4Client g4Client = new G4Client();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(RoleService.class);
    private final String sigintHost = G4Properties.getRunProperties().getApplicationURL();

    public int add(Role entity) {
        log.info("Creating new Role");
        log.debug(Parser.entityToString(entity));

        RoleRequest request = new RoleRequest();
        String url = sigintHost + request.getURI();
        G4Response response = g4Client.post(url, entity, request.getCookie(), PegasusMediaType.PEGASUS_JSON);

        Role createdRole = JsonCoverter.readEntityFromResponse(response, Role.class);
        log.debug(Parser.entityToString(createdRole));
        if (createdRole != null) {
            Entities.getRoles().addOrUpdateEntity(createdRole);
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
