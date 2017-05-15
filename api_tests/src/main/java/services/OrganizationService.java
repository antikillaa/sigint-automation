package services;

import http.G4Response;
import http.OperationResult;
import http.requests.OrganizationRequest;
import json.JsonConverter;
import model.Organization;
import model.SearchFilter;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class OrganizationService implements EntityService<Organization> {

    private static Logger log = Logger.getLogger(OrganizationService.class);
    private static OrganizationRequest request = new OrganizationRequest();

    @Override
    public OperationResult<?> add(Organization entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult remove(Organization entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Organization>> search(SearchFilter filter) {
        log.info("Search organization by filter:" + JsonConverter.toJsonString(filter));

        G4Response response = g4HttpClient.sendRequest(request.search(filter));

        OperationResult<Organization[]> operationResult = new OperationResult<>(response, Organization[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    @Override
    public OperationResult<List<Organization>> list() {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Organization> update(Organization entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Organization> view(String id) {
        throw new NotImplementedException();
    }
}
