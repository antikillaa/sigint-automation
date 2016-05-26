package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.phonebook.PhonebookEntriesRequest;
import http.requests.phonebook.PhonebookSearchRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Phonebook;
import model.phonebook.PhonebookSearchResults;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.type.MapType;
import service.EntityService;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

public class PhonebookService implements EntityService<Phonebook>{

    private Logger log = Logger.getLogger(PhonebookService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    public int addNew(Phonebook entity) {
        PhonebookEntriesRequest request = new PhonebookEntriesRequest();
        log.info("Creating new Phonebook entry");
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());
        Phonebook createdPhonebook = JsonCoverter.readEntityFromResponse(response, Phonebook.class, "result");
        if (createdPhonebook != null) {
            context.entities().getPhonebooks().addOrUpdateEntity(createdPhonebook);
        }
        return response.getStatus();
    }

    public int remove(Phonebook entity) {
        return 0;
    }

    public EntityList<Phonebook> list(SearchFilter filter) {
        PhonebookSearchRequest request = new PhonebookSearchRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());

        String responseJson = response.readEntity(String.class);
        MapType mapType = JsonCoverter.constructMapTypeToValue(PhonebookSearchResults.class);
        try {
            HashMap<String, PhonebookSearchResults> searchResults = JsonCoverter.mapper.readValue(responseJson, mapType);
            return new EntityList<Phonebook>(searchResults.get("result").getContent()) {
                public Phonebook getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AssertionError("Unable to read search results from Phonebook search");
        }
    }

    public int update(Phonebook entity) {
        return 0;
    }

    public Phonebook view(String id) {
        return null;
    }


}
