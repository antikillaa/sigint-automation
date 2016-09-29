package abs;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import static java.lang.Boolean.TRUE;

public abstract class TeelaEntity {

    private String id;
    private Date createdAt;
    private Date modifiedAt;
    private String modifiedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public <T extends TeelaEntity> Boolean equals(T object)  {
        Boolean equals = TRUE;
        for (Field field: object.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String originalValue;
            String requestValue;
            try {
                originalValue = BeanUtils.getProperty(this, field.getName());
                requestValue = BeanUtils.getProperty(object, field.getName());
            } catch (Exception e) {
                throw new AssertionError();
            }
            if (originalValue == null && requestValue == null) {
                continue;
            }
            equals = originalValue.equals(requestValue);
            if (!equals) {
                return equals;
            }
        }
        return equals;
}
}
