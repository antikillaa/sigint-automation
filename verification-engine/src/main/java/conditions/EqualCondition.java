package conditions;

import abs.TeelaEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import static java.lang.Boolean.TRUE;

public class EqualCondition implements ExpectedCondition {

    private ExpectedCondition condition;

    @Override
    public String toString() {
        return condition.toString();
    }


    public EqualCondition(Object obj1, Object obj2) {
        condition = new ObjectEqualCondition(obj1, obj2);

    }

    public EqualCondition(Set set1, Set set2) {
        condition = new SetEqualCondition(set1, set2);

    }

    public <T extends TeelaEntity> EqualCondition(T obj1, T obj2) {
        condition = new TeelaEntityEqualCondition<T>(obj1, obj2);
    }

    public Boolean check() {
        return condition.check();
    }
    private class SetEqualCondition implements ExpectedCondition {
        private Set set1;
        private Set set2;

        private SetEqualCondition(Set set1, Set set2) {
            this.set1 = set1;
            this.set2 = set2;
        }

        public String toString() {
            return "Set condition with set1:"+set1+" and set2:"+set2;
        }

        public Boolean check() {
            if ((set1==null || set1.size()==0) && (set2==null || set2.size()==0)) {
                return true;
            }
            return set1.equals(set2);
        }
    }

    private class ObjectEqualCondition implements ExpectedCondition {
        private Object obj1;
        private Object obj2;

        private ObjectEqualCondition(Object obj1, Object obj2) {
            this.obj1 = obj1;
            this.obj2 = obj2;
        }

        @Override
        public String toString() {
            return "Object condition with object1:"+obj1.toString()+" and object2:"+obj2.toString();
        }

        public Boolean check() {
            return obj1.equals(obj2);
        }
    }

   private class TeelaEntityEqualCondition<T extends TeelaEntity> implements ExpectedCondition {
       private T obj1;
       private T obj2;

       private  TeelaEntityEqualCondition(T obj1, T obj2) {
           this.obj1 = obj1;
           this.obj2 = obj2;

       }

       public Boolean check() {
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
}



