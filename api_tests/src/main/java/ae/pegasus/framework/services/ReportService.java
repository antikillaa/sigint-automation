package ae.pegasus.framework.services;

import ae.pegasus.framework.errors.OperationResultError;
import ae.pegasus.framework.http.G4Response;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.http.requests.ReportRequest;
import ae.pegasus.framework.model.Report;
import ae.pegasus.framework.model.SearchFilter;
import ae.pegasus.framework.model.entities.Entities;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

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
        throw new NotImplementedException("");
    }

    @Override
    public OperationResult<List<Report>> search(SearchFilter filter) {
        throw new NotImplementedException("");
    }

    @Override
    public OperationResult<List<Report>> list() {
        throw new NotImplementedException("");
    }

    @Override
    public OperationResult<Report> update(Report entity) {
        throw new NotImplementedException("");
    }

    @Override
    public OperationResult<Report> view(String id) {
        throw new NotImplementedException("");
    }

}
