package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.G4Response;
import http.client.G4Client;
import http.requests.ReportRequest;
import json.JsonCoverter;
import model.AppContext;
import model.Report;
import org.apache.log4j.Logger;

public class ReportService implements EntityService<Report> {

    private Logger log = Logger.getLogger(RecordService.class);
    private static G4Client g4Client = new G4Client();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    @Override
    public int add(Report entity) {
        log.info("Sending create new report request...");
        try {
            log.debug("Report: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("This is not a report");
        }

        ReportRequest request = new ReportRequest();
        G4Response response = g4Client.put(sigintHost + request.getURI(), entity, request.getCookie());
        Report report = JsonCoverter.readEntityFromResponse(response, Report.class, "result");
        if (report != null) {
            context.entities().getReports().addOrUpdateEntity(report);
        } else {
            log.warn("Failed to create report");
            throw new AssertionError("Failed to create report");
        }
        return response.getStatus();
    }

    @Override
    public int remove(Report entity) {
        return 0;
    }

    @Override
    public EntityList<Report> list(SearchFilter filter) {
        return null;
    }

    @Override
    public int update(Report entity) {
        return 0;
    }

    @Override
    public Report view(String id) {
        return null;
    }

}
