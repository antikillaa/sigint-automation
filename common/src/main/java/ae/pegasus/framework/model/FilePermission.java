package ae.pegasus.framework.model;

import ae.pegasus.framework.data_for_entity.annotations.DataIgnore;
import ae.pegasus.framework.data_for_entity.annotations.DataProvider;
import ae.pegasus.framework.data_for_entity.data_providers.profiler.ClassificationProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class FilePermission {

    @DataIgnore
    private String actionPermission;
    @DataProvider(ClassificationProvider.class)
    private String classification;
    @DataIgnore
    private String dataSources;
    @DataIgnore
    private String orgUnit;

    public String getActionPermission() {
        return actionPermission;
    }

    public void setActionPermission(String actionPermission) {
        this.actionPermission = actionPermission;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }
}
