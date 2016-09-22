package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.TeelaEntity;


public interface EntityService<T extends TeelaEntity> {

    /**
     * ADD new entity
     *
     * @param entity entity
     * @return HTTP status code
     */
    public int add(T entity);

    /**
     * DELETE entity
     * @param entity entity
     * @return HTTP status code
     */
    public int remove(T entity);

    /**
     * GET list of entities
     * @param filter search filter for payload
     * @return EntityList of entity
     */
    public EntityList<T> list(SearchFilter filter);

    /**
     * UPDATE entity
     * @param entity entity
     * @return HTTP status code
     */
    public int update(T entity);

    /**
     * GET entity
     * @param id id of entity
     * @return entity
     */
    public T view(String id);
}
