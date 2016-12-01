package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.*;
import http.requests.ReportRequest;
import model.Report;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

public class ReportService implements EntityService<Report> {

    private Logger log = Logger.getLogger(RecordService.class);
    private static G4HttpClient g4HttpClient = new G4HttpClient();

    /**
     * API: PUT /api/reports/
     *
     * @param entity entity
     * @return HTTP status code
     */
    @Override
    public OperationResult<Report> add(Report entity) {
        log.info("Sending create new report request...");
        log.debug(Parser.entityToString(entity));
        ReportRequest request = new ReportRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        Report report = JsonConverter.readEntityFromResponse(response, Report.class, "result");
        OperationResult<Report> operationResult = new OperationResult<>(response, report);
        if (report != null) {
            Entities.getReports().addOrUpdateEntity(report);
        } else {
            OperationsResults.throwError(operationResult);
        }
        return operationResult;
    }

    @Override
    public OperationResult remove(Report entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<EntityList<Report>> list(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Report> update(Report entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<Report> view(String id) {
        throw new NotImplementedException();
    }

}
