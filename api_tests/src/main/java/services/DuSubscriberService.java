package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.SearchResult;
import errors.NullReturnException;
import http.requests.phonebook.DuSubscriberRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.DuSubscriberEntry;
import model.phonebook.DuSubscriberSearchResult;
import model.phonebook.EntriesUploadResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.FileHelper;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

public class DuSubscriberService implements EntityService<DuSubscriberEntry> {

    private Logger log = Logger.getLogger(DuSubscriberService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    public int add(DuSubscriberEntry entity) {
        log.info("Upload new DuSubscriber Entry");
        DuSubscriberRequest request = new DuSubscriberRequest().upload();
        try {
            log.debug("Writing DuSubscriberEntry to csv file...");
            File file = File.createTempFile("DuSubscriberEntry", ".csv");
            FileHelper.writeLineToFile(file, entryToString(entity));
            request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
            file.deleteOnExit();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AssertionError("Failed to create csv file");
        }

        Entity payload = Entity.entity(request.getBody(), request.getMediaType());
        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.client().target(sigintHost + request.getURI())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(request.getCookie())
                .post(payload);

        EntriesUploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, EntriesUploadResult.class, "result");
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

        SearchResult<DuSubscriberEntry> searchResults = JsonCoverter.readEntityFromResponse(response, DuSubscriberSearchResult.class, "result");
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


    private String entryToString(DuSubscriberEntry entry) {
        String DELIMETER = "~";
        return  entry.getPhoneNumber() + DELIMETER +
                entry.getTitle() + DELIMETER +
                entry.getFirstName() + DELIMETER +
                entry.getMiddleName() + DELIMETER +
                entry.getLastName() + DELIMETER +
                entry.getPoBox() + DELIMETER +
                entry.getCity() + DELIMETER +
                entry.getNationality() + DELIMETER +
                entry.getVisaType() + DELIMETER +
                entry.getVisaNumber() + DELIMETER +
                entry.getIdType() + DELIMETER +
                entry.getIdNumber() + DELIMETER +
                entry.getStatus() + DELIMETER +
                entry.getCustomerType() + DELIMETER +
                entry.getServiceType() + DELIMETER +
                entry.getCustomerCode();
    }

}
