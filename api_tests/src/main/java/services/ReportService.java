package services;

import abs.EntityList;
import abs.SearchFilter;
import errors.NullReturnException;
import http.requests.ReportRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.Report;
import model.ReportOwner;
import model.User;
import org.apache.log4j.Logger;
import service.EntityService;

import javax.ws.rs.core.Response;

public class ReportService implements EntityService<Report> {

    private Logger log = Logger.getLogger(RecordService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    @Override
    public int add(Report entity) {
        log.info("Creating new report");
        try {
            log.info("Report: " + JsonCoverter.toJsonString(entity));
        } catch (NullReturnException e) {
            log.error(e.getMessage());
            throw new AssertionError("This is not a report");
        }

        ReportRequest request = new ReportRequest();
        Response response = rsClient.put(sigintHost + request.getURI(), entity, request.getCookie());
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

    public Report setOwner(Report report) {
        UserService userService = new UserService();
        User user = userService.me();

        ReportOwner.User reportUser = new ReportOwner.User();
        reportUser.setId(user.getId());
        reportUser.setName(user.getName());
        reportUser.setStaffId(user.getStaffId());

        ReportOwner owner = new ReportOwner();
        owner
                .setRole(userService.getReportRole(user))
                .setUser(reportUser);

        report
                .setAuthorId(user.getId())
                .setAuthorName(user.getName())
                .setOwner(owner);

        return report;
    }
}
