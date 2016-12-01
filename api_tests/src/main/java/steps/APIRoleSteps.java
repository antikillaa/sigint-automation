package steps;

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


public class APIRoleSteps extends APISteps {

    private Logger log = Logger.getLogger(APIPhonebookSteps.class);
    private RoleService service = new RoleService();

    @When("I send create a new role request")
    public void createRoleRequest() {
        Role role = getRandomRole();
        OperationResult<Role> operationResult = service.add(role);
        OperationsResults.setResult(operationResult);
        context.put("requestRole", operationResult.getResult());
    }

    @Then("Created role is correct")
    public void createdRoleIsCorrect() throws NullReturnException {
        Role createdRole = Entities.getRoles().getLatest();
        Role requestRole = context.get("requestRole", Role.class);

        Verify.shouldBe(Conditions.equals(createdRole, requestRole));
    }
    
    
    static Role getRandomRole() {
        return objectInitializer.randomEntity(Role.class);
    }
}
