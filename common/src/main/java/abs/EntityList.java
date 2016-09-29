package abs;
import errors.NullReturnException;
import org.apache.commons.lang.math.RandomUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class EntityList<T extends TeelaEntity> implements Iterable<T> {

    protected List <T> entities;

    public EntityList(List<T> entities) {
        this.entities = new ArrayList<>(entities);

    }
    
    public Integer size() {
        return entities.size();
    }

    public EntityList() {
        this.entities = new ArrayList<>();
    }

    public List<T> getEntities() {
        return entities;
    }

    public Iterator<T> iterator() {

        return entities.iterator();
    }

    public void addOrUpdateEntity(T entity) {
        for (T oldEntity: entities) {
            if(oldEntity.getId() == entity.getId()) {
                updateEntity(oldEntity, entity);
                return;
            }
        }
        entities.add(entity);
    }


    public boolean contains(T searchEntity) {
        Boolean exist = false;
        for(T entity: entities) {
            if (entity.getId().equals(searchEntity.getId())) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    public void updateEntity(T oldEntity, T newEntity) {
        int index = entities.indexOf(oldEntity);
        entities.set(index, newEntity);
    }

    public void removeEntity(T entity) throws NullReturnException {
        for (T exEntity: entities) {
            if (exEntity.getId().equals(entity.getId())) {
                entities.remove(entity);
                return;
            }
        }
        throw new NullReturnException("There was no entity to delete");
    }

    public abstract T getEntity(String param) throws NullReturnException;

    public T getLatest()  {
        if (entities.size() == 0) {
            return null;
        }
        T entity =  entities.get(entities.size()-1);
        return entity;
    }

    public T random() {
        if (entities.size() == 0) {
            throw new AssertionError("There are no entities in current list");
        }
        if (entities.size() > 1) {
            return entities.get(RandomUtils.nextInt(entities.size()-1)); }
        return entities.get(0);
    }

}

