package data_for_entity.data_providers.user_permissions;

import data_for_entity.data_providers.EntityDataProvider;
import users_management.Permissions;
import utils.RandomGenerator;


public class UserPermissionProvider implements EntityDataProvider {
    @Override
    public Object generate(int length) {
        return RandomGenerator.getRandomItemFromList(Permissions.getPermissions());
    }
}
