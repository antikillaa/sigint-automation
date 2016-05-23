package conditions;

import abs.TeelaEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import static java.lang.Boolean.TRUE;

public class EqualCondition implements ExpectedConditions {

    private Object arg1;
    private Object arg2;


    public EqualCondition(Object obj1, Object obj2) {
        this.arg1 = obj1;
        this.arg2 = obj2;


    }

    public Boolean check() {
        return execute(arg1, arg2);
    }




    private Boolean execute(Set set1, Set set2) {
        if ((set1==null || set1.size()==0) && (set2==null || set2.size()==0)) {
                return true;
        }
        return set1.equals(set2);
    }

    private Boolean execute(Object obj1, Object obj2) {
        return obj1.equals(obj2);
    }

    private <T extends TeelaEntity> Boolean execute(T obj1, T obj2) {
        Boolean equals = TRUE;
        for (Field field: obj2.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String originalValue;
            String requestValue;
            try {
                originalValue = BeanUtils.getProperty(obj1, field.getName());
                requestValue = BeanUtils.getProperty(obj2, field.getName());
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



