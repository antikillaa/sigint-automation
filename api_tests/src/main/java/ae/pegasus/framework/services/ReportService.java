package ae.pegasus.framework.services;

import ae.pegasus.framework.errors.OperationResultError;
import ae.pegasus.framework.http.G4Response;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.http.requests.GenerateReportNumberRequest;
import ae.pegasus.framework.http.requests.ReportRequest;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.List;

public class ReportService implements EntityService<Report> {

    private static Logger log = Logger.getLogger(ReportService.class);
    private static ReportRequest reportRequest = new ReportRequest();
    private static GenerateReportNumberRequest genereteReportNumberRequest = new GenerateReportNumberRequest();

    @Override
    public OperationResult<Report> add(Report entity) {
        log.info("Sending create new report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.add(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "data");
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

    public void initReport(List<SearchEntity> events, Report report) {
        report.setObjectType("OperatorReport");
        report.setState(ReportState.DRAFT);
        report.setLayoutType("CARD_GROUP");
        report.setReportType("GENERAL");
    }

    public OperationResult<Result> generateNumber() {
        log.info("Sending create new report number request...");

        G4Response response = g4HttpClient.sendRequest(genereteReportNumberRequest.generateNumber());

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {
            return operationResult;
        } else {
            throw new OperationResultError(operationResult);
        }
    }
}
