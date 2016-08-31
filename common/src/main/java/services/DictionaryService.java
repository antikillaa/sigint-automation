package services;

import app_context.properties.G4Properties;
import http.requests.GetDictionariesRequest;
import json.JsonCoverter;
import json.RsClient;
import model.Dictionary;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;

public class DictionaryService {
    
    private static Logger logger = Logger.getLogger(DictionaryService.class);
    
    public static Dictionary loadDictionary() {
        Dictionary dictionary = null;
        GetDictionariesRequest request = new GetDictionariesRequest();
        Response response = new RsClient().get(G4Properties.getRunProperties().getApplicationURL()+ request.getURI(),
                        request.getCookie());
        try {
            dictionary = JsonCoverter.readEntityFromResponse(response, Dictionary.class, "result");
        } catch (Exception ex) {
            logger.error("Error occurred getting dictionary. User must be logged in to get it");
        }
        return dictionary;
    }
}
