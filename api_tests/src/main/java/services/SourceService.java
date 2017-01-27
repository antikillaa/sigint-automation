package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.SourceRequest;
import model.Result;
import model.Source;
import model.SourceListResult;
import model.SourcesRequest;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.Iterator;

public class SourceService implements EntityService<Source> {

    private Logger log = Logger.getLogger(RoleService.class);

    /**
     * API: PUT /api/sigint/admin/source/add
     *
     * @param entity Source entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Source> add(Source entity) {
        log.info("Creating new Source..");
        log.debug(Parser.entityToString(entity));

        SourceRequest request = new SourceRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        Source source = JsonConverter.readEntityFromResponse(response, Source.class, "id");
        OperationResult<Source> operationResult = new OperationResult<>(response, source);
        if (operationResult.isSuccess()) {
            Entities.getSources().addOrUpdateEntity(source);
        }
        return operationResult;
    }

    /**
     * API: DELETE /api/sigint/admin/source/{id}
     *
     * @param entity Source entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Result> remove(Source entity) {
        log.info("Deleting Source id:" + entity.getId());

        SourceRequest request = new SourceRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {
            Entities.getSources().addOrUpdateEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<EntityList<Source>> list(SearchFilter filter) {
        throw new NotImplementedException();
    }

    /**
     * GET list of Sources. Filter {"deleted":false}
     * API: GET /api/sigint/sources getSources
     *
     * @return HTTP status code
     */
    public OperationResult<EntityList<Source>> list() {
        SourcesRequest request = new SourcesRequest();
        G4Response response = g4HttpClient.sendRequest(request);
        SourceListResult result = JsonConverter.readEntityFromResponse(response, SourceListResult.class);
        // filter {"deleted":false}
        Iterator<Source> iterator = result.getResult().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isDeleted()) {
                iterator.remove();
            }
        }
        EntityList<Source> sourceEntityList = result.getResult();
        return new OperationResult<>(response, sourceEntityList);
    }

    /**
     * API: POST /api/sigint/admin/source
     *
     * @param entity Source entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Source> update(Source entity) {
        log.info("Updating Source id: " + entity.getId());
        entity.setVersion(entity.getVersion() == null ? 1 : entity.getVersion() + 1);
        log.debug(Parser.entityToString(entity));
        SourceRequest request = new SourceRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult<Source> operationResult = new OperationResult<>(response, entity);
        if (operationResult.isSuccess()) {
            Entities.getSources().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update source was failed");
        }
        return operationResult;
    }

    /**
     * API: GET /api/sigint/admin/source/{id}/details
     *
     * @param id id of entity
     * @return Source entity
     */
    @Override
    public OperationResult<Source> view(String id) {
        log.info("View Source details, id:" + id);
        SourceRequest request = new SourceRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);
        Source source =  JsonConverter.readEntityFromResponse(response, Source.class, "result");
        return new OperationResult<>(response, source);
    }
}
