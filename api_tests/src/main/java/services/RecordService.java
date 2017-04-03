package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import errors.OperationResultError;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.RecordRequest;
import model.Record;
import model.RecordSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

public class RecordService implements EntityService<Record> {

    private static Logger log = Logger.getLogger(RecordService.class);
    private static RecordRequest request = new RecordRequest();

    /**
     * ADD new manual record
     * API: POST /api/sigint/record/manual
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Record> add(Record entity) {
        log.info("Creating new record");
        log.debug(Parser.entityToString(entity));
        G4Response response = g4HttpClient.sendRequest(request.manual(entity));

        OperationResult<Record> operationResult = new OperationResult<>(response, Record.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRecords().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    @Override
    public OperationResult remove(Record entity) {
        throw new NotImplementedException();
    }

    /**
     * Search list of records
     * API: POST "/api/sigint/records/search?withTargets=true"
     *
     * @param filter search filter for payload
     * @return EntityList of record
     */
    @Override
    public OperationResult<EntityList<Record>> list(SearchFilter filter) {
        log.info("Search records by filter:" + JsonConverter.toJsonString(filter));
        G4Response response = g4HttpClient.sendRequest(request.search(filter));

        OperationResult<RecordSearchResult> operationResult = new OperationResult<>(response, RecordSearchResult.class);
        if (operationResult.isSuccess()) {
            EntityList<Record> records = operationResult.getEntity().getResult();
            log.info("Founded " + records.size() + " records");
            return new OperationResult<>(response, records);
        } else {
            throw new OperationResultError(operationResult);
        }
    }

    @Override
    public OperationResult<Record> update(Record entity) {
        throw new RuntimeException();
    }

    @Override
    public OperationResult<Record> view(String id) {
        throw new RuntimeException();
    }
}