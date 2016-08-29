package app_context.entities;
import abs.EntityList;
import errors.NullReturnException;
import model.User;


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
