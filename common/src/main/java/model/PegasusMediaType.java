package model;

import javax.ws.rs.core.MediaType;

public class PegasusMediaType {
    public static final String PEGASUS_JSON = "application/vnd.pegasus+json";
    public final static String TEXT_CSV = "text/csv";
    public final static MediaType TEXT_CSV_TYPE = new MediaType("text", "csv");
}

