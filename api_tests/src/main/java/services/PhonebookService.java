package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.RunContext;
import app_context.entities.Entities;
import errors.NullReturnException;
import file_generator.FileGenerator;
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

public class PhonebookService implements EntityService<Phonebook> {

    private Logger log = Logger.getLogger(PhonebookService.class);
    private RunContext context = RunContext.get();
    private static G4HttpClient g4HttpClient = new G4HttpClient();

    /*
        TODO
        POST /phonebook/search search

        GET /phonebook/sources getAll
        POST /phonebook/sources create
        POST /phonebook/sources/search search
        DELETE /phonebook/sources/{id} delete
        GET /phonebook/sources/{id} get
        POST /phonebook/sources/{id} update
        POST /phonebook/sources/{id}/undelete undelete

        PUT /phonebook/uploadBinary uploadBinary
    */

    /**
     * POST /phonebook/entries addEntry
     *
     * @param entity Phonebook entry
     * @return * @return HTTP status code
     */
    @Override
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

    /**
     * DELETE /phonebook/entries/{id} deleteEntry
     *
     * @param entity Phonebook entity
     * @return HTTP status code
     */
    @Override
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

    /**
     * GET list of Unified Phonebook subscriber entries: Phonebook, Du and Etisalat subscriber.
     * API: POST /unified-phonebook/search search
     *
     * @param filter search filter for payload
     * @return EntityList of Unified Phonebook subscriber entries
     */
    @Override
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

    /**
     * POST /phonebook/entries/{id} updateEntry
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
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

    /**
     * GET /phonebook/entries/{id} getEntry
     *
     * @param id id of entity
     * @return Phonebook entry
     */
    @Override
    public Phonebook view(String id) {
        log.info("View Phonebook entry id:" + id);

        PhonebookRequest request = new PhonebookRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        return JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
    }

    /**
     * POST /phonebook/upload uploadMultipart
     *
     * @param phonebooks list of Phonebook entries
     * @return HTTP status code
     */
    public int upload(List<Phonebook> phonebooks) {
        log.info("Writing Phonebook entries to file..");
        G4File file = new FileGenerator(Phonebook.class).write(phonebooks);

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
