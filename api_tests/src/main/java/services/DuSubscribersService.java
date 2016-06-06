package services;

import abs.EntityList;
import abs.SearchFilter;
import http.requests.phonebook.DuSubscribersRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.DuSubscriberEntry;
import model.phonebook.DuSubscribersUploadResult;
import org.apache.log4j.Logger;
import service.EntityService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class DuSubscribersService implements EntityService<DuSubscriberEntry> {

    private Logger log = Logger.getLogger(DuSubscribersService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    @Override
    public int add(DuSubscriberEntry entity) {
        log.info("Upload new DuSubscriber Entry");
        DuSubscribersRequest request = new DuSubscribersRequest().upload();
        try {
            log.debug("Writing DuSubscribersEntry to csv file...");
            File file = File.createTempFile("DuSubscribersEntry", ".csv");
            writeEntryToFile(file, entity);
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

        DuSubscribersUploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, DuSubscribersUploadResult.class, "result");
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
        return null;
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

    private void writeEntryToFile(File file, DuSubscriberEntry entry) {
        Writer writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(entryToString(entry));
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                }
            }
        }
    }

}
