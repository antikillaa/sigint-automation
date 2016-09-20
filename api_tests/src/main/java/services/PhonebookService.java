package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.RunContext;
import app_context.entities.Entities;
import errors.NullReturnException;
import file_generator.PhoneBookFile;
import http.G4HttpClient;
import http.G4Response;
import http.requests.phonebook.PhonebookRequest;
import http.requests.phonebook.UnifiedPhonebookSearchRequest;
import json.JsonCoverter;
import model.G4File;
import model.Phonebook;
import model.UploadResult;
import model.phonebook.PhonebookSearchResults;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;

public class PhonebookService implements EntityService<Phonebook>{

    private Logger log = Logger.getLogger(PhonebookService.class);
    private RunContext context = RunContext.get();
    private static G4HttpClient g4HttpClient = new G4HttpClient();

    public int add(Phonebook entity) {
        log.info("Creating new Phonebook entry");
        log.debug(Parser.entityToString(entity));

        PhonebookRequest request = new PhonebookRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Phonebook createdPhonebook = JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
        if (createdPhonebook != null) {
            Entities.getPhonebooks().addOrUpdateEntity(createdPhonebook);
        }
        return response.getStatus();
    }

    public int remove(Phonebook entity) {
        log.info("Delete Phonebook entry id:" + entity.getId());
        PhonebookRequest request = new PhonebookRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);
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
        UnifiedPhonebookSearchRequest request = new UnifiedPhonebookSearchRequest(filter);
        G4Response response = g4HttpClient.sendRequest(request);

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
        log.info("Updating Phonebook entry id:" + entity.getId());
        PhonebookRequest request = new PhonebookRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Phonebook createdPhonebook = JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
        if (createdPhonebook != null) {
            Entities.getPhonebooks().addOrUpdateEntity(createdPhonebook);
        }
        return response.getStatus();
    }

    public Phonebook view(String id) {
        log.info("View Phonebook entry id:" + id);

        PhonebookRequest request = new PhonebookRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        return JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
    }

    public int upload(List<Phonebook> phonebooks) {
        log.info("Writing Phonebook entries to file..");
        G4File file = new PhoneBookFile().write(phonebooks);

        log.info("Upload file with " + phonebooks.size() + " Phonebook entries..");
        PhonebookRequest request = new PhonebookRequest().upload(file);
        G4Response response = g4HttpClient.sendRequest(request);

        UploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

}
