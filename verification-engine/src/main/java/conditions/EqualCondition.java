package conditions;

import abs.TeelaEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import static java.lang.Boolean.TRUE;

public class EqualCondition extends ExpectedCondition {

    private ExpectedCondition condition;

    @Override
    public String toString() {
        return condition.toString();
    }




    public EqualCondition elements(Object obj1, Object obj2) {
        condition = new ObjectEqualCondition(obj1, obj2);
        return this;

    }

    public EqualCondition elements(Set set1, Set set2) {
        condition = new SetEqualCondition(set1, set2);
        return this;
    }

    public <T extends TeelaEntity> EqualCondition elements(T obj1, T obj2) {
        condition = new TeelaEntityEqualCondition<T>(obj1, obj2);
        return this;
    }


    public Boolean check() {
        return condition.check();
    }

    private class SetEqualCondition extends ExpectedCondition {
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

    private class ObjectEqualCondition extends ExpectedCondition {
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

        protected Boolean check() {
            return obj1.equals(obj2);
        }
    }

   private class TeelaEntityEqualCondition<T extends TeelaEntity> extends ExpectedCondition {
       private T obj1;
       private T obj2;

       private  TeelaEntityEqualCondition(T obj1, T obj2) {
           this.obj1 = obj1;
           this.obj2 = obj2;

       }

       public String toString() {
           return String.format("Compare two Teela entities: %s and %s", obj1.toString(), obj2.toString());
       }

       protected Boolean check() {
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
                   throw new AssertionError(e.getMessage());
               }
               if ((originalValue == null || originalValue.equals(""))  && (requestValue == null || requestValue.equals(""))) {
                   continue;
               }
               equals = originalValue.trim().equals(requestValue.trim());
               if (!equals) {
                   return equals;
               }
           }
           return equals;
       }
       }
}



