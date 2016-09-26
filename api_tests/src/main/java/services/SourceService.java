package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.RunContext;
import app_context.entities.Entities;
import http.G4HttpClient;
import http.G4Response;
import http.requests.SourceRequest;
import json.JsonCoverter;
import model.Result;
import model.Source;
import model.SourceListResult;
import model.SourcesRequest;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;

public class SourceService implements EntityService<Source> {

    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private Logger log = Logger.getLogger(RoleService.class);
    private RunContext context = RunContext.get();

    /**
     * API: PUT /api/sigint/admin/source/add
     *
     * @param entity Source entity
     * @return HTTP status code
     */
    @Override
    public int add(Source entity) {
        log.info("Creating new Source..");
        log.debug(Parser.entityToString(entity));

        SourceRequest request = new SourceRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Source source = JsonCoverter.readEntityFromResponse(response, Source.class, "id");
        Entities.getSources().addOrUpdateEntity(source);

        return response.getStatus();
    }

    /**
     * API: DELETE /api/sigint/admin/source/{id}
     *
     * @param entity Source entity
     * @return HTTP status code
     */
    @Override
    public int remove(Source entity) {
        log.info("Deleting Source id:" + entity.getId());

        SourceRequest request = new SourceRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);

        Result result = JsonCoverter.readEntityFromResponse(response, Result.class);
        if (response.getStatus() == 200) {
            context.put("resultMessage", result.getResult());
            Entities.getSources().addOrUpdateEntity(entity);
        }
        return response.getStatus();
    }

    @Override
    public EntityList<Source> list(SearchFilter filter) {
        return null;
    }

    /**
     * GET list of Sources
     * API: GET /api/sigint/sources getSources
     *
     * @return HTTP status code
     */
    public List<Source> list() {
        SourcesRequest request = new SourcesRequest();

        G4Response response = g4HttpClient.sendRequest(request);

        SourceListResult result = JsonCoverter.readEntityFromResponse(response, SourceListResult.class);

        return result.getResult();
    }

    /**
     * API: POST /api/sigint/admin/source
     *
     * @param entity Source entity
     * @return HTTP status code
     */
    @Override
    public int update(Source entity) {
        log.info("Updating Source id: " + entity.getId());
        entity.incrementVersion();
        log.debug(Parser.entityToString(entity));

        SourceRequest request = new SourceRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Result result = JsonCoverter.readEntityFromResponse(response, Result.class);
        if (result != null) {
            context.put("resultMessage", result.getResult());
            Entities.getSources().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update target process was failed");
            throw new AssertionError("Error! Update target process was failed");
        }
        return response.getStatus();
    }

    /**
     * API: GET /api/sigint/admin/source/{id}/details
     *
     * @param id id of entity
     * @return Source entity
     */
    @Override
    public Source view(String id) {
        log.info("View Source details, id:" + id);

        SourceRequest request = new SourceRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        return JsonCoverter.readEntityFromResponse(response, Source.class, "result");
    }
}
