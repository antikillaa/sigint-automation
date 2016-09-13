package app_context.entities;
import abs.EntityList;
import model.User;


class UsersList extends EntityList<User> {

    @Override
    public User getEntity(String role) {
        User searchedUser = null;
        for (User user:getEntities()) {
            if (user.getRoles().contains(role.toUpperCase())) {
                searchedUser = user;
                break;
            }
        }
        return searchedUser;
    }
}
