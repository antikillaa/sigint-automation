package services;

import model.SearchFilter;
import model.entities.Entities;
import http.G4Response;
import http.OperationResult;
import http.requests.WhiteListRequest;
import model.Result;
import model.Whitelist;
import model.WhitelistListResult;
import org.apache.log4j.Logger;
import org.apache.commons.lang.NotImplementedException;
import java.util.List;


public class WhitelistService implements EntityService<Whitelist> {

    WhiteListRequest request = new WhiteListRequest();

    private Logger logger = Logger.getLogger(WhitelistService.class);

    @Override
    public OperationResult<Whitelist> add(Whitelist entity) {
        G4Response response = g4HttpClient.sendRequest(request.add(entity));
        OperationResult<Whitelist> operationResult = new OperationResult<>(response,entity);
        if (operationResult.isSuccess()) {
            Entities.getWhitelists().addOrUpdateEntity(entity);
        }

        return operationResult;

    }

    @Override
    public OperationResult<Result> remove(Whitelist entity) {
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));
        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {

            Entities.getWhitelists().removeEntity(entity);
        }
        return operationResult;

    }

    @Override
    public OperationResult<List<Whitelist>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }


    @Override
    public OperationResult<List<Whitelist>> list() {
        G4Response response = g4HttpClient.sendRequest(request.list());
        OperationResult<WhitelistListResult> operationResult =
                new OperationResult<>(response, WhitelistListResult.class);

        if (operationResult.isSuccess()) {
            List<Whitelist> whiteLists = operationResult.getEntity().getResult();
            return new OperationResult<>(response, whiteLists);

        } else {
            throw new RuntimeException("Unable to read list of whitelist entries: "+operationResult.getCode()+" , "+operationResult.getMessage());
        }

    }

    @Override
    public OperationResult<Whitelist> update(Whitelist entity) {
        G4Response response = g4HttpClient.sendRequest(request.update(entity));
        OperationResult<Whitelist> operationResult = new OperationResult<>(response,entity);
        if(operationResult.isSuccess()){

            Entities.getWhitelists().addOrUpdateEntity(entity);
        }
        return operationResult;
    }


    @Override
    public OperationResult<Whitelist> view(String id) {

        G4Response response = g4HttpClient.sendRequest(request.get(id));
        OperationResult<Whitelist> operationResult =
                new OperationResult<>(response, Whitelist.class, "result");

        if (operationResult.isSuccess()) {

            Entities.getWhitelists().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }
}
