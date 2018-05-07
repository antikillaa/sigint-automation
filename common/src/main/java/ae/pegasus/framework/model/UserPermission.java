package ae.pegasus.framework.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserPermission {

    private List<String> titles = new ArrayList<>();
    private List<TeamTitle> teamTitles = new ArrayList<>();
    private List<String> actions = new ArrayList<>();
    private PermissionRecord record = new PermissionRecord();

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public PermissionRecord getRecord() {
        return record;
    }

    public void setRecord(PermissionRecord record) {
        this.record = record;
    }

    public List<TeamTitle> getTeamTitles() {
        return teamTitles;
    }

    public void setTeamTitles(List<TeamTitle> teamTitles) {
        this.teamTitles = teamTitles;
    }

    public class PermissionRecord {

        private List<String> clearances = Arrays.asList("TS-OS","TS-CIO","TS-SCI");
        private List<String> dataSources = Arrays.asList("PR", "INSTAGRAM", "E", "F", "H", "RFA", "ODD_JOBS", "O", "DU",
                "UDB", "S", "T", "DARK_WEB", "RFI", "DARK_WEB_REPORTS", "ZELZAL", "SITA", "YOUTUBE", "GPLUS", "KARMA",
                "TUMBLR", "NEWS", "OperatorReport", "PHONEBOOK", "TWITTER", "FORUM");
        private List<String> organizations = new ArrayList<>();

        public List<String> getClearances() {
            return clearances;
        }

        public void setClearances(List<String> clearances) {
            this.clearances = clearances;
        }

        public List<String> getDataSources() {
            return dataSources;
        }

        public void setDataSources(List<String> dataSources) {
            this.dataSources = dataSources;
        }

        public List<String> getOrganizations() {
            return organizations;
        }

        public void setOrganizations(List<String> organizations) {
            this.organizations = organizations;
        }
    }
}
