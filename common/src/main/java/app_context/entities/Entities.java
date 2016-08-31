package app_context.entities;

import abs.EntityList;
import errors.NullReturnException;
import json.JsonCoverter;
import model.*;
import org.apache.log4j.Logger;

import java.io.InputStream;

public class Entities {
    
    private static Logger logger = Logger.getLogger(Entities.class);
    private static RFIList RFIs;
    private static UsersList users;
    private static TargetGroupsList targetGroups;
    private static TargetsList targets;
    private static PhonebookList phonebooks;
    private static DuSubscriberList duSubscriberses;
    private static RoleList roles;
    private static GroupList groups;
    private static RecordList records;
    private static ReportList reports;
    private static SourceList sources;
    
    
    public static EntityList<Target> getTargets() {
        if (targets == null) {
            targets = new TargetsList();
        }
        return targets;
    }
    
    public static EntityList<TargetGroup> getTargetGroups() {
        if (targetGroups==null){
            targetGroups = new TargetGroupsList();
        }
        return targetGroups;
    }
    
    private static UsersList initDefaultUsers() {
        UsersList users = null;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream users_input = classloader.getResourceAsStream("default_users.json");
        try {
            users = JsonCoverter.fromJsonToObjectsList(users_input, UsersList.class);
            
        } catch (NullReturnException e) {
            logger.warn("Users weren't loaded from config file");
        }
        return users;
    }
    
    public static EntityList<User> getUsers() {
        if (users==null) {
            users = initDefaultUsers();
        }
        return users;
    }
    
    public static EntityList<InformationRequest> getRFIs() {
        if (RFIs == null){
            RFIs = new RFIList();
        }
        return RFIs;
    }
    
    
    public static EntityList<Phonebook> getPhonebooks() {
        if (phonebooks == null) {
            phonebooks = new PhonebookList();
        }
        return phonebooks;
    }
    
    public static EntityList<DuSubscriberEntry> getDuSubscriberses() {
        if (duSubscriberses == null) {
            duSubscriberses = new DuSubscriberList();
        }
        return duSubscriberses;
    }
    
    public static EntityList<Record> getRecords() {
        if (records == null) {
            records = new RecordList();
        }
        return records;
    }
    
    public static EntityList<Role> getRoles() {
        if ( roles == null ) {
            roles = new RoleList();
        }
        return roles;
    }
    
    public static EntityList<Group> getGroups() {
        if (groups == null) {
            groups = new GroupList();
        }
        return groups;
    }
    
    public static EntityList<Report> getReports() {
        if (reports == null) {
            reports = new ReportList();
        }
        return reports;
    }
    
    public static EntityList<Source> getSources() {
        if (sources == null) {
            sources = new SourceList();
        }
        return sources;
    }
    
}
