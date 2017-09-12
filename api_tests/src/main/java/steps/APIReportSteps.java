package steps;

import http.OperationResult;
import model.Report;
import model.ReportOwner;
import model.ReportStatus;
import model.User;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import services.ReportCategoryService;
import services.ReportService;
import services.UserService;

import java.util.Date;

public class APIReportSteps extends APISteps {

    private static final Logger log = Logger.getLogger(APIReportSteps.class);
    private ReportService reportService = new ReportService();
    private ReportCategoryService reportCategoryService = new ReportCategoryService();

    private static Report generateReport() {
        Report report = new Report();
        report.setSubject("subject:" + RandomStringUtils.randomAlphabetic(4));
        report.setStatus(ReportStatus.DRAFT);
        initOwner(report);
        report.setCreatedAt(new Date());
        return report;
    }

    private static void initOwner(Report report) {
        UserService userService = new UserService();
        OperationResult<User> operationResult = userService.me();
        User user = operationResult.getEntity();
        User reportUser = new User();
        reportUser.setId(user.getId());

        ReportOwner owner = new ReportOwner();
        owner
                .setRole(userService.getReportRole(user))
                .setUser(reportUser);
        report.setAuthorId(user.getId());
        report.setOwner(owner);
    }
}
