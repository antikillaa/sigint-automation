package service; /**
 * Created by dm on 3/11/16.
 */

import model.EntityList;
import model.SearchFilter;
import model.TeelaEntity;

import javax.ws.rs.core.Response;


public interface EntityService <T extends TeelaEntity> {

    public Response addNew(T entity);
    public Response remove(T entity);
    public EntityList<T> list(SearchFilter filter);
    public void update(T entity);
    public T view(String id);
}
