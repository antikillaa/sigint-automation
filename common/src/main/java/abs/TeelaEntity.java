package abs;

import data_for_entity.annotations.DataIgnore;
import data_for_entity.annotations.WithFieldDataType;
import data_for_entity.data_types.FieldDataType;

import java.util.Date;

public abstract class TeelaEntity extends AbstractEntity {

    @WithFieldDataType(FieldDataType.DATE)
    private Date createdAt;
    @DataIgnore
    private Date modifiedAt;
    @DataIgnore
    private String modifiedBy;

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

}
