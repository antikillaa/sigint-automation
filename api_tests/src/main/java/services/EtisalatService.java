package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.SearchResult;
import data_generator.EtisalatFile;
import errors.NullReturnException;
import http.requests.phonebook.EtisalatRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.EtisalatEntry;
import model.UploadResult;
import model.phonebook.EtisalatSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EtisalatService implements EntityService<EtisalatEntry> {

    private Logger log = Logger.getLogger(EtisalatService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    @Override
    public int add(EtisalatEntry entity) {
        log.info("Add Etisalat Entry..");
        List<EtisalatEntry> entries = new ArrayList<>();
        entries.add(entity);
        return upload(entries);
    }

    public int upload(List<EtisalatEntry> entries) {
        log.info("Writing Etisalat entries to file..");
        File file = new EtisalatFile().write(entries);

        log.info("Upload file with " + entries.size() + "Etisalat entries..");
        EtisalatRequest request = new EtisalatRequest().upload();
        request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        file.deleteOnExit();

        Entity payload = Entity.entity(request.getBody(), request.getMediaType());
        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.client()
                .target(sigintHost + request.getURI())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(request.getCookie())
                .post(payload);

        UploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

    @Override
    public int remove(EtisalatEntry entity) {
        return 0;
    }

    @Override
    public EntityList<EtisalatEntry> list(SearchFilter filter) {
        EtisalatRequest request = new EtisalatRequest().search();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());

        SearchResult<EtisalatEntry> searchResults =
                JsonCoverter.readEntityFromResponse(response, EtisalatSearchResult.class, "result");
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from Etisalat search");
        } else {
            return new EntityList<EtisalatEntry>(searchResults.getContent()) {
                public EtisalatEntry getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        }
    }

    @Override
    public int update(EtisalatEntry entity) {
        return 0;
    }

    @Override
    public EtisalatEntry view(String id) {
        EtisalatRequest request = new EtisalatRequest().get(id);
        log.info("Getting derails of Etisalat entry by id: " + id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());
        return JsonCoverter.readEntityFromResponse(response, EtisalatEntry.class, "result");
    }

}
