package model;

import json.JsonCoverter;
import model.lists.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.type.MapType;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class AppContext {

    private static AppContext instance = new AppContext();
    private Properties generalProperties = new Properties();
    private Properties jiraConnection = new Properties();
    private Map<String, Object> runContext = new HashMap<>();
    private Entities entities;
    private Environment environment;
    private Map<String, String> countries;
    private static Logger log = Logger.getLogger(AppContext.class);

    private static Map<String,String > loadJsonToMap(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream entityList = classloader.getResourceAsStream(filename);
        MapType mapType = JsonCoverter.constructMapTypeToValue(String.class);
        try {
            Map<String, String> entities = JsonCoverter.mapper.readValue(entityList, mapType);
            return entities;
        } catch (IOException e) {
            log.error("Cannot load list of entities");
            throw new AssertionError();
        }
    }


    public Map<String, String> getLanguages() {
        if (languages == null) {
            setLanguages(loadJsonToMap("languages.json"));
        }
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

    public Map<String, String> getCountries() {
        if (countries == null) {
            setCountries(loadJsonToMap("countries.json"));
        }
        return countries;
    }

    public void setCountries(Map<String, String> countries) {
        this.countries = countries;
    }

    private Map<String, String> languages;

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    private Dictionary dictionary;

    public Entities entities() {
        if (entities == null) {
            entities = new Entities();
        }
        return entities;
    }

    public Environment environment(){
        if (environment == null) {
            environment = new Environment();
        }
        return environment;
    }

    public class Environment {

        private Token token;
        private String sigintHost;

        public String getSigintHost() {
            return sigintHost;
        }

        public void setSigintHost(String sigintHost) {
            this.sigintHost = sigintHost;
        }

        public Token getToken() {
            return token;
        }

        public void setToken(Token token) {
            this.token = token;
        }


        private Environment() {}

    }

    public class Entities {

        private RFIList RFIs;
        private UsersList users;
        private TargetGroupsList targetGroups;
        private TargetsList targets;
        private PhonebookList phonebooks;
        private DuSubscriberList duSubscriberses;

        private RoleList roles;

        public TargetsList getTargets() {
            if (targets == null) {
                targets = new TargetsList();
            }
            return targets;
        }

        public void setTargets(TargetsList targets) {
            this.targets = targets;
        }

        public TargetGroupsList getTargetGroups() {
            if (targetGroups==null){
                targetGroups = new TargetGroupsList();
            }
            return targetGroups;
        }

        public void setTargetGroups(TargetGroupsList targetGroups) {
            this.targetGroups = targetGroups;
        }

        public UsersList getUsers() {
            if (users==null) {
                users = new UsersList();
            }
            return users;
        }

        public void setUsers(UsersList users) {
            this.users = users;
        }

        public RFIList getRFIs() {
            if (RFIs == null){
                RFIs = new RFIList();
            }
            return RFIs;
        }

        public void setRFIs(RFIList RFIs) {
            this.RFIs = RFIs;
        }

        public PhonebookList getPhonebooks() {
            if (phonebooks == null) {
                phonebooks = new PhonebookList();
            }
            return phonebooks;
        }

        public void setPhonebooks(PhonebookList phonebooks) {
            this.phonebooks = phonebooks;
        }

        public DuSubscriberList getDuSubscriberses() {
            if (duSubscriberses == null) {
                duSubscriberses = new DuSubscriberList();
            }
            return duSubscriberses;
        }

        public void setDuSubscriberses(DuSubscriberList duSubscriberses) {
            this.duSubscriberses = duSubscriberses;
        }

        public RoleList getRoles() {
            if ( roles == null ) {
                roles = new RoleList();
            }
            return roles;
        }

        public void setRoles(RoleList roles) {
            this.roles = roles;
        }

        private Entities(){}

    }

    private AppContext(){
        String host;
        InputStream general = this.getClass().getResourceAsStream("/general.properties");
        InputStream connection = getClass().getClassLoader().getResourceAsStream("jiraConnection.properties");
        try {
            generalProperties.load(general);
            jiraConnection.load(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        host = generalProperties.getProperty("sigintURL");
        try {
            host = (host.equals("")) ? "http://"+InetAddress.getLocalHost().getHostAddress() + ":81": host;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        environment().setSigintHost(host);
    }

    public Properties getJiraConnection() {return jiraConnection;}

    public Properties getGeneralProperties(){return generalProperties;}

    public static AppContext getContext() {
        return instance;
    }

    public <T>void put(String key, T object) {
        this.runContext.put(key, object);
    }

    public <T>T get(String key, Class<T> userClass) {
        Object object;
        try {
            object = runContext.get(key);
        }catch (NullPointerException e) {
            throw new AssertionError(String.format("Object with key %s doesn't exist!", key));
        }
        return userClass.cast(object);
    }
}

