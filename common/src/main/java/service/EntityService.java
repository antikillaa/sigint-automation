package service; /**
 * Created by dm on 3/11/16.
 */

import model.SearchFilter;
import model.TeelaEntity;

import javax.ws.rs.core.Response;
import java.util.List;


public interface EntityService <T extends TeelaEntity> {

    public Response addNew(T entity);
    public void remove(T entity);
    public <T extends TeelaEntity>List<T> list(SearchFilter filter);
    public void update(T entity);
    public T view(String id);
}
