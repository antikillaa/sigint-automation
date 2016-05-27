package services;
import abs.EntityList;
import abs.SearchFilter;
import model.User;
import service.EntityService;

/**
 * Created by dm on 3/11/16.
 */
public class UserService implements EntityService<User> {

    public int addNew(User user) {
        return 0;
    }

    public int remove(User entity) {
        return 0;
    }

    public EntityList<User> list(SearchFilter filter) {
        return null;
    }

    public int update(User entity) {
        return 0;
    }

    public User view(String id) {
        return null;
    }


}
