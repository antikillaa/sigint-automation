package services;

import static json.JsonConverter.toJsonString;
import static org.junit.Assert.assertNotNull;

import csv.CSVWhitelistWriter;
import errors.OperationResultError;
import http.G4Response;
import http.OperationResult;
import http.requests.WhiteListRequest;
import java.util.Arrays;
import java.util.List;
import model.G4File;
import model.ImportResult;
import model.Result;
import model.SearchFilter;
import model.Whitelist;
import model.WhitelistSearchResult;
import model.entities.Entities;
import org.apache.log4j.Logger;


public class WhitelistService implements EntityService<Whitelist> {

    private static final Logger log = Logger.getLogger(WhitelistService.class);
    private static WhiteListRequest request = new WhiteListRequest();

    @Override
    public OperationResult<Whitelist> add(Whitelist entity) {
        log.info("Creating new Whitelist..");
        G4Response response = g4HttpClient.sendRequest(request.add(entity));

        OperationResult<Whitelist> operationResult = new OperationResult<>(response, Whitelist.class);
        if (operationResult.isSuccess()) {
            Entities.getWhitelists().addOrUpdateEntity(operationResult.getEntity());
        }

        return operationResult;
    }

    @Override
    public OperationResult<Result> remove(Whitelist entity) {
        log.info("Deleting Whitelist with id: " + entity.getId());
        G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {
            Entities.getWhitelists().removeEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<List<Whitelist>> search(SearchFilter filter) {
        log.info("Search whitelists, filter:\n" + toJsonString(filter));
        G4Response response = g4HttpClient.sendRequest(request.search(filter));

        OperationResult<WhitelistSearchResult> operationResult = new OperationResult<>(response, WhitelistSearchResult.class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, operationResult.getEntity().getResult());
        } else {
            return new OperationResult<>(response);
        }
    }

    @Override
    public OperationResult<List<Whitelist>> list() {
        log.info("Getting list of Whitelists");
        G4Response response = g4HttpClient.sendRequest(request.list());

        OperationResult<Whitelist[]> operationResult = new OperationResult<>(response, Whitelist[].class);
        if (operationResult.isSuccess() && operationResult.getEntity() != null) {
            List<Whitelist> whitelists = Arrays.asList(operationResult.getEntity());
            return new OperationResult<>(response, whitelists);
        } else {
            throw new OperationResultError(operationResult);
        }
    }

    @Override
    public OperationResult<Whitelist> update(Whitelist entity) {
        log.info("Updating Whitelist with id: " + entity.getId());

        G4Response response = g4HttpClient.sendRequest(request.update(entity));

        OperationResult<Whitelist> operationResult = new OperationResult<>(response, entity);
        if (operationResult.isSuccess()) {
            Entities.getWhitelists().addOrUpdateEntity(entity);
        }
        return operationResult;
    }

    @Override
    public OperationResult<Whitelist> view(String id) {
        log.info("Getting Whitelist with id: " + id);
        G4Response response = g4HttpClient.sendRequest(request.get(id));

        OperationResult<Whitelist> operationResult = new OperationResult<>(response, Whitelist.class);

        if (operationResult.isSuccess()) {
            Entities.getWhitelists().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    public G4File createCSVFile(List<Whitelist> whitelists, boolean withHeader) {
        log.info("Writing " + whitelists.size() + " Whitelist entries to file..");

        CSVWhitelistWriter writer = new CSVWhitelistWriter("whitelists.csv", withHeader);
        G4File file = null;
        try {
            file = writer.writeEntitiesToCsv(whitelists);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return file;
    }

    public OperationResult<ImportResult> upload(G4File file) {
        assertNotNull("null object for whitelist upload", file);

        log.info("Import Whitelists from file..");
        G4Response response = g4HttpClient.sendRequest(request.upload(file));

        return new OperationResult<>(response, ImportResult.class);
    }
}
