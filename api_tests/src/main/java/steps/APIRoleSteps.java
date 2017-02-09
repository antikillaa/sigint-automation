package steps;

import abs.EntityList;
import app_context.entities.Entities;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import http.OperationResult;
import http.OperationsResults;
import model.Role;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import services.RoleService;

import java.util.Objects;


public class APIRoleSteps extends APISteps {

    private Logger log = Logger.getLogger(APIRoleSteps.class);
    private RoleService service = new RoleService();

    @When("I send create a new role request")
    public void createRoleRequest() {
        Role role = getRandomRole();
        context.put("role", role);

        OperationResult<Role> operationResult = service.add(role);
        OperationsResults.setResult(operationResult);
    }

    @Then("Role is correct")
    public void createdRoleIsCorrect() throws NullReturnException {
        Role createdRole = Entities.getRoles().getLatest();
        Role requestRole = context.get("role", Role.class);

        Verify.shouldBe(Conditions.equals(createdRole, requestRole));
    }
    
    static Role getRandomRole() {
        return objectInitializer.randomEntity(Role.class);
    }

    @When("I send delete Role request")
    public void deleteLatestRole() {
        Role role = Entities.getRoles().getLatest();
        OperationResult operationResult = service.remove(role);
        OperationsResults.setResult(operationResult);
    }

    @When("I send get list of Roles request")
    public void viewSource() {
        OperationResult<EntityList<Role>> operationResult = service.list();
        context.put("roles", operationResult.getResult());
    }

    @Then("Role is deleted")
    public void roleIsDeleted(){
        EntityList<Role> roleEntityList = context.get("roles", EntityList.class);
        Role role = context.get("role", Role.class);

        for (Role entity : roleEntityList.getEntities()) {
            if (Objects.equals(role.getName(), entity.getName())) {
                throw new AssertionError("Role wasn't removed, name: " + role.getName());
            }
        }
    }

    @When("I send update role request")
    public void updateRole() {
        Role role = Entities.getRoles().getLatest();
        Role updatedRole = getRandomRole();
        updatedRole.setName(role.getName());
        context.put("role", updatedRole);

        OperationResult<Role> operationResult = service.update(updatedRole);
        OperationsResults.setResult(operationResult);
    }
}
