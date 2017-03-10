package steps;

import data_for_entity.RandomEntities;
import http.OperationResult;
import model.Group;
import model.Role;
import model.User;
import services.GroupService;
import services.RoleService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class UserSteps {

    private static RoleService roleService = new RoleService();
    private static GroupService groupService = new GroupService();
    private static RandomEntities randomEntities = new RandomEntities();

    public static User createUserWithPermissions(String...permissions) {
        Role role  = randomEntities.randomEntity(Role.class);
        HashSet<String> permissionsSet = new HashSet<>(Arrays.asList(permissions));
        role.setPermissions(permissionsSet);
        OperationResult<Role> result =  roleService.add(role);
        Group group = randomEntities.randomEntity(Group.class);
        group.setPermissions()
    }
}
