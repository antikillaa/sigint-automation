package data_generator;

import abs.EntityList;
import model.Target;
import model.TargetGroup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class TargetFile extends FileProcessor {

    @Override
    public File write(EntityList entityList) {
        List<Target> targets = entityList.getEntities();
        return write(targets);
    }

    public File write(List<Target> targets) {

        Map<Integer, Object[]> data = new HashMap<>();

        data.put(1, new Object[] {"ID", "Name", "Type", "Phones", "Keywords", "Target Groups"}); // columns name

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

    @Override
    public EntityList read(File file) {
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
        // TODO
        return null;
    }
}
