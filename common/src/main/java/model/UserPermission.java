package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserPermission {

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> actions = new ArrayList<>();
    private PermissionRecord record = new PermissionRecord();

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    public void setActions(ArrayList<String> actions) {
        this.actions = actions;
    }

    public PermissionRecord getRecord() {
        return record;
    }

    public void setRecord(PermissionRecord record) {
        this.record = record;
    }

    public class PermissionRecord {

        private List<String> clearances = new ArrayList<>();
        private List<String> dataSources = new ArrayList<>();
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
