package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import errors.NullReturnException;
import http.G4HttpClient;
import http.G4Response;
import http.requests.RecordRequest;
import json.JsonCoverter;
import model.Record;
import model.RecordSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

public class RecordService implements EntityService<Record> {

    private Logger log = Logger.getLogger(RecordService.class);
    private static G4HttpClient g4HttpClient = new G4HttpClient();

    public int add(Record entity) {
        log.info("Creating new record");
        log.debug(Parser.entityToString(entity));

        RecordRequest request = new RecordRequest().manual(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Record record = JsonCoverter.readEntityFromResponse(response, Record.class, "result");
        if (record != null) {
            Entities.getRecords().addOrUpdateEntity(record);
        } else {
            log.error("Add new record process was failed");
            throw new AssertionError("Add new record process was failed");
        }
        return response.getStatus();
    }

    public int remove(Record entity) {
        return 0;
    }

    public EntityList<Record> list(SearchFilter filter) {
        RecordRequest request = new RecordRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);

        RecordSearchResult searchResults = JsonCoverter.readEntityFromResponse(response, RecordSearchResult.class);
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

    public int update(Record entity) {
        return 0;
    }

    public Record view(String id) {
        return null;
    }
}