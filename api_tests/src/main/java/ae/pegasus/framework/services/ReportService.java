package ae.pegasus.framework.services;

import ae.pegasus.framework.errors.OperationResultError;
import ae.pegasus.framework.http.G4Response;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.http.requests.ReportRequest;
import ae.pegasus.framework.json.JsonConverter;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static ae.pegasus.framework.utils.RandomGenerator.getRandomItemFromList;
import static ae.pegasus.framework.utils.RandomGenerator.getRandomItemsFromList;

public class ReportService implements EntityService<Report> {

    private static Logger log = Logger.getLogger(ReportService.class);
    private static ReportRequest reportRequest = new ReportRequest();

    @Override
    public OperationResult<Report> add(Report entity) {
        log.info("Sending create new report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.add(entity));

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
        log.info("Sending delete a report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.remove(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getReports().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
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
        log.info("View report by id:" + id);
        G4Response response = g4HttpClient.sendRequest(reportRequest.view(id));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class);
        if (operationResult.isSuccess()) {
            return operationResult;
        } else {
            throw new OperationResultError(operationResult);
        }
    }

    public OperationResult submit(Report entity) {
        log.info("Sending submit a new report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.submit(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getReports().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult takeOwnership(Report entity) {
        log.info("Sending take ownership a report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.takeOwnership(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getReports().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult approveReport(Report entity) {
        log.info("Sending approve a report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.approveReport(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getReports().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult returnAuthor(Report entity) {
        log.info("Sending return to author report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.returnAuthor(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getReports().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult rejectReport(Report entity) {
        log.info("Sending reject a report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.rejectReport(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getReports().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult cancelReportNotOwned(Report entity) {
        log.info("Sending reject a report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.cancelReportNotOwned(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getReports().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult cancelReportOwned(Report entity) {
        log.info("Sending reject a report request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.cancelReportOwned(entity));

        OperationResult<Report> operationResult = new OperationResult<>(response, Report.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getReports().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    public OperationResult<List<CurrentOwner>> possibleOwners(Report entity) {
        log.info("Sending possible owners request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.possibleOwners(entity));

        OperationResult<CurrentOwner[]> operationResult = new OperationResult<>(response, CurrentOwner[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    public OperationResult<Result> generateNumber() {
        log.info("Sending create new report number request...");

        G4Response response = g4HttpClient.sendRequest(reportRequest.generateNumber());

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {
            return operationResult;
        } else {
            throw new OperationResultError(operationResult);
        }
    }

    public void buildReport(Report report, Result reportNo, List<SearchRecord> entities) {
        fillReportStaticData(report);
        report.setReportNo(reportNo.getResult());
        fillReportEvents(entities, report);
        fillReportOrgUnit(report);
        fillReportFinderFile(report);
    }

    private void fillReportStaticData(Report report) {
        report.setObjectType("OperatorReport");
        report.setLayoutType("CARD_GROUP");
        report.setReportType("GENERAL");
        report.setClassification("TS");
        report.setState("Initial Draft");
        report.setDescription("qe_" + RandomStringUtils.randomAlphabetic(5));
        report.setSubject("qe_" + RandomStringUtils.randomAlphabetic(5));
    }

    private void fillReportOrgUnit(Report report) {
        User user = appContext.get().getLoggedUser().getUser();
        report.setCreatedByName(user.getName());

        String teamID = UserService.getOrCreateDefaultTeamId();
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
    }

    private void fillReportEvent(List<SearchRecord> entities, Report report) {
        SearchRecord event = getRandomItemFromList(entities);
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
    }

    private void fillReportEvents(List<SearchRecord> entities, Report report) {
        int randomNum = ThreadLocalRandom.current().nextInt(50, 150 + 1);
        List<SearchRecord> event = getRandomItemsFromList(entities, randomNum);
        List<ReportEvent> reportEvents = new ArrayList<>();

        for (int i = 0; i < event.size(); i++) {
            ReportEvent reportEvent = new ReportEvent();
            reportEvent.setOrder(i);
            reportEvent.setId(event.get(i).getId());
            reportEvent.setType(event.get(i).getType());
            reportEvent.setBody(JsonConverter.toJsonString(event.get(i)));

            SourceType source = new SourceType();
            source.setEventFeed(String.valueOf(event.get(i).getEventFeed()));
            source.setDataSource(event.get(i).getSourceType());
            source.setSubSource(event.get(i).getRecordType());
            source.setSubSourceId(String.valueOf(event.get(i).getAttributes().get("interceptorId")));

            reportEvent.setSourceType(source);
            reportEvents.add(reportEvent);
        }
        report.setReportEvents(reportEvents);
    }

    private void fillReportFinderFile(Report report) {
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
    }

    public void setNextOwners(List<CurrentOwner> currentOwner, List<NextOwners> allOwners) {
        for (int i = 0; i < currentOwner.size(); i++) {
            NextOwners nextOwner = new NextOwners();
            nextOwner.setOwnerId(currentOwner.get(i).getOwnerId());
            nextOwner.setOwnerName(currentOwner.get(i).getOwnerName());
            nextOwner.setType(currentOwner.get(i).getType());
            allOwners.add(nextOwner);
        }
    }
}
