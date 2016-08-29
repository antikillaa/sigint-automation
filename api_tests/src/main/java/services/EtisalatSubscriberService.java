package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.SearchResult;
import errors.NullReturnException;
import file_generator.EtisalatSubscriberFile;
import http.requests.phonebook.EtisalatSubscriberRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.EtisalatSubscriberEntry;
import model.UploadResult;
import model.phonebook.EtisalatSubscriberSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EtisalatSubscriberService implements EntityService<EtisalatSubscriberEntry> {

    private Logger log = Logger.getLogger(EtisalatSubscriberService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    @Override
    public int add(EtisalatSubscriberEntry entity) {
        log.info("Add Etisalat Subscriber entry..");
        List<EtisalatSubscriberEntry> entries = new ArrayList<>();
        entries.add(entity);
        return upload(entries);
    }

    public int upload(List<EtisalatSubscriberEntry> entries) {
        log.info("Writing Etisalat Subscriber entries to file..");
        File file = new EtisalatSubscriberFile().write(entries);

        log.info("Upload file with " + entries.size() + " Etisalat Subscriber entries..");
        EtisalatSubscriberRequest request = new EtisalatSubscriberRequest().upload();
        request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        file.deleteOnExit();

        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.post(sigintHost + request.getURI(), request.getBody(), request.getCookie());

        UploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

    @Override
    public int remove(EtisalatSubscriberEntry entity) {
        return 0;
    }

    @Override
    public EntityList<EtisalatSubscriberEntry> list(SearchFilter filter) {
        EtisalatSubscriberRequest request = new EtisalatSubscriberRequest().search();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());

        SearchResult<EtisalatSubscriberEntry> searchResults =
                JsonCoverter.readEntityFromResponse(response, EtisalatSubscriberSearchResult.class, "result");
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from Etisalat Subscriber search");
        } else {
            return new EntityList<EtisalatSubscriberEntry>(searchResults.getContent()) {
                public EtisalatSubscriberEntry getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        }
    }

    @Override
    public int update(EtisalatSubscriberEntry entity) {
        return 0;
    }

    @Override
    public EtisalatSubscriberEntry view(String id) {
        EtisalatSubscriberRequest request = new EtisalatSubscriberRequest().get(id);
        log.info("Getting derails of Etisalat Subscriber entry by id: " + id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());
        return JsonCoverter.readEntityFromResponse(response, EtisalatSubscriberEntry.class, "result");
    }

}
