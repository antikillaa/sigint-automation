package ae.pegasus.framework.services;

import ae.pegasus.framework.errors.OperationResultError;
import ae.pegasus.framework.http.G4Response;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.http.requests.RequestForApproveRequest;
import ae.pegasus.framework.json.JsonConverter;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static ae.pegasus.framework.utils.RandomGenerator.getRandomItemsFromList;

public class RequestForApproveService implements EntityService<RequestForApprove> {

    private static Logger log = Logger.getLogger(RequestForApprove.class);
    private static RequestForApproveRequest requestForApproveRequest = new RequestForApproveRequest();

    @Override
    public OperationResult<?> add(RequestForApprove entity) {
        log.info("Sending create new RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForApproveRequest.add(entity));

        OperationResult<RequestForApprove> operationResult = new OperationResult<>(response, RequestForApprove.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForApproves().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    @Override
    public OperationResult remove(RequestForApprove entity) {
        return null;
    }

    @Override
    public OperationResult<List<RequestForApprove>> search(SearchFilter filter) {
        return null;
    }

    @Override
    public OperationResult<List<RequestForApprove>> list() {
        return null;
    }

    @Override
    public OperationResult<RequestForApprove> update(RequestForApprove entity) {
        return null;
    }

    @Override
    public OperationResult<RequestForApprove> view(String id) {
        return null;
    }

    public OperationResult<Result> generateNumber() {
        log.info("Sending generate a RFA number request...");

        G4Response response = g4HttpClient.sendRequest(requestForApproveRequest.generateNumber());

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {
            return operationResult;
        } else {
            throw new OperationResultError(operationResult);
        }
    }

    public void buildRFA(RequestForApprove requestForApprove, Result rfiNo, List<SearchRecord> entities) {
        fillRFIStaticData(requestForApprove);
        requestForApprove.setInternalRequestNo(rfiNo.getResult());
        fillRFIOrgUnit(requestForApprove);
        fillRFIFinderFile(requestForApprove);
        fillRFILink(requestForApprove, entities);
    }

    private void fillRFILink(RequestForApprove requestForApprove, List<SearchRecord> entities) {
        int randomNum = ThreadLocalRandom.current().nextInt(50, 150 + 1);
        List<SearchRecord> event = getRandomItemsFromList(entities, randomNum);
        List<Link> links = new ArrayList<>();

        for (int i = 0; i < event.size(); i++) {
            Link link = new Link();
            link.setLinkType("ARTIFACT");
            link.setLinkId(event.get(i).getId());
            setRFAEvent(event, links, i, link);
        }
        requestForApprove.setLinks(links);

    }

    private void setRFAEvent(List<SearchRecord> event, List<Link> links, int i, Link link) {
        RFAEvent rfaEvent = new RFAEvent();
        rfaEvent.setObjectType("event");
        rfaEvent.setType(event.get(i).getType());
        rfaEvent.setId(event.get(i).getId());
        rfaEvent.setSources(event.get(i).getSourceType());
        rfaEvent.setEventFeed(event.get(i).getType());
        rfaEvent.setSourceType(event.get(i).getSourceType());
        rfaEvent.setRecordType(event.get(i).getRecordType());
        rfaEvent.setModifiedOn((event.get(i).getModifiedOn()));
        rfaEvent.setEventTime(event.get(i).getEventTime());
        rfaEvent.setEndTime(event.get(i).getEndTime());
        rfaEvent.setAttributes(event.get(i).getAttributes());

        Assignments assignments = new Assignments();
        assignments.setDesignationIds("Undesignated");
        rfaEvent.setAssignments(assignments);

        link.setAttributes(Collections.singletonList(rfaEvent));
        links.add(link);
    }

    private void fillRFIOrgUnit(RequestForApprove requestForApprove) {
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
        orgUnit.setKey("00-" + teamID);
        orgUnit.setValue("00-" + teamID);
        orgUnit.setLabel(organization.getFullName());
        requestForApprove.setOrgUnits(Collections.singletonList(orgUnit));
    }

    private void fillRFIFinderFile(RequestForApprove requestForApprove) {
        FinderFile finderFile = Entities.getFinderFiles().getLatest();
        DirectCaseFile directCase = new DirectCaseFile();
        directCase.setLinkId(finderFile.getId());
        directCase.setLinkNo(finderFile.getId());
        directCase.setLinkName(finderFile.getName());
        directCase.setLinkType("FILE");
        requestForApprove.setDirectCaseFiles(Collections.singletonList(directCase));
    }

    private void fillRFIStaticData(RequestForApprove requestForApprove) {
        requestForApprove.setObjectType("RFA");
        requestForApprove.setObjectType("event");
        requestForApprove.setClassification("OUO");
        requestForApprove.setDescription("qe_" + RandomStringUtils.randomAlphabetic(5));
        requestForApprove.setSubject("qe_" + RandomStringUtils.randomAlphabetic(5));
    }
}
