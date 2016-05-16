package model;
import errors.NullReturnException;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


public class AppContext {

    private List<EntityList> entityLists = new ArrayList<>();
    private Token token;
    private static AppContext instance = new AppContext();
    private Properties generalProperties = new Properties();
    private Properties jiraConnection = new Properties();
    private String sigintHost;
    private Map<String, Object> runContext = new HashMap<> ();

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
        setHost(host);
    }

    public Properties getJiraConnection() {
        return jiraConnection;
    }


    public Properties getGeneralProperties(){

        return generalProperties;
    }

    public static AppContext getContext() {

        return instance;
    }

    public void registerList(EntityList list) {

        entityLists.add(list);
    }

    public <T extends EntityList>T  getEntitiesList(Class<T> type)  {

        for (EntityList list: entityLists) {
            if (list.getClass().equals(type)){
                return type.cast(list);
            }
        }
        throw new AssertionError("Entities List with type" + type + "is not found!");

    }

    public String getToken() {
        return token.getValue();
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getHost() {return sigintHost;}

    public void setHost(String host) {this.sigintHost = host;}


    public <T>void putToRunContext(String key, T object) {
        this.runContext.put(key, object);
    }

    public <T> T getFromRunContext(String key, Class<T> classType) throws NullReturnException {
        T object = classType.cast(runContext.get(key));
        if (object == null){
            throw new NullReturnException("There is no object with type:"+ classType);
        }
        return object;
    }
}

