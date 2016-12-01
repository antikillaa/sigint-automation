package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.RunContext;
import app_context.entities.Entities;
import file_generator.FileGenerator;
import http.G4HttpClient;
import http.G4Response;
import http.JsonConverter;
import http.OperationResult;
import http.requests.phonebook.PhonebookRequest;
import http.requests.phonebook.UnifiedPhonebookSearchRequest;
import model.G4File;
import model.Phonebook;
import model.UploadResult;
import model.phonebook.PhonebookSearchResults;
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
    public OperationResult<Phonebook> add(Phonebook entity) {
        log.info("Creating new Phonebook entry");
        log.debug(Parser.entityToString(entity));

        PhonebookRequest request = new PhonebookRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        Phonebook createdPhonebook = JsonConverter.readEntityFromResponse(response, Phonebook.class, "result");
        if (createdPhonebook != null) {
            Entities.getPhonebooks().addOrUpdateEntity(createdPhonebook);
        }
        return new OperationResult<>(response, createdPhonebook);
    }

    /**
     * DELETE /phonebook/entries/{id} deleteEntry
     *
     * @param entity Phonebook entity
     * @return HTTP status code
     */
    @Override
    public OperationResult remove(Phonebook entity) {
        log.info("Delete Phonebook entry id:" + entity.getId());
        PhonebookRequest request = new PhonebookRequest().delete(entity.getId());
        G4Response response = g4HttpClient.sendRequest(request);
        OperationResult operationResult = new OperationResult(response);
        if (operationResult.isSuccess()) {
            Entities.getPhonebooks().removeEntity(entity);
        }
        return operationResult;
    }

    /**
     * GET list of Unified Phonebook subscriber entries: Phonebook, Du and Etisalat subscriber.
     * API: POST /unified-phonebook/search search
     *
     * @param filter search filter for payload
     * @return EntityList of Unified Phonebook subscriber entries
     */
    @Override
    public OperationResult<EntityList<Phonebook>> list(SearchFilter filter) {
        UnifiedPhonebookSearchRequest request = new UnifiedPhonebookSearchRequest(filter);
        G4Response response = g4HttpClient.sendRequest(request);

        PhonebookSearchResults searchResults = JsonConverter.readEntityFromResponse(
                response, PhonebookSearchResults.class, "result");
        EntityList<Phonebook> phonebooks;
        if (searchResults != null) {
            phonebooks = new EntityList<>(searchResults.getContent());
        } else {
            throw new RuntimeException("Unable to read search results from Phonebook search");
        }
        return new OperationResult<>(response, phonebooks);
    }

    /**
     * POST /phonebook/entries/{id} updateEntry
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Phonebook> update(Phonebook entity) {
        log.info("Updating Phonebook entry id:" + entity.getId());
        PhonebookRequest request = new PhonebookRequest().update(entity);
        G4Response response = g4HttpClient.sendRequest(request);

        Phonebook createdPhonebook = JsonConverter.readEntityFromResponse(response, Phonebook.class, "result");
        if (createdPhonebook != null) {
            Entities.getPhonebooks().addOrUpdateEntity(createdPhonebook);
        }
        return new OperationResult<>(response, createdPhonebook);
    }

    /**
     * GET /phonebook/entries/{id} getEntry
     *
     * @param id id of entity
     * @return Phonebook entry
     */
    @Override
    public OperationResult<Phonebook> view(String id) {
        log.info("View Phonebook entry id:" + id);

        PhonebookRequest request = new PhonebookRequest().get(id);
        G4Response response = g4HttpClient.sendRequest(request);

        Phonebook phonebook= JsonConverter.readEntityFromResponse(response, Phonebook.class, "result");
        return new OperationResult<>(response, phonebook);
    }

    /**
     * POST /phonebook/upload uploadMultipart
     *
     * @param phonebooks list of Phonebook entries
     * @return HTTP status code
     */
    public OperationResult<UploadResult> upload(List<Phonebook> phonebooks) {
        log.info("Writing Phonebook entries to file..");
        G4File file = new FileGenerator(Phonebook.class).write(phonebooks);
        log.info("Upload file with " + phonebooks.size() + " Phonebook entries..");
        PhonebookRequest request = new PhonebookRequest().upload(file);
        G4Response response = g4HttpClient.sendRequest(request);
        UploadResult uploadResult = JsonConverter.readEntityFromResponse(response, UploadResult.class, "result");
        return new OperationResult<>(response,uploadResult);
    }

}
