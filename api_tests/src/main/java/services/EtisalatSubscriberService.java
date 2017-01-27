package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.SearchResult;
import app_context.RunContext;
import file_generator.FileGenerator;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.phonebook.EtisalatSubscriberRequest;
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
    private static RunContext context = RunContext.get();

    /**
     * ADD Etisalat Subscriber entry
     * API: POST /etisalat-subscriber-data/upload uploadMultipart
     *
     * @param entity Etisalat Subscriber entry
     * @return HTTP status code
     */
    @Override
    public OperationResult<EtisalatSubscriberEntry> add(EtisalatSubscriberEntry  entity) {
        log.info("Add Etisalat Subscriber entry..");
        log.debug(Parser.entityToString(entity));
        List<EtisalatSubscriberEntry> entries = new ArrayList<>();
        entries.add(entity);
        G4Response response = upload(entries);
        return new OperationResult<>(response, entity);
    }
    
    public OperationResult<List<EtisalatSubscriberEntry>> add(List<EtisalatSubscriberEntry> entities) {
        log.info("Adding Etisalat Subscriber entries..");
        log.debug(Parser.entityToString(entities));
        G4Response response = upload(entities);
        return new OperationResult<>(response, entities);
    }
    

    /**
     * UPLOAD list of Etisalat subscriber entries
     * POST /etisalat-subscriber-data/upload uploadMultipart
     *
     * @param entries of Etisalat subscriber entries
     * @return HTTP status code
     */
    private G4Response upload(List<EtisalatSubscriberEntry> entries) {
        log.info("Writing Etisalat Subscriber entries to file..");
        G4File file = new FileGenerator(EtisalatSubscriberEntry.class).write(entries);

        log.info("Upload file with " + entries.size() + " Etisalat Subscriber entries..");
        EtisalatSubscriberRequest request = new EtisalatSubscriberRequest().upload(file);
        G4Response response = g4HttpClient.sendRequest(request);

        UploadResult uploadResult = JsonConverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            //TODO: get rid of context manipulation in services
            context.put("uploadResult", uploadResult);
        }
        return response;
    }

    @Override
    public OperationResult remove(EtisalatSubscriberEntry entity) {
        throw new NotImplementedException();
    }

    /**
     * GET list of Etisalat subscriber entries
     * API: POST /etisalat-subscriber-data/search search
     *
     * @param filter search filter for payload
     * @return EntityList of Etisalat subscriber
     */
    @Override
    public OperationResult<EntityList<EtisalatSubscriberEntry>> list(SearchFilter filter) {
        log.info("Getting list of Etisalat Subscriber enries..");
        EtisalatSubscriberRequest request = new EtisalatSubscriberRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);

        SearchResult<EtisalatSubscriberEntry> searchResults =
                JsonConverter.readEntityFromResponse(response, EtisalatSubscriberSearchResult.class, "result");
        EntityList<EtisalatSubscriberEntry> etisalatSubscriberEntries;
        if (searchResults != null) {
            etisalatSubscriberEntries =  new EntityList<>(searchResults.getContent());
        } else {
            throw new RuntimeException("Unable to read search results from Etisalat Subscriber search");
        }
        return new OperationResult<>(response, etisalatSubscriberEntries);
    }

    @Override
    public OperationResult<EtisalatSubscriberEntry> update(EtisalatSubscriberEntry entity) {
        throw new NotImplementedException();
    }

    /**
     * GET Etisalat subscriber entry
     * API: GET /etisalat-subscriber-data/entries/{id} getEntry
     *
     * @param id id of entity
     * @return Etisalat subscriber entry
     */
    @Override
    public OperationResult<EtisalatSubscriberEntry> view(String id) {
        log.info("Getting derails of Etisalat Subscriber entry by id: " + id);

        EtisalatSubscriberRequest request = new EtisalatSubscriberRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);
    
        EtisalatSubscriberEntry entry = JsonConverter.readEntityFromResponse(response, EtisalatSubscriberEntry.class, "result");
        return new OperationResult<>(response, entry);
    }

}
