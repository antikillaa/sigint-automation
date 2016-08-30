package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.RunContext;
import app_context.entities.Entities;
import app_context.properties.G4Properties;
import errors.NullReturnException;
import file_generator.PhoneBookFile;
import http.requests.phonebook.PhonebookRequest;
import http.requests.phonebook.UnifiedPhonebookSearchRequest;
import json.JsonCoverter;
import json.RsClient;
import model.Phonebook;
import model.UploadResult;
import model.phonebook.PhonebookSearchResults;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

public class PhonebookService implements EntityService<Phonebook>{

    private Logger log = Logger.getLogger(PhonebookService.class);
    private static RsClient rsClient = new RsClient();
    private RunContext context = RunContext.get();
    private final String sigintHost = G4Properties.getRunProperties().getApplicationURL();

    public int add(Phonebook entity) {
        PhonebookRequest request = new PhonebookRequest().entries();
        log.info("Creating new Phonebook entry");
        try {
            log.debug("entity: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("Unable to parse Phonebook entity");
        }
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());
        Phonebook createdPhonebook = JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
        if (createdPhonebook != null) {
            Entities.getPhonebooks().addOrUpdateEntity(createdPhonebook);
        }
        return response.getStatus();
    }

    public int remove(Phonebook entity) {
        PhonebookRequest request = new PhonebookRequest().entries();
        log.info("Delete Phonebook entry id:" + entity.getId());
        Response response = rsClient.delete(sigintHost + request.getURI() + "/" + entity.getId(), request.getCookie());
        if (response.getStatus() == 200) {
            try {
                Entities.getPhonebooks().removeEntity(entity);
            } catch (NullReturnException e) {
                log.warn("Was unable to remove entity with id:" + entity.getId() + " as it doesn't in the list");
            }
        }
        return response.getStatus();
    }

    public EntityList<Phonebook> list(SearchFilter filter) {
        UnifiedPhonebookSearchRequest request = new UnifiedPhonebookSearchRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());
        PhonebookSearchResults searchResults = JsonCoverter.readEntityFromResponse(response, PhonebookSearchResults.class, "result");
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from Phonebook search");
        } else {
            return new EntityList<Phonebook>(searchResults.getContent()) {
                public Phonebook getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        }
    }

    public int update(Phonebook entity) {
        PhonebookRequest request = new PhonebookRequest().entries();
        log.info("Updating Phonebook entry id:" + entity.getId());
        Response response = rsClient.post(sigintHost + request.getURI() + "/" + entity.getId(), entity, request.getCookie());
        Phonebook createdPhonebook = JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
        if (createdPhonebook != null) {
            Entities.getPhonebooks().addOrUpdateEntity(createdPhonebook);
        }
        return response.getStatus();
    }

    public Phonebook view(String id) {
        PhonebookRequest request = new PhonebookRequest().entries();
        log.info("View Phonebook entry id:" + id);
        Response response = rsClient.get(sigintHost + request.getURI() + "/" + id, request.getCookie());
        return JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
    }

    public int upload(List<Phonebook> phonebooks) {
        log.info("Writing Phonebook entries to file..");
        File file = new PhoneBookFile().write(phonebooks);

        log.info("Upload file with " + phonebooks.size() + " Phonebook entries..");
        PhonebookRequest request = new PhonebookRequest().upload();
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

}
