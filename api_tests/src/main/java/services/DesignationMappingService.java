package services;

import static org.junit.Assert.assertNotNull;

import csv.CSVDesignationMappingWriter;
import errors.OperationResultError;
import http.G4Response;
import http.OperationResult;
import http.requests.DesignationMappingRequest;
import java.util.Arrays;
import java.util.List;
import json.JsonConverter;
import model.DesignationMapping;
import model.DesignationMappingSearchResult;
import model.G4File;
import model.ImportResult;
import model.Result;
import model.SearchFilter;
import model.entities.Entities;
import org.apache.log4j.Logger;

public class DesignationMappingService implements EntityService<DesignationMapping> {

  private static final Logger log = Logger.getLogger(DesignationMappingService.class);
  private static DesignationMappingRequest request = new DesignationMappingRequest();

  /**
   * API: POST /api/workflow/designation-mappings
   *
   * @param entity Designation-mapping entity
   * @return OperationResult with entity
   */
  @Override
  public OperationResult<DesignationMapping> add(DesignationMapping entity) {
    log.info("Creating new Designation-mapping..");

    G4Response response = g4HttpClient.sendRequest(request.add(entity));

    OperationResult<DesignationMapping> operationResult = new OperationResult<>(response, DesignationMapping.class);
    if (operationResult.isSuccess()) {
      Entities.getDesignationMappings().addOrUpdateEntity(operationResult.getEntity());
    }
    return operationResult;
  }

  /**
   * API: DELETE /api/workflow/designation-mappings/{id}
   *
   * @param entity Designation-mapping entity
   * @return OperationResult with result
   */
  @Override
  public OperationResult<Result> remove(DesignationMapping entity) {
    log.info("Deleting Designation-mapping id: " + entity.getId());

    G4Response response = g4HttpClient.sendRequest(request.delete(entity.getId()));

    OperationResult<Result> operationResult = new OperationResult<>(response, Result.class);
    if (operationResult.isSuccess()) {
      Entities.getDesignationMappings().removeEntity(entity);
    }
    return operationResult;
  }

  @Override
  public OperationResult<List<DesignationMapping>> search(SearchFilter filter) {
    log.info("Search list of designation-mappings, filter: " + JsonConverter.toJsonString(filter));
    G4Response response = g4HttpClient.sendRequest(request.search(filter));

    OperationResult<DesignationMappingSearchResult> operationResult =
        new OperationResult<>(response, DesignationMappingSearchResult.class);
    if (operationResult.isSuccess()) {
      return new OperationResult<>(response, operationResult.getEntity().getResult());
    } else {
      return new OperationResult<>(response);
    }
  }

  /**
   * GET list of Designation-mappings.
   * API: GET /api/workflow/designation-mappings getDesignationMappings
   *
   * @return OperationResult with list of entities
   */
  @Override
  public OperationResult<List<DesignationMapping>> list() {
    log.info("Getting list of Designation-mappings");
    G4Response response = g4HttpClient.sendRequest(request.list());

    OperationResult<DesignationMapping[]> operationResult = new OperationResult<>(response, DesignationMapping[].class);

    if (operationResult.isSuccess() && operationResult.getEntity() != null) {
      List<DesignationMapping> designationMappings = Arrays.asList(operationResult.getEntity());

      return new OperationResult<>(response, designationMappings);
    } else {
      throw new OperationResultError(operationResult);
    }
  }

  /**
   * API: PUT /api/workflow/designation-mappings/{id}
   *
   * @param entity Designation-mapping entity
   * @return OperationResult with entity
   */
  @Override
  public OperationResult<DesignationMapping> update(DesignationMapping entity) {
    log.info("Updating Designation-mapping id: " + entity.getId());

    G4Response response = g4HttpClient.sendRequest(request.update(entity));

    OperationResult operationResult = new OperationResult<>(response);
    if (operationResult.isSuccess()) {
      Entities.getDesignationMappings().addOrUpdateEntity(entity);
      return new OperationResult<>(response, entity);
    } else {
      log.error("Error! Update designation-mapping was failed");
      return operationResult;
    }
  }

  /**
   * API: GET /api/workflow/designation-mappings/{id}
   *
   * @param id id of entity
   * @return Designation-mapping entity
   */
  @Override
  public OperationResult<DesignationMapping> view(String id) {
    log.info("View Designation-mapping details, id: " + id);

    G4Response response = g4HttpClient.sendRequest(request.view(id));

    return new OperationResult<>(response, DesignationMapping.class);
  }

  public G4File createCSVFile(List<DesignationMapping> designationMappings, boolean withHeader) {
    log.info("Writing " + designationMappings.size() + " DesignationMapping entries to file..");

    CSVDesignationMappingWriter writer = new CSVDesignationMappingWriter("designationMapping.csv", withHeader);
    G4File file = null;
    try {
      file = writer.writeEntitiesToCsv(designationMappings);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return file;
  }

  public OperationResult<ImportResult> upload(G4File file) {
    assertNotNull("null object for designation-mapping upload", file);

    log.info("Import Designation-mappings from file..");
    G4Response response = g4HttpClient.sendRequest(request.upload(file));

    return new OperationResult<>(response, ImportResult.class);
  }
}
