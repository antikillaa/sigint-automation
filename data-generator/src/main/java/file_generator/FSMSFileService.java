package file_generator;

import model.FSMS;
import model.G4File;
import model.PegasusMediaType;
import utils.FileHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * F-SMS file service.
 * Used for generate F-SMS.csv files
 */
class FSMSFileService implements FileService<FSMS> {

    private String entityToCSVString(FSMS fsms) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return String.format("%s,%s,%s,\"%s\",%s,%s," +
                        "\"%s\",\"%s\",\"%s\"," +
                        "%s,%s,%s,%s,%s",
                fsms.getId(), fsms.getFromNumber(), fsms.getToNumber(), fsms.getTxt(), fsms.getImsi(), fsms.getImei(),
                df.format(fsms.getDatetime()), df.format(fsms.getDateTimeEnd()), df.format(fsms.getCallLength()),
                fsms.getOpcRaw(), fsms.getOriginalTmsi(), fsms.getOriginalTmsi(), fsms.getTargetNumber(), fsms.getCallingGlobalTitle()
        );
    }

    @Override
    public G4File write(List<FSMS> fsmsList) {
        String fileName = "FSMS" + new Date().getTime() + ".csv";
        G4File file = new G4File(path + fileName);
        file.setMediaType(PegasusMediaType.TEXT_CSV_TYPE);

        String fields = "\"ID\",\"Calling Number Raw\",\"Called Number Raw\",\"SMS Text\",\"IMSI\",\"IMEI\",\"Datetime\",\"Datetime End\",\"call_length\",\"opc_raw\",\"Original TMSI\",\"Target Number\",\"calling_global_title\"";
        FileHelper.writeLineToFile(file, fields);

        for (FSMS fsms : fsmsList) {
            FileHelper.writeLineToFile(file, entityToCSVString(fsms));
        }

        log.info("FSMS list written successfully to cvs file: " + file.getAbsolutePath());
        return file;
    }

}
