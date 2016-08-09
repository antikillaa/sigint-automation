package services;

import abs.EntityList;
import abs.SearchFilter;
import http.requests.UploadFilesRequest;
import json.RsClient;
import data_generator.SSMSFile;
import model.AppContext;
import model.SSMS;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.List;

public class SSMSService implements EntityService<SSMS> {

    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private Logger log = Logger.getLogger(SSMSService.class);
    private final String sigintHost = context.environment().getSigintHost();

    @Override
    public int add(SSMS entity) {
        return 0;
    }

    @Override
    public int remove(SSMS entity) {
        return 0;
    }

    @Override
    public EntityList<SSMS> list(SearchFilter filter) {
        return null;
    }

    @Override
    public int update(SSMS entity) {
        return 0;
    }

    @Override
    public SSMS view(String id) {
        return null;
    }

    public int upload(List<SSMS> ssmsList) {
        log.info("Writing S-SMSs to file..");
        File file = new SSMSFile().write(ssmsList);

        log.info("Upload file with " + ssmsList.size() + " S-SMS..");
        UploadFilesRequest request = new UploadFilesRequest();
        request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
        //file.deleteOnExit();

        /*
        Entity payload = Entity.entity(request.getBody(), request.getMediaType());

        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.client()
                .target(sigintHost + request.getURI())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(request.getCookie())
                .post(payload);

        //TODO
        UploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, UploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
        */
        return 0;
    }

}
