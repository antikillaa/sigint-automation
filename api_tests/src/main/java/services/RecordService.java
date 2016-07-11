package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.RecordRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Record;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class RecordService implements EntityService<Record> {

    private Logger log = Logger.getLogger(RecordService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    public int add(Record entity) {
        log.info("Creating new record");
        try {
            log.info("Record: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("This is not a record");
        }

        RecordRequest request = new RecordRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());
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
        return null;
    }

    public int update(Record entity) {
        return 0;
    }

    public Record view(String id) {
        return null;
    }
}