package abs;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static java.lang.Boolean.TRUE;

public abstract class TeelaEntity {

    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract <T extends TeelaEntity> T generate();

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
