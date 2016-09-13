package services;

import abs.EntityList;
import abs.SearchFilter;
import http.G4Response;
import http.client.G4Client;
import http.requests.SourceRequest;
import json.JsonCoverter;
import model.*;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;

public class SourceService implements EntityService<Source> {

    private static G4Client g4Client = new G4Client();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(RoleService.class);
    private final String sigintHost = context.environment().getSigintHost();

    @Override
    public int add(Source entity) {
        log.info("Creating new Source..");
        log.debug(Parser.entityToString(entity));

        SourceRequest request = new SourceRequest().add();
        G4Response response = g4Client.put(sigintHost + request.getURI(), entity, request.getCookie());

        Source source = JsonCoverter.readEntityFromResponse(response, Source.class, "id");
        context.entities().getSources().addOrUpdateEntity(source);

        return response.getStatus();
    }

    @Override
    public int remove(Source entity) {
        log.info("Deleting Source id:" + entity.getId());

        SourceRequest request = new SourceRequest().delete(entity.getId());
        G4Response response = g4Client.delete(sigintHost + request.getURI(), request.getCookie());

        Result result = JsonCoverter.readEntityFromResponse(response, Result.class);
        if (response.getStatus() == 200) {
            context.put("resultMessage", result.getResult());
            context.entities().getSources().addOrUpdateEntity(entity);
        }
        return response.getStatus();
    }

    @Override
    public EntityList<Source> list(SearchFilter filter) {
        return null;
    }

    public List<Source> list() {
        SourcesRequest request = new SourcesRequest();

        G4Response response = g4Client.get(sigintHost + request.getURI(), request.getCookie());

        SourceListResult result = JsonCoverter.fromJsonToObject(response.getMessage(), SourceListResult.class);

        return result.getResult();
    }

    @Override
    public int update(Source entity) {
        log.info("Updating Source id: " + entity.getId());
        entity.incrementVersion();
        log.debug(Parser.entityToString(entity));

        SourceRequest request = new SourceRequest();
        G4Response response = g4Client.post(sigintHost + request.getURI(), entity, request.getCookie());

        Result result = JsonCoverter.readEntityFromResponse(response, Result.class);
        if (result != null) {
            context.put("resultMessage", result.getResult());
            context.entities().getSources().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update target process was failed");
            throw new AssertionError("Error! Update target process was failed");
        }
        return response.getStatus();
    }

    @Override
    public Source view(String id) {
        log.info("View Source details, id:" + id);

        SourceRequest request = new SourceRequest().get(id);
        G4Response response = g4Client.get(sigintHost + request.getURI(), request.getCookie());

        return JsonCoverter.readEntityFromResponse(response, Source.class, "result");
    }
}
