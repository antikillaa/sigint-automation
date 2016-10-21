package data_for_entity.data_providers.user_permissions;

import data_for_entity.data_providers.EntityDataProvider;
import model.Permission;

public class UserPermissionProvider implements EntityDataProvider {
    @Override
    public Object generate(int length) {
        return Permission.random();
    }
}
