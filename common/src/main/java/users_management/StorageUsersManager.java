package users_management;

import error_reporter.ErrorReporter;
import model.User;
import steps.GlobalSteps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageUsersManager {


    public StorageUsersManager() {
        initializePermissions();
    }

    private static Map<String, List<User>> userMap = new HashMap<>();

    private static void initializePermissions() {
        List<String> existingPermissions = new StoragePermissionsManager().getPermissions();
        if (existingPermissions.size() == 0) {
            ErrorReporter.raiseError("List of permissions wasn't loaded from mongo DB!");
        }

        existingPermissions.forEach(permission -> userMap.put(permission, new ArrayList<>()));
    }

    public List<User> getUsersWithPermissions(String... permissions) {
        List<User> users = new ArrayList<>();
        for (String permission: permissions) {
            if (users.size() == 0) {
                users.addAll(userMap.get(permission));
            } else {
                users.retainAll(userMap.get(permission));
                if (users.size() == 0) {
                    User user = GlobalSteps.createUserWithPermissions(permissions);
                    users.add(user);
                    return users;
                }

            }
        }
       return users;
    }
}
