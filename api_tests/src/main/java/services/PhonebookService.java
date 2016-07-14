package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.phonebook.PhonebookEntriesRequest;
import http.requests.phonebook.UnifiedPhonebookSearchRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Phonebook;
import model.phonebook.PhonebookSearchResults;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class PhonebookService implements EntityService<Phonebook>{

    private Logger log = Logger.getLogger(PhonebookService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    public int add(Phonebook entity) {
        PhonebookEntriesRequest request = new PhonebookEntriesRequest();
        log.info("Creating new Phonebook entry");
        try {
            log.info("entite: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            e.printStackTrace();
        }
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());
        Phonebook createdPhonebook = JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
        if (createdPhonebook != null) {
            context.entities().getPhonebooks().addOrUpdateEntity(createdPhonebook);
        }
        return response.getStatus();
    }

    public int remove(Phonebook entity) {
        PhonebookEntriesRequest request = new PhonebookEntriesRequest();
        log.info("Delete Phonebook entry id:" + entity.getId());
        Response response = rsClient.delete(sigintHost + request.getURI() + "/" + entity.getId(), request.getCookie());
        if (response.getStatus() == 200) {
            try {
                context.entities().getPhonebooks().removeEntity(entity);
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
        PhonebookEntriesRequest request = new PhonebookEntriesRequest();
        log.info("Updating Phonebook entry id:" + entity.getId());
        Response response = rsClient.post(sigintHost + request.getURI() + "/" + entity.getId(), entity, request.getCookie());
        Phonebook createdPhonebook = JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
        if (createdPhonebook != null) {
            context.entities().getPhonebooks().addOrUpdateEntity(createdPhonebook);
        }
        return response.getStatus();
    }

    public Phonebook view(String id) {
        PhonebookEntriesRequest request = new PhonebookEntriesRequest();
        log.info("View Phonebook entry id:" + id);
        Response response = rsClient.get(sigintHost + request.getURI() + "/" + id, request.getCookie());
        return JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
    }


}
