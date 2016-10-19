package file_generator;

import model.G4File;
import model.PegasusMediaType;
import model.XVoiceMetadata;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * X-VoiceMetadata file service.
 * Used for generate X-VoiceMetadata.xls files
 */
class XVoiceMetadataFileService implements FileService<XVoiceMetadata> {

    @Override
    public G4File write(List<XVoiceMetadata> list) {

        log.info("X-VoiceMetadata size: " + list.size());

        // Map <row number, fields array> for XLS table
        Map<Integer, Object[]> data = new HashMap<>();
        // Columns headers
        data.put(1, new Object[]{"EventTime", "Sender", "Receiver", "", ""});

        // fill data map
        for (int i = 0; i < list.size(); i++) {
            List<String> fields = new ArrayList<>();

            //"EventTime", "Sender", "Receiver"
            fields.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getEventTime()));
            fields.add(list.get(i).getSender());
            fields.add(list.get(i).getReceiver());
            fields.add("");
            fields.add("");

            Object[] array = new Object[fields.size()];
            Object[] row = fields.toArray(array);

            Integer rowNum = i + 2;
            data.put(rowNum, row);
        }

        // Create and fill XSSFWorkbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("XVoice sheet");

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

        // write XSSFWorkbook to XLS G4File
        G4File file = null;
        try {
            String fileName = "X-VoiceMetadata-" + new Date().getTime() + ".xls";
            file = new G4File(path + fileName);
            file.setMediaType(PegasusMediaType.MS_EXCEL_TYPE);
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            log.info("XLS file with X-VoiceMetadata entries written successfully: " + file.getAbsolutePath());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return file;
    }

}
