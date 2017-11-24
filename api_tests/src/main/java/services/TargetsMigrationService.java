package services;

import http.G4HttpClient;
import http.G4Response;
import http.OperationResult;
import http.requests.TargetMigrationRequest;
import model.G4File;
import model.Profile;
import model.ProfileCategory;
import model.TargetGroup;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import utils.DateHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static utils.RandomGenerator.generateRandomInteger;


public class TargetsMigrationService {

    private static final Path IMPORT_DIR = Paths.get(System.getProperty("user.dir"), "importTargetsData");
    private static final String FILENAME = "import-data.xlsx";
    private static XSSFWorkbook workbook = new XSSFWorkbook();
    private static G4HttpClient g4HttpClient = new G4HttpClient();
    private static final TargetMigrationRequest request = new TargetMigrationRequest();
    private static Logger log = Logger.getLogger(TargetsMigrationService.class);

    public static void writeTargets(List<Profile> targets) {

        Map<Integer, Object[]> data = new HashMap<>();
        data.put(1, new Object[]{"ID", "name", "description", "category", "criminalRecord", "classification", "active", "activeUntil", "targetGroupsID"}); // columns name

        log.info("Targets_List data...");

        Integer rowNum = 2;
        Iterator<Profile> iterator = targets.iterator();
        while (iterator.hasNext()) {
            try {
                Profile target = iterator.next();
                List<String> targetFields = new ArrayList<>(); // new target row

                targetFields.add(target.getId()); // ID
                targetFields.add(target.getName()); // name
                targetFields.add(target.getProperties().getDescription()); // description
                targetFields.add(target.getCategory().equals("Person of Interest") ?
                        ProfileCategory.POI.name() : ProfileCategory.Dangerous.name()); // category
                targetFields.add(target.getCriminalRecord().name()); // criminalRecord
                targetFields.add(target.getClassification()); // classification
                targetFields.add(target.getActive().name()); // active
                targetFields.add(DateHelper.dateToFormat(target.getActiveUntil(), "YYYY-MM-dd"));// activeUntil

                // fill targetGroupsID
                List<TargetGroup> targetGroups = target.getGroups();
                String groupIDs = "";
                for (TargetGroup group : targetGroups) {
                    groupIDs = groupIDs.isEmpty() ? groupIDs.concat(group.getId()) : groupIDs.concat("," + group.getId());
                }
                targetFields.add(groupIDs);

                Object[] array = new Object[targetFields.size()];
                Object[] row = targetFields.toArray(array);

                data.put(rowNum, row);
                rowNum++;
            } catch (NullPointerException e) { // FIXME skip invalid random data
                iterator.remove();
            }
        }
        writeSheet(data, "Targets_List");
    }

    public static void writeGroups(List<TargetGroup> groups) {

        log.info("Target_Groups_List data...");

        Map<Integer, Object[]> data = new HashMap<>();
        data.put(1, new Object[]{"ID", "targetGroup", "parentID", "team"}); // columns name

        for (int i = 0; i < groups.size(); i++) {
            List<String> groupFields = new ArrayList<>(); // new group row
            groupFields.add(groups.get(i).getId()); // ID
            groupFields.add(groups.get(i).getName()); // name

            // parentID
            if (i % 2 == 1) {
                groupFields.add(groups.get(generateRandomInteger(0, i - 1)).getId());
            } else {
                groupFields.add("");
            }

            // team
            String fieldTeam = "";
            for (String team : groups.get(i).getAssignedTeams()) {
                fieldTeam = fieldTeam.isEmpty() ? fieldTeam.concat(team) : fieldTeam.concat(", " + team);
            }
            groupFields.add(fieldTeam); // team

            Object[] array = new Object[groupFields.size()];
            Object[] row = groupFields.toArray(array);

            Integer rowNum = i + 2;
            data.put(rowNum, row);
        }
        writeSheet(data, "Target_Groups_List");
    }

    public static void writeIdentifiers(List<Profile> targets) {

        log.info("Target_Identifiers_List data...");

        Map<Integer, Object[]> data = new HashMap<>();
        data.put(1, new Object[]{"ID", "target", "type", "name", "sources"}); // columns name

        Integer identifiersTotal = 0;
        for (Profile target : targets) {
            Integer identifiersCount = target.getIdentifiers().size();
            for (int j = 0; j < identifiersCount; j++) {
                try {
                    List<String> identifiersFields = new ArrayList<>();// new identifiers row

                    identifiersFields.add(target.getId()); // ID
                    identifiersFields.add(target.getName()); // target
                    identifiersFields.add(target.getIdentifiers().get(j).getType().name()); // type

                    Assert.assertFalse(target.getIdentifiers().get(j).getValue().isEmpty());
                    identifiersFields.add(target.getIdentifiers().get(j).getValue()); // name

                    identifiersFields.add(target.getIdentifiers().get(j).getSources().get(0).name()); // source

                    Object[] array = new Object[identifiersFields.size()];
                    Object[] row = identifiersFields.toArray(array);

                    Integer rowNum = identifiersTotal + 2;
                    data.put(rowNum, row);
                    identifiersTotal++;
                } catch (Exception e) { // FIXME skip invalid random data
                }
            }
        }
        writeSheet(data, "Target_Identifiers_List");
    }

    private static void writeSheet(Map<Integer, Object[]> data, String sheetName) {
        XSSFSheet sheet = workbook.createSheet(sheetName);

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
    }

    public static G4File writeFile() {
        G4File file = null;
        try {
            if (Files.exists(IMPORT_DIR)) {
                FileUtils.deleteQuietly(new File(IMPORT_DIR.toString()));
            }
            File dir = new G4File(IMPORT_DIR.normalize().toString());
            if (!dir.exists()) {
                Assert.assertTrue("Unable create directory: " + IMPORT_DIR.normalize().toString(), dir.mkdirs());
            }

            String absolutePath = Paths.get(IMPORT_DIR.toString(), FILENAME).toString();
            file = new G4File(absolutePath);
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            log.info("Excel file with Targets_List sheet written successfully: " + absolutePath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return file;
    }

    public static OperationResult uploadTargetsFile(G4File file) {
        log.info("Import Targets file to env...");
        G4Response response = g4HttpClient.sendRequest(request.uploadTargets(file));

        OperationResult<Profile> operationResult = new OperationResult<>(response);
        if (operationResult.isSuccess()) {
            log.info(operationResult.getCode() + ", " + operationResult.getMessage());
        } else {
            log.error(operationResult.getCode() + ", " + operationResult.getMessage());
        }
        return operationResult;
    }


}
