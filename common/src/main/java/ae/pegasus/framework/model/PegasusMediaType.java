package ae.pegasus.framework.model;

import javax.ws.rs.core.MediaType;

public class PegasusMediaType {
    public static final String PEGASUS_JSON = "application/vnd.pegasus+json";
    public static final String PEGASUS_JSON_V1 = "application/vnd.ae.pegasus.v1+json";
    public static final String TEXT_CSV = "text/csv";
    public static final MediaType TEXT_CSV_TYPE = new MediaType("text", "ae/pegasus/framework/csv");
    public static final MediaType APPLICATION_JSON_TYPE = new MediaType("application", "ae/pegasus/framework/json");
    public static final String MS_EXCEL = "application/vnd.ms-excel";
    public static final MediaType MS_EXCEL_TYPE = new MediaType("application", "vnd.ms-excel");
    public static final MediaType AUDIO = new MediaType("audio", "wav");
    public static final MediaType TIFF = new MediaType("image", "tiff");
    public static final MediaType PEGASUS_XLSX = new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    public static final MediaType ZDF = new MediaType("text", "xml");
    public static final MediaType EML = new MediaType("message", "rfc822");
}

