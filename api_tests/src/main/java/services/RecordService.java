package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import errors.NullReturnException;
import http.G4HttpClient;
import http.G4Response;
import http.requests.RecordRequest;
import json.JsonConverter;
import model.Record;
import model.RecordSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

public class RecordService implements EntityService<Record> {

    private Logger log = Logger.getLogger(RecordService.class);
    private static G4HttpClient g4HttpClient = new G4HttpClient();

    /**
     * ADD new manual record
     * API: POST /api/sigint/record/manual
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public int add(Record entity) {
        log.info("Creating new record");
        log.debug(Parser.entityToString(entity));

        RecordRequest request = new RecordRequest().manual(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Record record = JsonConverter.readEntityFromResponse(response, Record.class, "result");
        if (record != null) {
            Entities.getRecords().addOrUpdateEntity(record);
        } else {
            log.error("Add new record process was failed");
            throw new AssertionError("Add new record process was failed");
        }
        return response.getStatus();
    }

    @Override
    public int remove(Record entity) {
        return 0;
    }

    /**
     * Search list of records
     * API: POST "/api/sigint/record/search?withTargets=true"
     *
     * @param filter search filter for payload
     * @return EntityList of record
     */
    @Override
    public EntityList<Record> list(SearchFilter filter) {
        RecordRequest request = new RecordRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);

        RecordSearchResult searchResults = JsonConverter.readEntityFromResponse(response, RecordSearchResult.class);
        if (searchResults == null) {
            throw new AssertionError("Unable to read search records results");
        } else {
            return new EntityList<Record>(searchResults.getResult()) {
                public Record getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        }
    }

    @Override
    public int update(Record entity) {
        return 0;
    }

    @Override
    public Record view(String id) {
        return null;
    }
}