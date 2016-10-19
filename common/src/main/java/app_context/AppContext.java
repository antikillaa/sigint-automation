package app_context;

import json.JsonConverter;
import model.Dictionary;
import model.LoggedUser;
import services.DictionaryService;

import java.util.Map;


public class AppContext {
    
    private static AppContext instance;
    private Map<String, String> countries;
    private LoggedUser loggedUser;
    private Dictionary dictionary;
    private Map<String, String> languages;
    
    
    private AppContext(){}
    
    public static AppContext get() {
        if (instance == null) {
            instance = new AppContext();}
        return instance;
    }
    
    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }
    
    
    public Map<String, String> getLanguages() {
        if (languages == null) {
            languages = JsonConverter.loadJsonToStringMap("languages.json");
        }
        return languages;
    }

    public Map<String, String> getCountries() {
        if (countries == null) {
            countries = JsonConverter.loadJsonToStringMap("countries.json");
        }
        return countries;
    }
    
    public Dictionary getDictionary() {
        if (dictionary == null) {
            dictionary = DictionaryService.loadDictionary();
        }
        return dictionary;
    }
    
}

