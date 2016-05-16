package steps;
import errors.NullReturnException;
import model.EntityList;
import model.User;

/**
 * Created by dm on 3/12/16.
 */
class UsersList extends EntityList<User> {

    @Override
    public User getEntity(String role) throws NullReturnException {
        User searchedUser = null;
        for (User user:getEntities()) {
            if (user.getRoles().contains(role.toUpperCase())) {
                searchedUser = user;
                break;
            }
        }
        if (searchedUser == null) {
            throw new NullReturnException("There is no user with requested role:"+ role);
        }
        return searchedUser;
    }
}
