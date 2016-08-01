package services;

import abs.EntityList;
import abs.SearchFilter;
import conditions.Conditions;
import conditions.Verify;
import errors.NullReturnException;
import http.requests.targets.TargetRequest;
import json.JsonCoverter;
import json.RsClient;
import model.*;
import model.targetGroup.TargetGroupSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import utils.Parser;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class TargetService implements EntityService<Target> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(TargetService.class);
    private final String sigintHost = context.environment().getSigintHost();


    public int add(Target entity) {
        log.info("Creating new target");
        log.debug(Parser.entityToString(entity));

        TargetRequest request = new TargetRequest();
        Response response = rsClient.put(sigintHost + request.getURI(), entity, request.getCookie());
        Target target = JsonCoverter.readEntityFromResponse(response, Target.class, "id");
        if (target != null) {
            context.entities().getTargets().addOrUpdateEntity(target);
        } else {
            log.error("Add new target process was failed");
            throw new AssertionError("Add new target process was failed");
        }
        return response.getStatus();
    }

    public int upload(List<Target> targets) {
        log.info("Upload new " + targets.size() + " targets");
        TargetRequest request = new TargetRequest().upload();

        File file = writeTargetXLS(targets);
        request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        file.deleteOnExit();

        Entity payload = Entity.entity(request.getBody(), request.getMediaType());
        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.client()
                .target(sigintHost + request.getURI())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(request.getCookie())
                .post(payload);

        UploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

    public int remove(Target entity) {
        log.info("Deleting target id:" + entity.getId());

        TargetRequest request = new TargetRequest().delete(entity.getId());
        Response response = rsClient.delete(sigintHost + request.getURI(), request.getCookie());

        if (response.getStatus() == 200) {
            try {
                context.entities().getTargets().removeEntity(entity);
            } catch (NullReturnException e) {
                log.warn("Was unable to remove entity with id:" + entity.getId() + " as it doesn't in the list");
            }
        }
        return response.getStatus();
    }

    public EntityList<Target> list(SearchFilter filter) {
        log.debug(filter);
        TargetRequest request = new TargetRequest().search();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());
        TargetSearchResults searchResults = JsonCoverter.readEntityFromResponse(response, TargetSearchResults.class, "result");
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from Targets search");
        } else {
            return new EntityList<Target>(searchResults.getContent()) {
                public Target getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        }
    }

    public int update(Target entity) {
        log.info("Updating target id: " + entity.getId());
        log.debug(Parser.entityToString(entity));

        TargetRequest request = new TargetRequest();
        Response response = rsClient.post(sigintHost + request.getURI(), entity, request.getCookie());

        Result result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), Result.class);
        if (result != null) {
            Verify.isTrue(Conditions.equals.elements(result.getResult(), "ok"));
            context.entities().getTargets().addOrUpdateEntity(entity);
        } else {
            log.error("Error! Update target process was failed");
            throw new AssertionError("Error! Update target process was failed");
        }
        return response.getStatus();
    }

    public Target view(String id) {
        log.info("View target entry id:" + id);

        TargetRequest request = new TargetRequest().get(id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        Target resultTarget = JsonCoverter.readEntityFromResponse(response, Target.class, "result");
        log.debug(Parser.entityToString(resultTarget));

        return resultTarget;
    }

    public List<TargetGroup> getTargetGroups(String id) {
        log.info("Get targetGroups of target id:" + id);

        TargetRequest request = new TargetRequest().findTargetGroups(id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());

        TargetGroupSearchResult result = JsonCoverter.fromJsonToObject(response.readEntity(String.class), TargetGroupSearchResult.class);
        context.put("code", response.getStatus());
        if (result != null) {
            log.debug("Count of found groups: " + result.getResult().size());
            return result.getResult();
        } else {
            throw new AssertionError("Unable to get list of target groups");
        }
    }

    public File writeTargetXLS(List<Target> targets) {

        Map<Integer, Object[]> data = new HashMap<>();
        data.put(1, new Object[] {"ID", "Name", "Type", "Phones", "Keywords", "Target Groups"});

        for (int i = 0; i < targets.size(); i++) {
            List<String> targetFields = new ArrayList<>();

            //ID	Name	Type	Phones	Keywords    Target Groups
            targetFields.add(targets.get(i).getId());
            targetFields.add(targets.get(i).getName());
            targetFields.add(targets.get(i).getType().name());
            targetFields.add(targets.get(i).getPhones().toString().replace("[", "").replace("]", ""));
            targetFields.add(targets.get(i).getKeywords().toString().replace("[", "").replace("]", ""));
            // fill target groups
            List<TargetGroup> targetGroups = targets.get(i).getGroups();
            String groups = "";
            for (TargetGroup targetGroup : targetGroups) {
                if (groups.isEmpty()) {
                    groups = groups.concat(targetGroup.getName());
                } else {
                    groups = groups.concat(", " + targetGroup.getName());
                }
            }
            targetFields.add(groups);

            Object[] array = new Object[targetFields.size()];
            Object[] row = targetFields.toArray(array);

            Integer rowNum = i + 2;
            data.put(rowNum, row);
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Target sheet");

        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        for (Integer key : keyset) {
            Row row = sheet.createRow(rownum++);

            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Date)
                    cell.setCellValue((Date) obj);
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
                else if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
            }
        }

        File file = null;
        try {
            file = new File("Targets-" + new Date().getTime() + ".xls");
            log.info("Write targets to xls file: " + file.getAbsolutePath());
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            log.info("Excel written successfully..");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return file;
    }

    public void readTargetXLS(File file) {
        log.info("Read xls file: " + file.getName());
        try {
            FileInputStream inputStream = new FileInputStream(file);

            //Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            //Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {

                Row row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    switch(cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                    }
                }
                System.out.println("");
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
