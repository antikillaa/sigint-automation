package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.SearchResult;
import app_context.RunContext;
import errors.NullReturnException;
import file_generator.FileGenerator;
import http.G4HttpClient;
import http.G4Response;
import http.requests.phonebook.EtisalatSubscriberRequest;
import json.JsonConverter;
import model.EtisalatSubscriberEntry;
import model.G4File;
import model.UploadResult;
import model.phonebook.EtisalatSubscriberSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;

public class EtisalatSubscriberService implements EntityService<EtisalatSubscriberEntry> {

    private Logger log = Logger.getLogger(EtisalatSubscriberService.class);
    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private static RunContext context = RunContext.get();

    /**
     * ADD Etisalat Subscriber entry
     * API: POST /etisalat-subscriber-data/upload uploadMultipart
     *
     * @param entity Etisalat Subscriber entry
     * @return HTTP status code
     */
    @Override
    public int add(EtisalatSubscriberEntry entity) {
        log.info("Add Etisalat Subscriber entry..");
        log.debug(Parser.entityToString(entity));

        List<EtisalatSubscriberEntry> entries = new ArrayList<>();
        entries.add(entity);
        return upload(entries);
    }

    /**
     * UPLOAD list of Etisalat subscriber entries
     * POST /etisalat-subscriber-data/upload uploadMultipart
     *
     * @param entries of Etisalat subscriber entries
     * @return HTTP status code
     */
    public int upload(List<EtisalatSubscriberEntry> entries) {
        log.info("Writing Etisalat Subscriber entries to file..");
        G4File file = new FileGenerator(EtisalatSubscriberEntry.class).write(entries);

        log.info("Upload file with " + entries.size() + " Etisalat Subscriber entries..");
        EtisalatSubscriberRequest request = new EtisalatSubscriberRequest().upload(file);
        G4Response response = g4HttpClient.sendRequest(request);

        UploadResult uploadResult = JsonConverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

    @Override
    public int remove(EtisalatSubscriberEntry entity) {
        return 0;
    }

    /**
     * GET list of Etisalat subscriber entries
     * API: POST /etisalat-subscriber-data/search search
     *
     * @param filter search filter for payload
     * @return EntityList of Etisalat subscriber
     */
    @Override
    public EntityList<EtisalatSubscriberEntry> list(SearchFilter filter) {
        log.info("Getting list of Etisalat Subscriber enries..");

        EtisalatSubscriberRequest request = new EtisalatSubscriberRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);

        SearchResult<EtisalatSubscriberEntry> searchResults =
                JsonConverter.readEntityFromResponse(response, EtisalatSubscriberSearchResult.class, "result");
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

    /**
     * GET Etisalat subscriber entry
     * API: GET /etisalat-subscriber-data/entries/{id} getEntry
     *
     * @param id id of entity
     * @return Etisalat subscriber entry
     */
    @Override
    public EtisalatSubscriberEntry view(String id) {
        log.info("Getting derails of Etisalat Subscriber entry by id: " + id);

        EtisalatSubscriberRequest request = new EtisalatSubscriberRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        return JsonConverter.readEntityFromResponse(response, EtisalatSubscriberEntry.class, "result");
    }

}
