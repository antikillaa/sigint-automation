package services;

import abs.AbstractEntity;
import abs.EntityList;
import abs.SearchFilter;
import http.OperationResult;


public interface EntityService<T extends AbstractEntity> {

    /**
     * ADD new entity
     *
     * @param entity entity
     * @return {@link OperationResult}
     */
    OperationResult<?> add(T entity);

    /**
     * DELETE entity
     * @param entity entity
     * @return {@link OperationResult}
     */
    OperationResult remove(T entity);

    /**
     * GET list of entities
     * @param filter search filter for payload
     * @return {@link OperationResult}
     */
    OperationResult<EntityList<T>> list(SearchFilter filter);

    /**
     * UPDATE entity
     * @param entity entity
     * @return {@link OperationResult}
     */
    OperationResult<T> update(T entity);

    /**
     * GET entity
     * @param id id of entity
     * @return {@link OperationResult}
     */
    OperationResult<T> view(String id);
}
