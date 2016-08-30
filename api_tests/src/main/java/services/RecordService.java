package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import app_context.properties.G4Properties;
import errors.NullReturnException;
import http.requests.RecordRequest;
import json.JsonCoverter;
import json.RsClient;
import model.Record;
import model.RecordSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class RecordService implements EntityService<Record> {

    private Logger log = Logger.getLogger(RecordService.class);
    private static RsClient rsClient = new RsClient();
    private final String sigintHost = G4Properties.getRunProperties().getApplicationURL();

    public int add(Record entity) {
        log.info("Creating new record");
        try {
            log.debug("Record: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("This is not a record");
        }

        RecordRequest request = new RecordRequest().manual();
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());
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
        RecordRequest request = new RecordRequest().search();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());

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