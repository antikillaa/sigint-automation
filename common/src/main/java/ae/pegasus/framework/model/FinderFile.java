package ae.pegasus.framework.model;

import ae.pegasus.framework.data_for_entity.annotations.DataIgnore;
import ae.pegasus.framework.data_for_entity.annotations.DataProvider;
import ae.pegasus.framework.data_for_entity.annotations.WithCollectionSize;
import ae.pegasus.framework.data_for_entity.data_providers.profiler.ProfileAndTargetGroupNameProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class FinderFile extends AbstractEntity {

    @DataIgnore
    private ProfileJsonType type = ProfileJsonType.File;
    @DataIgnore
    private ProfileJsonType baseType = ProfileJsonType.File;
    @WithCollectionSize(1)
    private ArrayList<FilePermission> reqPermissions = new ArrayList<>();
    @DataProvider(ProfileAndTargetGroupNameProvider.class)
    private String name;
    private String description;
    @DataIgnore
    private Boolean empty;
    @DataIgnore
    private Object aggregatedTypeCounts;
    @DataIgnore
    private Date creationTime;
    @DataIgnore
    private String creator;
    @DataIgnore
    private ArrayList<ParentChain> parentChain = new ArrayList<>();
    @DataIgnore
    private String parentFileId;
    @DataIgnore
    private Boolean hasContents;
    @DataIgnore
    private Boolean deleted = false;
    @DataIgnore
    private FinderFile payload;
    @DataIgnore
    private List<String> files = new ArrayList<>();

    public ProfileJsonType getType() {
        return type;
    }

    public void setType(ProfileJsonType type) {
        this.type = type;
    }

    public ProfileJsonType getBaseType() {
        return baseType;
    }

    public void setBaseType(ProfileJsonType baseType) {
        this.baseType = baseType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public Object getAggregatedTypeCounts() {
        return aggregatedTypeCounts;
    }

    public void setAggregatedTypeCounts(Object aggregatedTypeCounts) {
        this.aggregatedTypeCounts = aggregatedTypeCounts;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<ParentChain> getParentChain() {
        return parentChain;
    }

    public void setParentChain(ArrayList<ParentChain> parentChain) {
        this.parentChain = parentChain;
    }

    public String getParentFileId() {
        return parentFileId;
    }

    public void setParentFileId(String parentFileId) {
        this.parentFileId = parentFileId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getHasContents() {
        return hasContents;
    }

    public void setHasContents(Boolean hasContents) {
        this.hasContents = hasContents;
    }

    public ArrayList<FilePermission> getReqPermissions() {
        return reqPermissions;
    }

    public void setReqPermissions(ArrayList<FilePermission> reqPermissions) {
        this.reqPermissions = reqPermissions;
    }

    public FinderFile getPayload() {
        return payload;
    }

    public void setPayload(FinderFile payload) {
        this.payload = payload;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public static class ParentChain extends AbstractEntity {

        private String name;

        public ParentChain(){
        }

        public ParentChain(String id, String name) {
            this.setId(id);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
