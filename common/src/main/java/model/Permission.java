package model;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    private ArrayList<PermissionType> titles = new ArrayList<>();
    private ArrayList<PermissionType> actions = new ArrayList<>();
    private PermissionRecord record;

    public ArrayList<PermissionType> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<PermissionType> titles) {
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

    class PermissionRecord {

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
