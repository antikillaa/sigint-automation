package services;

import abs.AbstractEntity;
import abs.SearchFilter;
import http.G4HttpClient;
import http.OperationResult;

import java.util.List;


public interface EntityService<T extends AbstractEntity> {

    G4HttpClient g4HttpClient = new G4HttpClient();

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
    OperationResult<List<T>> list(SearchFilter filter);

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
