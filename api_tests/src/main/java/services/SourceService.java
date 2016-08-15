package services;

import abs.EntityList;
import abs.SearchFilter;
import http.requests.SourceRequest;
import json.JsonCoverter;
import json.RsClient;
import model.*;
import org.apache.log4j.Logger;
import utils.Parser;

import javax.ws.rs.core.Response;
import java.util.List;

public class SourceService implements EntityService<Source> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(RoleService.class);
    private final String sigintHost = context.environment().getSigintHost();

    @Override
    public int add(Source entity) {
        log.info("Creating new Source..");
        log.debug(Parser.entityToString(entity));

        SourceRequest request = new SourceRequest().add();
        Response response = rsClient.put(sigintHost + request.getURI(), entity, request.getCookie());

        Source source = JsonCoverter.readEntityFromResponse(response, Source.class, "id");
        context.entities().getSources().addOrUpdateEntity(source);

        return response.getStatus();
    }

    @Override
    public int remove(Source entity) {
        log.info("Deleting Source id:" + entity.getId());

        SourceRequest request = new SourceRequest().delete(entity.getId());
        Response response = rsClient.delete(sigintHost + request.getURI(), request.getCookie());

        Result result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Result.class);
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

        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        String jsonResponse = response.readEntity(String.class);
        SourceListResult result = JsonCoverter.fromJsonToObject(jsonResponse, SourceListResult.class);

        return result.getResult();
    }

    @Override
    public int update(Source entity) {
        log.info("Updating Source id: " + entity.getId());
        entity.incrementVersion();
        log.debug(Parser.entityToString(entity));

        SourceRequest request = new SourceRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());

        Result result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Result.class);
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
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        return JsonCoverter.readEntityFromResponse(response, Source.class, "result");
    }
}
