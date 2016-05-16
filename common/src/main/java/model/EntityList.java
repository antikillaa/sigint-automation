package model;
import errors.NullReturnException;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by dm on 3/12/16.
 */
public abstract class EntityList<T extends TeelaEntity> {

    private List <T> entities = new ArrayList<>();

    public List<T> getEntities() {
        return entities;
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

    private void updateEntity(T oldEntity, T newEntity) {
        int index = entities.indexOf(oldEntity);
        entities.set(index, newEntity);
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public void removeEntity(T entity) {
        entities.remove(entity);
    }

    public abstract T getEntity(String param) throws NullReturnException;

    public T getLatest() throws NullReturnException {
        T entity =  entities.get(entities.size()-1);
        if (entity == null) {
            throw new NullReturnException("There are no entities in the list");
        }
        return entity;
    }

}

