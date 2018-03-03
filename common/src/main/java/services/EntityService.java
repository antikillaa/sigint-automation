package services;

import app_context.AppContext;
import app_context.properties.G4Properties;
import http.G4HttpClient;
import http.OperationResult;
import model.AbstractEntity;
import model.SearchFilter;

import java.util.List;

import static http.G4HttpClient.Protocol.HTTP;


public interface EntityService<T extends AbstractEntity> {

    G4HttpClient g4HttpClient = new G4HttpClient(G4Properties.getRunProperties().getApplicationURL(), HTTP);
    AppContext appContext = AppContext.get();

    /**
     * ADD new entity
     *
     * @param entity entity
     * @return {@link OperationResult}
     */
    OperationResult<?> add(T entity);

    /**
     * DELETE entity
     *
     * @param entity entity
     * @return {@link OperationResult}
     */
    OperationResult remove(T entity);

    /**
     * Filter Entities Search
     *
     * @param filter search filter for payload
     * @return {@link OperationResult}
     */
    OperationResult<List<T>> search(SearchFilter filter);

    /**
     * Get list of entities
     *
     * @return {@link OperationResult}
     */
    OperationResult<List<T>> list();

    /**
     * UPDATE entity
     *
     * @param entity entity
     * @return {@link OperationResult}
     */
    OperationResult<T> update(T entity);

    /**
     * GET entity
     *
     * @param id id of entity
     * @return {@link OperationResult}
     */
    OperationResult<T> view(String id);
}
