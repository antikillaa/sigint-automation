package services;

import http.G4HttpClient;
import http.G4Response;
import http.OperationResult;
import http.requests.DictionaryRequest;
import model.Dictionary;
import model.RecordType;
import model.SourceType;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class DictionaryService {

    private static final Logger log = Logger.getLogger(DictionaryService.class);
    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private static DictionaryRequest request = new DictionaryRequest();

    public static OperationResult<List<RecordType>> getSubSources() {
        log.info("Dictionaries. Get sub-sources.");

        G4Response response = g4HttpClient.sendRequest(request.subSources());

        OperationResult<RecordType[]> operationResult = new OperationResult<>(response, RecordType[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    public static OperationResult<List<SourceType>> getSources() {
        log.info("Dictionaries. Get sources.");

        G4Response response = g4HttpClient.sendRequest(request.sources());

        OperationResult<SourceType[]> operationResult = new OperationResult<>(response, SourceType[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    public static Dictionary loadDictionary() {
        log.info("Load workflow dictionary.");
        Dictionary dictionary = new Dictionary();

        OperationResult<List<SourceType>> resultSources = getSources();
        if (resultSources.isSuccess()) {
            dictionary.setSourceTypes(resultSources.getEntity());
        }

        OperationResult<List<RecordType>> resultSubSources = getSubSources();
        if (resultSubSources.isSuccess()) {
            dictionary.setRecordTypes(resultSubSources.getEntity());
        }
        return dictionary;
    }
}
