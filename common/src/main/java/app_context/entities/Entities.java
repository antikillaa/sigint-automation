package app_context.entities;

public class Entities {
    
    
    private static Entities instance;
    private RFIList RFIs;
    private UsersList users;
    private TargetGroupsList targetGroups;
    private TargetsList targets;
    private PhonebookList phonebooks;
    private DuSubscriberList duSubscriberses;
    private RoleList roles;
    private GroupList groups;
    private RecordList records;
    private ReportList reports;
    private SourceList sources;
    
    
    private Entities(){}
    
    public static Entities get() {
        if (instance == null){
            instance = new Entities();
        }
        return instance;
    }
    
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
    
    public RecordList getRecords() {
        if (records == null) {
            records = new RecordList();
        }
        return records;
    }
    
    public void setRecords(RecordList records) {
        this.records = records;
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
    
    public GroupList getGroups() {
        if (groups == null) {
            groups = new GroupList();
        }
        return groups;
    }
    
    public void setGroups(GroupList groups) {
        this.groups = groups;
    }
    
    public ReportList getReports() {
        if (reports == null) {
            reports = new ReportList();
        }
        return reports;
    }
    
    public void setReports(ReportList reports) {
        this.reports = reports;
    }
    
    public SourceList getSources() {
        if (sources == null) {
            sources = new SourceList();
        }
        return sources;
    }
    
    public void setSources(SourceList sources) {
        this.sources = sources;
    }
    
    
    
}
