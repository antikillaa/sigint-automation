package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4Response;
import http.OperationResult;
import http.requests.SourceRequest;
import http.requests.SourcesRequest;
import model.Result;
import model.Source;
import model.SourceListResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;

public class SourceService implements EntityService<Source> {

    private static Logger log = Logger.getLogger(SourceService.class);
    private static SourceRequest request = new SourceRequest();
    private static SourcesRequest sourcesRequest = new SourcesRequest();

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

        G4Response response = g4HttpClient.sendRequest(request.add(entity));

        OperationResult<Source> operationResult = new OperationResult<>(response, Source.class, "id");
        if (operationResult.isSuccess()) {
            Entities.getSources().addOrUpdateEntity(operationResult.getEntity());
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
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {
            Entities.getSources().removeEntity(entity);
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
        log.info("GET list of Sources. Filter {\"deleted\":false}");
        G4Response response = g4HttpClient.sendRequest(sourcesRequest.list());

        OperationResult<SourceListResult> operationResult = new OperationResult<>(response, SourceListResult.class);
        if (operationResult.isSuccess()) {
            // filter {"deleted":false}
            List<Source> sources = new ArrayList<>(operationResult.getEntity().getResult().getEntities());
            sources.removeIf(Source::isDeleted);

            return new OperationResult<>(response, new EntityList<>(sources));
        } else {
            throw new RuntimeException("Unable to read list of sources from response");
        }
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

        G4Response response = g4HttpClient.sendRequest(request.update(entity));

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);

        if (operationResult.isSuccess() && operationResult.getEntity().getResult().equals("ok")) {
            Entities.getSources().addOrUpdateEntity(entity);
            return new OperationResult<>(response, entity);
        } else {
            log.error("Error! Update source was failed");
            return new OperationResult<>(response);
        }
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
        G4Response response = g4HttpClient.sendRequest(request.get(id));

        return new OperationResult<>(response, Source.class, "result");
    }
}
