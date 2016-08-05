package data_generator;

import abs.EntityList;
import model.SSMS;

import java.io.File;
import java.text.SimpleDateFormat;

public class SSMSFile extends FileProcessor {

    public String entityToCSVString(SSMS ssms) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");

        return String.format("%d,\"%s\",%d,%d,%d," +
                        "%d,%d,%d,%d,\"%s\",\"%s\"," +
                        "\"%s\",\"%s\",\"%s\",%d,%d," +
                        "%s,\"%s\",%s,\"%s\"," +
                        "%s,\"%s\",%s,\"%s\"," +
                        "\"%s\"," +
                        "\"%s\",%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"" +
                        "\"%s\",%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"" +
                        "%s,%d,%s,\"%s\",\"%s\",\"%s\",\"%s\"",
                ssms.getCdr_id(), df.format(ssms.getEvent_time()), ssms.getUnit(), ssms.getMatrix_ts(), ssms.getNi(),
                ssms.getOpc(), ssms.getDpc(), ssms.getHit_count(), ssms.getMaskmarked(), ssms.getSrcip(), ssms.getDscip(),
                df.format(ssms.getSms_time()), ssms.getSmstimezone(), ssms.getTxt(), ssms.getHaveparts(), ssms.getTotalparts(),
                ssms.getCalled_imsi(), ssms.getCalled_imsi_cc(), ssms.getCalled_imsi_ac(), ssms.getCalled_imsi_hit(),
                ssms.getCaller_imsi(), ssms.getCaller_imsi_cc(), ssms.getCaller_imsi_ac(), ssms.getCaller_imsi_hit(),
                ssms.getPattern(),
                ssms.getCalled(), ssms.getCalled_non(), ssms.getCalled_mod(),
                ssms.getCalled_rule(), ssms.getCalled_hit(), ssms.getCalled_cc(), ssms.getCalled_ac(),
                ssms.getCaller(), ssms.getCaller_non(), ssms.getCaller_mod(),
                ssms.getCaller_rule(), ssms.getCaller_hit(), ssms.getCaller_cc(), ssms.getCaller_ac(),
                ssms.getSmsc(), ssms.getSmsc_non(), ssms.getSmsc_mod(),
                ssms.getSmsc_rule(), ssms.getSmsc_hit(), ssms.getSmsc_cc(), ssms.getSmsc_ac()
        );
    }

    @Override
    public File write(EntityList entityList) {
        return null;
    }

    @Override
    public EntityList read(File file) {
        return null;
    }

}
