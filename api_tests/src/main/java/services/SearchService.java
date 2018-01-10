package services;

import errors.OperationResultError;
import http.G4Response;
import http.OperationResult;
import http.requests.SearchRequest;
import model.CBEntity;
import model.CBSearchFilter;
import model.CBSearchResult;
import model.SearchFilter;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;

import static json.JsonConverter.toJsonString;

public class SearchService implements EntityService<CBEntity> {

    private static final SearchRequest request = new SearchRequest();
    private static final Logger log = Logger.getLogger(SearchService.class);

    @Override
    public OperationResult<?> add(CBEntity entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult remove(CBEntity entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<CBEntity>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }

    public OperationResult<List<CBEntity>> search(CBSearchFilter filter) {
        log.info("CB_Search with filter: " + toJsonString(filter));
        G4Response response = g4HttpClient.sendRequest(request.search(filter));
        log.info(response.getMessage());

        OperationResult<CBSearchResult> operationResult = new OperationResult<>(response, CBSearchResult.class);
        if (operationResult.isSuccess() && Objects.equals(operationResult.getEntity().getStatus().getCode(), CBSearchResult.CodeStatus.SUCCESS)) {
            return new OperationResult<>(response, operationResult.getEntity().getData());
        } else {
            throw new OperationResultError(operationResult);
        }
    }

    @Override
    public OperationResult<List<CBEntity>> list() {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<CBEntity> update(CBEntity entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<CBEntity> view(String id) {
        throw new NotImplementedException();
    }

    public OperationResult<CBSearchResult> count(CBSearchFilter filter) {
        log.info("CB_Search_count with filter: " + toJsonString(filter));
        G4Response response = g4HttpClient.sendRequest(request.count(filter));
        log.info(response.getMessage());

        OperationResult<CBSearchResult> result = new OperationResult<>(response, CBSearchResult.class);
        if (result.isSuccess() && Objects.equals(result.getEntity().getStatus().getCode(), CBSearchResult.CodeStatus.SUCCESS)) {
            return result;
        } else {
            throw new OperationResultError(result);
        }
    }
}
