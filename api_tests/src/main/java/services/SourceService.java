package services;

import errors.OperationResultError;
import http.G4Response;
import http.OperationResult;
import http.requests.SourceRequest;
import java.util.Arrays;
import java.util.List;
import model.Result;
import model.SearchFilter;
import model.Source;
import model.entities.Entities;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

public class SourceService implements EntityService<Source> {

    private static Logger log = Logger.getLogger(SourceService.class);
    private static SourceRequest request = new SourceRequest();

    /**
     * API: PUT /api/workflow/sources
     *
     * @param entity Source entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Source> add(Source entity) {
        log.info("Creating new Source..");

        G4Response response = g4HttpClient.sendRequest(request.add(entity));

        OperationResult<Source> operationResult = new OperationResult<>(response, Source.class);
        if (operationResult.isSuccess()) {
            Entities.getSources().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    /**
     * API: DELETE /api/workflow/sources/{id}
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
    public OperationResult<List<Source>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }

    /**
     * GET list of Sources. Filter {"deleted":false}
     * API: GET /api/workflow/sources getSources
     *
     * @return HTTP status code
     */
    @Override
    public OperationResult<List<Source>> list() {
        log.info("GET list of Sources. Filter {\"deleted\":false}");
        G4Response response = g4HttpClient.sendRequest(request.list());

        OperationResult<Source[]> operationResult = new OperationResult<>(response, Source[].class);
        if (operationResult.isSuccess() && operationResult.getEntity() != null) {
            // filter {"deleted":false}
            List<Source> sources = Arrays.asList(operationResult.getEntity());
            sources.removeIf(Source::isDeleted);

            return new OperationResult<>(response, sources);
        } else {
            throw new OperationResultError(operationResult);
        }
    }

    /**
     * API: POST /api/workflow/sources
     *
     * @param entity Source entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Source> update(Source entity) {
        log.info("Updating Source id: " + entity.getId());
        entity.setVersion(entity.getVersion() == null ? 1 : entity.getVersion() + 1);

        G4Response response = g4HttpClient.sendRequest(request.update(entity));

        OperationResult operationResult = new OperationResult<>(response);
        if (operationResult.isSuccess()) {
            Entities.getSources().addOrUpdateEntity(entity);
            return new OperationResult<>(response, entity);
        } else {
            log.error("Error! Update source was failed");
            return operationResult;
        }
    }

    /**
     * API: GET /api/workflow/sources/{id}
     *
     * @param id id of entity
     * @return Source entity
     */
    @Override
    public OperationResult<Source> view(String id) {
        log.info("View Source details, id:" + id);
        G4Response response = g4HttpClient.sendRequest(request.get(id));

        return new OperationResult<>(response, Source.class);
    }
}
