package model;

import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.WithFieldDataType;
import data_for_entity.data_types.FieldDataType;

import java.util.Date;

public class BaseEntity extends AbstractEntity {

    @WithFieldDataType(FieldDataType.DATE)
    private Date createdAt;
    @DataIgnore
    private String createdBy;
    @DataIgnore
    private Date modifiedAt;
    @DataIgnore
    private ModifiedBy modifiedBy;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public ModifiedBy getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(ModifiedBy modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
