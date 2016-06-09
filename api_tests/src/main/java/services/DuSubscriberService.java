package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.phonebook.DuSubscriberRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.DuSubscriberEntry;
import abs.SearchResult;
import model.phonebook.DuSubscriberSearchResult;
import model.phonebook.DuSubscriberUploadResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import service.EntityService;
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

    @Override
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

        DuSubscriberUploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, DuSubscriberUploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

    @Override
    public int remove(DuSubscriberEntry entity) {
        return 0;
    }

    @Override
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

    @Override
    public int update(DuSubscriberEntry entity) {
        return 0;
    }

    @Override
    public DuSubscriberEntry view(String id) {
        return null;
    }


    private String entryToString(DuSubscriberEntry entry) {
        return  entry.getPhoneNumber() + "~" +
                entry.getTitle() + "~" +
                entry.getFirstName() + "~" +
                entry.getMiddleName() + "~" +
                entry.getLastName() + "~" +
                entry.getPoBox() + "~" +
                entry.getCity() + "~" +
                entry.getNationality() + "~" +
                entry.getVisaType() + "~" +
                entry.getVisaNumber() + "~" +
                entry.getIdType() + "~" +
                entry.getIdNumber() + "~" +
                entry.getStatus() + "~" +
                entry.getCustomerType() + "~" +
                entry.getServiceType() + "~" +
                entry.getCustomerCode();
    }

}
