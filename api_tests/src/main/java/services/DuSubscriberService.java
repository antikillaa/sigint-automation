package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.SearchResult;
import app_context.entities.Entities;
import file_generator.FileGenerator;
import http.G4HttpClient;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.phonebook.DuSubscriberRequest;
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

    /**
     * ADD new Du Subscriber entry.
     * Used UPLOAD API: POST /du-subscribers/upload uploadMultipart.
     *
     * @param entity Du Subscriber entry
     * @return HTTP status code
     */
    @Override
    public OperationResult<UploadResult> add(DuSubscriberEntry entity) {
        log.info("Add duSubscriber Entry..");
        List<DuSubscriberEntry> entries = new ArrayList<>();
        entries.add(entity);
        OperationResult<UploadResult> operationResult =  upload(entries);
        if (operationResult.isSuccess()) {
            Entities.getDuSubscriberses().addOrUpdateEntity(entity);
        }
        return operationResult;
    }

    /**
     * UPLOAD list of Du Subscriber entries.
     * API: POST /du-subscribers/upload uploadMultipart
     *
     * @param entries list of Du Subscriber entries.
     * @return HTTP status code
     */
    private OperationResult<UploadResult> upload(List<DuSubscriberEntry> entries) {
        log.debug("Writing DuSubscriberEntry to csv file..");
        G4File file = new FileGenerator(DuSubscriberEntry.class).write(entries);

        log.info("Upload file: " + file.getName() + " with " + entries.size() + " duSubscriber entries..");
        DuSubscriberRequest request = new DuSubscriberRequest().upload(file);
        G4Response response = g4HttpClient.sendRequest(request);
        UploadResult uploadResult = JsonConverter.readEntityFromResponse(response, UploadResult.class, "result");
        return new OperationResult<>(response, uploadResult);
    }

    @Override
    public OperationResult remove(DuSubscriberEntry entity) {
        throw new NotImplementedException();
    }

    /**
     * Search list Du Subscriber entries.
     * API: POST /du-subscribers/search search
     *
     * @param filter DuSubscriberFilter search filter
     * @return EntityList of Du Subscriber entries
     */
    @Override
    public OperationResult<EntityList<DuSubscriberEntry>> list(SearchFilter filter) {
        log.info("Getting list of duSubscriber entries..");
        DuSubscriberRequest request = new DuSubscriberRequest().search(filter);
        G4Response response = g4HttpClient.sendRequest(request);
        SearchResult<DuSubscriberEntry> searchResults =
                JsonConverter.readEntityFromResponse(response, DuSubscriberSearchResult.class, "result");
        EntityList<DuSubscriberEntry> duSubscriberEntries;
        if (searchResults != null) {
            duSubscriberEntries = new EntityList<>(searchResults.getContent());
        } else {
            throw new RuntimeException("Unable to read search results from duSubscriber search");
        }
        return new OperationResult<>(response, duSubscriberEntries);
    }

    @Override
    public OperationResult<DuSubscriberEntry> update(DuSubscriberEntry entity) {
        throw new NotImplementedException();
    }

    /**
     * GET Du Subscriber entry
     * API: GET /du-subscribers/entries/{id} getEntry
     *
     * @param id id of entry
     * @return Du Subscriber entry
     */
    @Override
    public OperationResult<DuSubscriberEntry> view(String id) {
        log.info("Getting derails of duSubscriber Entry by id: " + id);
        DuSubscriberRequest request = new DuSubscriberRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);
        DuSubscriberEntry duEntry =  JsonConverter.readEntityFromResponse(
                response, DuSubscriberEntry.class, "result");
        return new OperationResult<>(response, duEntry);
    }

}
