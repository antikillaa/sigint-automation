package services;

import abs.SearchFilter;
import http.G4Response;
import http.OperationResult;
import http.requests.PermissionRequest;
import model.Permission;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class PermissionService implements EntityService<Permission> {

    private static Logger log = Logger.getLogger(PermissionService.class);
    private static PermissionRequest request = new PermissionRequest();

    @Override
    public OperationResult<?> add(Permission entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult remove(Permission entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Permission>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Permission>> list() {
        log.info("Get list of permission");
        G4Response response = g4HttpClient.sendRequest(request.list());

        OperationResult<Permission[]> operationResult = new OperationResult<>(response, Permission[].class);
        if (operationResult.isSuccess()) {
            Permission[] permissions = operationResult.getEntity();
            return new OperationResult<>(response, Arrays.asList(permissions));
        } else {
            throw new AssertionError("Unable to get list of permissions!");
        }
    }

    @Override
    public OperationResult<Permission> update(Permission entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Permission> view(String id) {
        throw new NotImplementedException();
    }
}
