package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import errors.OperationResultError;
import file_generator.FileGenerator;
import http.G4Response;
import http.OperationResult;
import http.requests.phonebook.EtisalatSubscriberRequest;
import java.util.ArrayList;
import java.util.List;
import model.EtisalatSubscriberEntry;
import model.G4File;
import model.UploadResult;
import model.phonebook.EtisalatSubscriberSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

public class EtisalatSubscriberService implements EntityService<EtisalatSubscriberEntry> {

    private static Logger log = Logger.getLogger(EtisalatSubscriberService.class);
    private static EtisalatSubscriberRequest request = new EtisalatSubscriberRequest();

    /**
     * ADD Etisalat Subscriber entry
     * API: POST /etisalat-subscriber-data/upload uploadMultipart
     *
     * @param entity Etisalat Subscriber entry
     * @return HTTP status code
     */
    @Override
    public OperationResult<UploadResult> add(EtisalatSubscriberEntry entity) {
        log.info("Add Etisalat Subscriber entry..");
        log.debug(Parser.entityToString(entity));
        List<EtisalatSubscriberEntry> entries = new ArrayList<>();
        entries.add(entity);

        OperationResult<UploadResult> operationResult =  upload(entries);
        if (operationResult.isSuccess()) {
            Entities.getEtisalatSubscribers().addOrUpdateEntity(entity);
        }
        return operationResult;
    }

    public OperationResult<UploadResult> add(List<EtisalatSubscriberEntry> entities) {
        log.info("Adding Etisalat Subscriber entries..");
        log.debug(Parser.entityToString(entities));

        OperationResult<UploadResult> operationResult = upload(entities);
        if (operationResult.isSuccess()) {
            for (EtisalatSubscriberEntry entity : entities) {
                Entities.getEtisalatSubscribers().addOrUpdateEntity(entity);
            }
        }
        return upload(entities);
    }

    /**
     * UPLOAD list of Etisalat subscriber entries
     * POST /etisalat-subscriber-data/upload uploadMultipart
     *
     * @param entries of Etisalat subscriber entries
     * @return {@link OperationResult<UploadResult>}
     */
    private OperationResult<UploadResult> upload(List<EtisalatSubscriberEntry> entries) {
        log.info("Writing Etisalat Subscriber entries to file..");
        G4File file = new FileGenerator(EtisalatSubscriberEntry.class).write(entries);

        log.info("Upload file with " + entries.size() + " Etisalat Subscriber entries..");
        G4Response response = g4HttpClient.sendRequest(request.upload(file));

        return new OperationResult<>(response, UploadResult.class, "result");
    }

    @Override
    public OperationResult remove(EtisalatSubscriberEntry entity) {
        throw new NotImplementedException();
    }

    /**
     * GET list of Etisalat subscriber entries
     * API: POST /etisalat-subscriber-data/search search
     *
     * @param filter search filter for payload
     * @return EntityList of Etisalat subscriber
     */
    @Override
    public OperationResult<EntityList<EtisalatSubscriberEntry>> list(SearchFilter filter) {
        log.info("Getting list of Etisalat Subscriber enries..");
        G4Response response = g4HttpClient.sendRequest(request.search(filter));

        OperationResult<EtisalatSubscriberSearchResult> operationResult =
                new OperationResult<>(response, EtisalatSubscriberSearchResult.class, "result");

        if (operationResult.isSuccess() && operationResult.getEntity() != null) {
            List<EtisalatSubscriberEntry> entries = operationResult.getEntity().getContent();
            EntityList<EtisalatSubscriberEntry> etisalatSubscriberEntries = new EntityList<>(entries);
            return new OperationResult<>(response, etisalatSubscriberEntries);
        } else {
            throw new OperationResultError(operationResult);
        }
    }

    @Override
    public OperationResult<EtisalatSubscriberEntry> update(EtisalatSubscriberEntry entity) {
        throw new NotImplementedException();
    }

    /**
     * GET Etisalat subscriber entry
     * API: GET /etisalat-subscriber-data/entries/{id} getEntry
     *
     * @param id id of entity
     * @return Etisalat subscriber entry
     */
    @Override
    public OperationResult<EtisalatSubscriberEntry> view(String id) {
        log.info("Getting derails of Etisalat Subscriber entry by id: " + id);
        G4Response response = g4HttpClient.sendRequest(request.get(id));

        return new OperationResult<>(response, EtisalatSubscriberEntry.class, "result");
    }

}
