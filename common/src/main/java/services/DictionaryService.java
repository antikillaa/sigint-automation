package services;

import http.G4HttpClient;
import http.G4Response;
import http.requests.GetDictionariesRequest;
import http.JsonConverter;
import model.Dictionary;
import org.apache.log4j.Logger;

public class DictionaryService {
    
    private static Logger logger = Logger.getLogger(DictionaryService.class);
    
    public static Dictionary loadDictionary() {
        GetDictionariesRequest request = new GetDictionariesRequest();
        G4Response response = new G4HttpClient().sendRequest(request);
        Dictionary dictionary = null;
        try {
            dictionary = JsonConverter.jsonToObject(response.getMessage(), Dictionary.class, "result");
        } catch (Exception ex) {
            logger.error("Error occurred getting dictionary. User must be logged in to get it");
        }
        return dictionary;
    }
}
