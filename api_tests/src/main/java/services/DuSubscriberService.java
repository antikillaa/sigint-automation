package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.SearchResult;
import app_context.RunContext;
import app_context.properties.G4Properties;
import errors.NullReturnException;
import file_generator.DuFile;
import http.requests.phonebook.DuSubscriberRequest;
import json.JsonCoverter;
import json.RsClient;
import model.DuSubscriberEntry;
import model.UploadResult;
import model.phonebook.DuSubscriberSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DuSubscriberService implements EntityService<DuSubscriberEntry> {

    private Logger log = Logger.getLogger(DuSubscriberService.class);
    private static RsClient rsClient = new RsClient();
    private RunContext context = RunContext.get();
    private final String sigintHost = G4Properties.getRunProperties().getApplicationURL();

    public int add(DuSubscriberEntry entity) {
        log.info("Add DuSubscriber Entry..");
        List<DuSubscriberEntry> entries = new ArrayList<>();
        entries.add(entity);
        return upload(entries);
    }

    public int upload(List<DuSubscriberEntry> entries) {
        log.debug("Writing DuSubscriberEntry to csv file...");
        File file = new DuFile().write(entries);

        log.info("Upload file with " + entries.size() + " DuSubscriber entries..");
        DuSubscriberRequest request = new DuSubscriberRequest().upload();
        request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        file.deleteOnExit();

        Entity payload = Entity.entity(request.getBody(), request.getMediaType());
        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.post(sigintHost + request.getURI(), payload, request.getCookie());

        UploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

    public int remove(DuSubscriberEntry entity) {
        return 0;
    }

    public EntityList<DuSubscriberEntry> list(SearchFilter filter) {
        DuSubscriberRequest request = new DuSubscriberRequest().search();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());

        SearchResult<DuSubscriberEntry> searchResults =
                JsonCoverter.readEntityFromResponse(response, DuSubscriberSearchResult.class, "result");
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from DuSubscriber search");
        } else {
            return new EntityList<DuSubscriberEntry>(searchResults.getContent()) {
                public DuSubscriberEntry getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        }
    }

    public int update(DuSubscriberEntry entity) {
        return 0;
    }

    public DuSubscriberEntry view(String id) {
        DuSubscriberRequest request = new DuSubscriberRequest().get(id);
        log.info("Getting derails of DuSubscriber Entry by id: " + id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());
        return JsonCoverter.readEntityFromResponse(response, DuSubscriberEntry.class, "result");
    }

}
