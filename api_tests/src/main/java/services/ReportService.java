package services;

import abs.EntityList;
import abs.SearchFilter;
import app_context.entities.Entities;
import http.G4HttpClient;
import http.G4Response;
import http.requests.ReportRequest;
import json.JsonCoverter;
import model.Report;
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
    public int add(Report entity) {
        log.info("Sending create new report request...");
        log.debug(Parser.entityToString(entity));

        ReportRequest request = new ReportRequest().add(entity);
        G4Response response = g4HttpClient.sendRequest(request);
        Report report = JsonCoverter.readEntityFromResponse(response, Report.class, "result");
        if (report != null) {
            Entities.getReports().addOrUpdateEntity(report);
        } else {
            log.error("Failed to create report. Response: " + response.getMessage());
            throw new AssertionError("Failed to create report. Response: " + response.getMessage());
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
