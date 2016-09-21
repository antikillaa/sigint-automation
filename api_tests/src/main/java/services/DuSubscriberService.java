package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.SearchResult;
import app_context.RunContext;
import app_context.properties.G4Properties;
import errors.NullReturnException;
import file_generator.DuFile;
import http.G4HttpClient;
import http.G4Response;
import http.requests.phonebook.DuSubscriberRequest;
import json.JsonCoverter;
import model.DuSubscriberEntry;
import model.G4File;
import model.UploadResult;
import model.phonebook.DuSubscriberSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DuSubscriberService implements EntityService<DuSubscriberEntry> {

    private Logger log = Logger.getLogger(DuSubscriberService.class);
    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private RunContext context = RunContext.get();
    private final String sigintHost = G4Properties.getRunProperties().getApplicationURL();

    public int add(DuSubscriberEntry entity) {
        log.info("Add DuSubscriber Entry..");
        List<DuSubscriberEntry> entries = new ArrayList<>();
        entries.add(entity);
        return upload(entries);
    }

    public int upload(List<DuSubscriberEntry> entries) {
        log.debug("Writing DuSubscriberEntry to csv file..");
        G4File file = new DuFile().write(entries);

        log.info("Upload file: " + file.getName() + " with " + entries.size() + " DuSubscriber entries..");
        DuSubscriberRequest request = new DuSubscriberRequest().upload(file);
        G4Response response = g4HttpClient.sendRequest(request);

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
        log.info("Getting list of DuSubscriber entries..");

        DuSubscriberRequest request = new DuSubscriberRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);

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
        log.info("Getting derails of DuSubscriber Entry by id: " + id);

        DuSubscriberRequest request = new DuSubscriberRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        return JsonCoverter.readEntityFromResponse(response, DuSubscriberEntry.class, "result");
    }

}
