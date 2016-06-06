package model;

import model.lists.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


public class AppContext {

    private static AppContext instance = new AppContext();
    private Properties generalProperties = new Properties();
    private Properties jiraConnection = new Properties();
    private Map<String, Object> runContext = new HashMap<>();
    private Entities entities;
    private Environment environment;

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
        private DuSubscribersList duSubscriberses;

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

        public DuSubscribersList getDuSubscriberses() {
            return duSubscriberses;
        }

        public void setDuSubscriberses(DuSubscribersList duSubscriberses) {
            this.duSubscriberses = duSubscriberses;
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

    public <T> T get(String key, Class<T> classType) {
        T object = classType.cast(runContext.get(key));
        if (object == null){
            throw new AssertionError("There is no object with type:" + classType + " by key:" + key);
        }
        return object;
    }
}

