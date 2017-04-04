package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Permission {

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<PermissionType> actions = new ArrayList<>();
    private PermissionRecord record;

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<PermissionType> getActions() {
        return actions;
    }

    public void setActions(ArrayList<PermissionType> actions) {
        this.actions = actions;
    }

    public PermissionRecord getRecord() {
        return record;
    }

    public void setRecord(PermissionRecord record) {
        this.record = record;
    }

    public class PermissionRecord {

        private List<String> clearances;
        private List<String> dataSources;
        private List<String> organizations;

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
