package services;

import abs.SearchFilter;
import app_context.entities.Entities;
import errors.OperationResultError;
import http.G4Response;
import http.OperationResult;
import http.requests.ReportRequest;
import model.Report;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import utils.Parser;

import java.util.List;

public class ReportService implements EntityService<Report> {

    private static Logger log = Logger.getLogger(ReportService.class);
    private static ReportRequest request = new ReportRequest();

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

        G4Response response = g4HttpClient.sendRequest(request.add(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getReports().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    @Override
    public OperationResult remove(Report entity) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Report>> search(SearchFilter filter) {
        throw new NotImplementedException();
    }

    @Override
    public OperationResult<List<Report>> list() {
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
