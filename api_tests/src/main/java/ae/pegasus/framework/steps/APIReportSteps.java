package ae.pegasus.framework.steps;

import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.Report;
import ae.pegasus.framework.model.ReportOwner;
import ae.pegasus.framework.model.ReportStatus;
import ae.pegasus.framework.model.User;
import ae.pegasus.framework.services.ReportCategoryService;
import ae.pegasus.framework.services.ReportService;
import ae.pegasus.framework.services.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

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
