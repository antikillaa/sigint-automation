package services;

import abs.AbstractEntity;
import abs.EntityList;
import abs.SearchFilter;


public interface EntityService<T extends AbstractEntity> {

    /**
     * ADD new entity
     *
     * @param entity entity
     * @return HTTP status code
     */
    int add(T entity);

    /**
     * DELETE entity
     * @param entity entity
     * @return HTTP status code
     */
    int remove(T entity);

    /**
     * GET list of entities
     * @param filter search filter for payload
     * @return EntityList of entity
     */
    EntityList<T> list(SearchFilter filter);

    /**
     * UPDATE entity
     * @param entity entity
     * @return HTTP status code
     */
    int update(T entity);

    /**
     * GET entity
     * @param id id of entity
     * @return entity
     */
    T view(String id);
}
