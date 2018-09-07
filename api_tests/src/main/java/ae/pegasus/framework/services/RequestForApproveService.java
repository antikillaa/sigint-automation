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

import java.util.*;
import java.util.stream.Collectors;

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
        log.info("Sending delete new RFA request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForApproveRequest.remove(entity));

        OperationResult<RequestForApprove> operationResult = new OperationResult<>(response, RequestForApprove.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForApproves().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
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
        log.info("View RFA by id:" + id);
        G4Response response = g4HttpClient.sendRequest(requestForApproveRequest.view(id));

        OperationResult<RequestForApprove> operationResult = new OperationResult<>(response, RequestForApprove.class);
        if (operationResult.isSuccess()) {
            Entities.getRequestForApproves().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
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

    public OperationResult<List<CurrentOwner>> possibleOwnersTeams(RequestForApprove entity) {
        log.info("Sending possible team owners request...");

        G4Response response = g4HttpClient.sendRequest(requestForApproveRequest.possibleOwnersTeams(entity));

        OperationResult<CurrentOwner[]> operationResult = new OperationResult<>(response, CurrentOwner[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    public OperationResult<RequestForApprove> sendForApprove(RequestForApprove entity) {
        log.info("Sending send for approval request...");

        G4Response response = g4HttpClient.sendRequest(requestForApproveRequest.sendForApprove(entity));

        OperationResult<RequestForApprove> operationResult = new OperationResult<>(response, RequestForApprove.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForApproves().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult<RequestForApprove> cancel(RequestForApprove entity) {
        log.info("Sending cancel request...");

        G4Response response = g4HttpClient.sendRequest(requestForApproveRequest.cancel(entity));

        OperationResult<RequestForApprove> operationResult = new OperationResult<>(response, RequestForApprove.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForApproves().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public void buildRFA(RequestForApprove requestForApprove, Result rfaNo, List<SearchRecord> entities) {
        fillRFIStaticData(requestForApprove);
        requestForApprove.setInternalRequestNo(rfaNo.getResult());
        fillRFIOrgUnit(requestForApprove);
        fillRFIFinderFile(requestForApprove);
        fillRFILink(requestForApprove, entities);
    }

    private void fillRFILink(RequestForApprove requestForApprove, List<SearchRecord> entities) {
        List<SearchRecord> eventswithvoice = entities.stream()
                .filter(w ->
                        w.getAttributes()
                                .containsKey("IS_VOICE_HIDDEN"))
                .collect(Collectors.toList());

        List<SearchRecord> eventwithvoice = getRandomItemsFromList(eventswithvoice, 1);

        List<Link> links = new ArrayList<>();
        for (SearchRecord record : eventwithvoice) {
            Link link = new Link();
            link.setLinkType("ARTIFACT");
            link.setLinkId(record.getId());
            setRFAEvent(record, links, link);
        }
        requestForApprove.setLinks(links);
    }

    private void setRFAEvent(SearchRecord record, List<Link> links, Link link) {
        link.setAttributes(record.getAttributes());
        link.setEntities(record.getEntities());
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
        requestForApprove.setClassification("TS");
        requestForApprove.setDescription("qe_" + RandomStringUtils.randomAlphabetic(5));
        requestForApprove.setSubject("qe_" + RandomStringUtils.randomAlphabetic(5));
    }

    public void setNextOwnersTeam(List<OrgUnit> currentOrgUnits, List<NextOwners> nextOwners) {
        for (int i = 0; i < currentOrgUnits.size(); i++) {
            NextOwners nextOwner = new NextOwners();
            nextOwner.setOwnerId(currentOrgUnits.get(i).getOrgUnitId());
            nextOwner.setOwnerName(currentOrgUnits.get(i).getOrgUnitName());
            nextOwner.setType("TEAM");
            nextOwners.add(nextOwner);
        }
    }
}
