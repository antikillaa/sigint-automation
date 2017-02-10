package abs;

import data_for_entity.annotations.DataIgnore;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static java.lang.Boolean.TRUE;

public abstract class AbstractEntity {

    @DataIgnore
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public <T extends TeelaEntity> Boolean equals(T object) {
        Boolean equals = TRUE;
        for (Field field : object.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String originalValue;
            String requestValue;
            try {
                originalValue = BeanUtils.getProperty(this, field.getName());
                requestValue = BeanUtils.getProperty(object, field.getName());
            } catch (Exception e) {
                throw new AssertionError(e.getMessage(), e);
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
