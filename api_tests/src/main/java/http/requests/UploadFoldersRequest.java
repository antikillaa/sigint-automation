package http.requests;

import http.HttpMethod;
import model.FileMetaFilter;
import model.PegasusMediaType;
import model.Source;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadFoldersRequest extends HttpRequest {

  private final static String URI = "/api/upload/folders";

  public UploadFoldersRequest() {
    super(URI);
    // API works only with specified Content-Type
    this.setMediaType(PegasusMediaType.PEGASUS_JSON_V1);
  }

  public UploadFoldersRequest search(FileMetaFilter filter) {
    this
        .setURI(URI + "/_search")
        .setHttpMethod(HttpMethod.POST)
        .setPayload(filter);

    return this;
  }

  public UploadFoldersRequest count(FileMetaFilter filter) {
    this
        .setURI(URI + "/_count")
        .setHttpMethod(HttpMethod.POST)
        .setPayload(filter);

    return this;
  }

  static public FileMetaFilter buildSearchFilter(Source source, String datePattern) {
    String path = "/" + source.getType()
        + "/" + source.getName()
        + "/" + new SimpleDateFormat(datePattern).format(new Date());

    return new FileMetaFilter(path);
  }

  static public FileMetaFilter buildSearchFilter(String datePattern) {
    String path = new SimpleDateFormat(datePattern).format(new Date());

    return new FileMetaFilter(path);
  }
}
