package steps;

import data_for_entity.RandomEntities;
import http.OperationResult;
import model.Group;
import model.Role;
import model.User;
import services.GroupService;
import services.RoleService;
import services.UserService;

import java.util.*;

public class UserSteps {

    private static GroupService groupService = new GroupService();
    private static RandomEntities randomEntities = new RandomEntities();
    private static UserService userService = new UserService();

    public static User createUserWithPermissions(String...permissions) {

        Group group = randomEntities.randomEntity(Group.class);
        group.setPermissions(Arrays.asList(permissions));
        OperationResult<Group> groupOperationResult = groupService.add(group);
        User newUser = randomEntities.randomEntity(User.class);
        List<String> ids = new ArrayList<>();
        ids.add(groupOperationResult.getResult().getId());
        newUser.setUserGroupIds(ids);
        OperationResult<User> userOperationResult = userService.add(newUser);
        return userOperationResult.getResult();
    }
}
