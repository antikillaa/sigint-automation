package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
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

    private static Logger LOGGER = Logger.getLogger(RecordService.class);

    /**
     * ADD new manual record
     * API: POST /api/sigint/record/manual
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Record> add(Record entity) {
        LOGGER.info("Creating new record");
        LOGGER.debug(Parser.entityToString(entity));

        RecordRequest request = new RecordRequest().manual(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Record record = JsonConverter.readEntityFromResponse(response, Record.class, "result");
        Entities.getRecords().addOrUpdateEntity(record);

        return new OperationResult<>(response, record);
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
        LOGGER.info("Search records by filter:" + JsonConverter.toJsonString(filter));

        RecordRequest request = new RecordRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);

        RecordSearchResult searchResults = JsonConverter.readEntityFromResponse(response, RecordSearchResult.class);
        EntityList<Record> records = searchResults.getResult();

        LOGGER.info("Founded " + records.size() + " records");
        return new OperationResult<>(response, records);
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