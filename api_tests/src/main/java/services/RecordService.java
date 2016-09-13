package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import http.requests.RecordRequest;
import json.JsonCoverter;
import model.AppContext;
import model.Record;
import model.RecordSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

public class RecordService implements EntityService<Record> {

    private Logger log = Logger.getLogger(RecordService.class);
    private static G4Client g4Client = new G4Client();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    public int add(Record entity) {
        log.info("Creating new record");
        try {
            log.debug("Record: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("This is not a record");
        }

        RecordRequest request = new RecordRequest().manual();
        G4Response response = g4Client.post(sigintHost + request.getURI(), entity, request.getCookie());
        Record record = JsonCoverter.readEntityFromResponse(response, Record.class, "result");
        if (record != null) {
            context.entities().getRecords().addOrUpdateEntity(record);
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
        RecordRequest request = new RecordRequest().search();
        G4Response response = g4Client.post(sigintHost + request.getURI(), filter, request.getCookie());

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