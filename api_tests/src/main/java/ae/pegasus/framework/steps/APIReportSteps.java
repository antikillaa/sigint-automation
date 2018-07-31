package ae.pegasus.framework.steps;

import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.json.JsonConverter;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import ae.pegasus.framework.services.OrganizationService;
import ae.pegasus.framework.services.ReportService;
import ae.pegasus.framework.services.UserService;
import ae.pegasus.framework.utils.RandomGenerator;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.When;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

        List<SearchRecord> entities = context.get("searchResults", List.class);
        SearchRecord event = RandomGenerator.getRandomItemFromList(entities);
        ReportEvent reportEvent = new ReportEvent();
        reportEvent.setId(event.getId());
        reportEvent.setOrder(0);
        reportEvent.setType(event.getType());
        reportEvent.setBody(JsonConverter.toJsonString(event));

        SourceType source = new SourceType();
        source.setEventFeed(String.valueOf(event.getEventFeed()));
        source.setDataSource(event.getSourceType());
        source.setSubSource(event.getRecordType());
        source.setSubSourceId(String.valueOf(event.getAttributes().get("interceptorId")));
        reportEvent.setSourceType(source);
        report.setReportEvents(Collections.singletonList(reportEvent));
        User user = appContext.get().getLoggedUser().getUser();
        report.setCreatedByName(user.getName());

        String teamID = UserService.getDefaultTeamId();

        OrganizationFilter filter = new OrganizationFilter();
        OperationResult<List<Organization>> operationResult = new OrganizationService().search(filter);
        List<Organization> orgUnits = operationResult.getEntity();
        Organization organization = orgUnits.stream()
                .filter(a -> Objects.equals(a.getId(), teamID))
                .findAny().orElse(null);
        if (organization == null) {
            /* TODO */
        }
        OrgUnit orgUnit = new OrgUnit();
        orgUnit.setOrgUnitId("00-" + teamID);
        orgUnit.setOrgUnitName(organization.getFullName());
        report.setOrgUnits(Collections.singletonList(orgUnit));

        FinderFile finderFile = Entities.getFinderFiles().getLatest();
        DirectCaseFile directCaseFile = new DirectCaseFile();
        directCaseFile.setLinkId(finderFile.getId());
        directCaseFile.setLinkNo(finderFile.getId());
        directCaseFile.setLinkName(finderFile.getName());
        directCaseFile.setLinkType("FILE");
        directCaseFile.setRelationType("RELATED");

        Attributes attributes = new Attributes();
        attributes.setType("File");
        attributes.setIcon("far fa-folder");

        directCaseFile.setAttributes(attributes);
        report.setDirectCaseFiles(Collections.singletonList(directCaseFile));

        report.setClassification("TS");

        context.put("report", report);

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
