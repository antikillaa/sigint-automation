package services;

import abs.SearchFilter;
import http.G4Response;
import http.OperationResult;
import http.requests.TitleRequest;
import model.Title;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class TitleService implements EntityService<Title> {

    private static Logger log = Logger.getLogger(TitleService.class);
    private static TitleRequest request = new TitleRequest();

    @Override
    public OperationResult<?> add(Title entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult remove(Title entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Title>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Title>> list() {
        log.info("Get list of titles");
        G4Response response = g4HttpClient.sendRequest(request.list());

        OperationResult<Title[]> operationResult = new OperationResult<>(response, Title[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            throw new AssertionError("Unable to get list of titles");
        }
    }

    @Override
    public OperationResult<Title> update(Title entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Title> view(String id) {
        throw new NotImplementedException();
    }
}
