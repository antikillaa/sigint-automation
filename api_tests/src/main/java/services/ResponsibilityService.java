package services;

import abs.SearchFilter;
import http.G4Response;
import http.OperationResult;
import http.requests.ResponsibilityRequest;
import model.Responsibility;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class ResponsibilityService implements EntityService<Responsibility> {

    private static Logger log = Logger.getLogger(ResponsibilityService.class);
    private static ResponsibilityRequest request = new ResponsibilityRequest();

    @Override
    public OperationResult<?> add(Responsibility entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult remove(Responsibility entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Responsibility>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Responsibility>> list() {
        log.info("Get list of responsibilities");
        G4Response response = g4HttpClient.sendRequest(request.list());

        OperationResult<Responsibility[]> operationResult = new OperationResult<>(response, Responsibility[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            throw new AssertionError("Unable to get list of responsibilities");
        }
    }

    @Override
    public OperationResult<Responsibility> update(Responsibility entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Responsibility> view(String id) {
        throw new NotImplementedException();
    }
}
