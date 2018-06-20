package ae.pegasus.framework.steps;

import ae.pegasus.framework.app_context.AppContext;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.model.Report;
import ae.pegasus.framework.model.Result;
import ae.pegasus.framework.services.ReportService;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.When;

public class APIReportSteps extends APISteps {

    public static ReportService serviceReport = new ReportService();

    private static Logger log = Logger.getLogger(APIFinderFileSteps.class);

    static Report getRandomReport() {
        return objectInitializer.randomEntity(Report.class);
    }

    @When("I send create a report request")
    public void sendReportRequest() {
        Report report = getRandomReport();
        serviceReport.initReport(null, report);
        Result reportNo = context.get("reportNo", Result.class);
        report.setReportNo(reportNo.getResult());

        context.put("report", report);

        AppContext.get().getLoggedUser().getUser();
        serviceReport.add(report);
    }

    @When("I send send generate report number request")
    public void sendGenerateReportNumberRequest() {
        Result reportNo = new Result();
        OperationResult<Result> operationResult = serviceReport.generateNumber();
        reportNo.setResult(operationResult.getEntity().getResult());
        context.put("reportNo", reportNo);
    }
}
