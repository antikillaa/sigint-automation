package ae.pegasus.framework.services;

import ae.pegasus.framework.errors.OperationResultError;
import ae.pegasus.framework.http.G4Response;
import ae.pegasus.framework.http.OperationResult;
import ae.pegasus.framework.http.requests.RequestForInformationRequest;
import ae.pegasus.framework.json.JsonConverter;
import ae.pegasus.framework.model.*;
import ae.pegasus.framework.model.entities.Entities;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RequestForInformationService implements EntityService<RequestForInformation> {
    private static Logger log = Logger.getLogger(ReportService.class);
    private static RequestForInformationRequest requestForInformationRequest = new RequestForInformationRequest();


    @Override
    public OperationResult<?> add(RequestForInformation entity) {
        log.info("Sending create new RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.add(entity));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    @Override
    public OperationResult remove(RequestForInformation entity) {
        log.info("Sending delete a RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.remove(entity));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult<?> submit(RequestForInformation entity) {
        log.info("Sending submit RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.submit(entity));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult<?> submitTookOwnership(RequestForInformation entity) {
        log.info("Sending submit took ownership RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.submitTookOwnership(entity));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult<?> approve(RequestForInformation entity) {
        log.info("Sending submit RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.approve(entity));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult<?> cancel(RequestForInformation entity) {
        log.info("Sending cancel RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.cancel(entity));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    @Override
    public OperationResult<List<RequestForInformation>> search(SearchFilter filter) {
        return null;
    }

    @Override
    public OperationResult<List<RequestForInformation>> list() {
        return null;
    }

    @Override
    public OperationResult<RequestForInformation> update(RequestForInformation entity) {
        return null;
    }

    @Override
    public OperationResult<RequestForInformation> view(String id) {
        log.info("View RFI by id:" + id);
        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.view(id));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class);
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        }
        return operationResult;
    }

    public OperationResult<?> send(RequestForInformation entity) {
        log.info("Sending submit RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.send(entity));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult<?> takeOwnership(RequestForInformation entity) {
        log.info("Sending take ownership RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.takeOwnership(entity));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public OperationResult<Result> generateNumber() {
        log.info("Sending generate a RFI number request...");

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.generateNumber());

        OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
        if (operationResult.isSuccess()) {
            return operationResult;
        } else {
            throw new OperationResultError(operationResult);
        }
    }

    public OperationResult<List<CurrentOwner>> possibleOwnersMembers(RequestForInformation entity) {
        log.info("Sending possible owners request...");

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.possibleOwnersMembers(entity));

        OperationResult<CurrentOwner[]> operationResult = new OperationResult<>(response, CurrentOwner[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    public OperationResult<List<CurrentOwner>> possibleOwnersTeams(RequestForInformation entity) {
        log.info("Sending possible owners request...");

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.possibleOwnersTeams(entity));

        OperationResult<CurrentOwner[]> operationResult = new OperationResult<>(response, CurrentOwner[].class);
        if (operationResult.isSuccess()) {
            return new OperationResult<>(response, Arrays.asList(operationResult.getEntity()));
        } else {
            return new OperationResult<>(response);
        }
    }

    public OperationResult<?> unassign(RequestForInformation entity) {
        log.info("Sending unassign RFI request... :" + JsonConverter.toJsonString(entity));

        G4Response response = g4HttpClient.sendRequest(requestForInformationRequest.unassign(entity));

        OperationResult<RequestForInformation> operationResult = new OperationResult<>(response, RequestForInformation.class, "result");
        if (operationResult.isSuccess()) {
            Entities.getRequestForInformations().addOrUpdateEntity(operationResult.getEntity());
        } else {
            throw new OperationResultError(operationResult);
        }
        return operationResult;
    }

    public void buildRFI(RequestForInformation requestForInformation, Result rfiNo) {
        fillRFIStaticData(requestForInformation);
        requestForInformation.setInternalRequestNo(rfiNo.getResult());
        fillRFIOrgUnit(requestForInformation);
        fillRFIFinderFile(requestForInformation);
    }

    private void fillRFIStaticData(RequestForInformation requestForInformation) {
        requestForInformation.setObjectType("RFI");
        requestForInformation.setPriority("IMPORTANT");
        requestForInformation.setType("INTERNAL");
        requestForInformation.setClassification("OUO");
        requestForInformation.setTimeToResponse(3);
        requestForInformation.setRequired("qe_" + RandomStringUtils.randomAlphabetic(5));
        requestForInformation.setSubject("qe_" + RandomStringUtils.randomAlphabetic(5));
    }

    private void fillRFIOrgUnit(RequestForInformation requestForInformation) {
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
        requestForInformation.setOrgUnits(Collections.singletonList(orgUnit));
    }

    private void fillRFIFinderFile(RequestForInformation requestForInformation) {
        FinderFile finderFile = Entities.getFinderFiles().getLatest();
        DirectCaseFile directCase = new DirectCaseFile();
        directCase.setLinkId(finderFile.getId());
        directCase.setLinkNo(finderFile.getId());
        directCase.setLinkName(finderFile.getName());
        directCase.setLinkType("FILE");
        requestForInformation.setDirectFile(directCase);
    }

    public void setNextOwnersMember(List<CurrentOwner> currentOwner, List<NextOwners> allOwners) {
        for (int i = 0; i < currentOwner.size(); i++) {
            NextOwners nextOwner = new NextOwners();
            nextOwner.setOwnerId(currentOwner.get(i).getOwnerId());
            nextOwner.setOwnerName(currentOwner.get(i).getOwnerName());
            nextOwner.setType(currentOwner.get(i).getType());
            allOwners.add(nextOwner);
        }
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
