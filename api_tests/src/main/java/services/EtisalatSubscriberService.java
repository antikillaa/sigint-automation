package services;

import abs.EntityList;
import abs.SearchFilter;
import abs.SearchResult;
import errors.NullReturnException;
import http.requests.phonebook.EtisalatSubscriberDataRequest;
import json.JsonCoverter;
import json.RsClient;
import model.AppContext;
import model.EtisalatSubscriberEntry;
import model.phonebook.EntriesUploadResult;
import model.phonebook.EtisalatSubscriberSearchResult;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import service.EntityService;
import utils.FileHelper;
import utils.RandomGenerator;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EtisalatSubscriberService implements EntityService<EtisalatSubscriberEntry> {

    private Logger log = Logger.getLogger(EtisalatSubscriberService.class);
    private static RsClient rsClient = new RsClient();
    private static AppContext context = AppContext.getContext();
    private final String sigintHost = context.environment().getSigintHost();

    @Override
    public int add(EtisalatSubscriberEntry entity) {

        EtisalatSubscriberDataRequest request = new EtisalatSubscriberDataRequest().upload();
        try {
            log.info("Writing EtisalatSubscriberEntry to file...");
            String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-etisalat_sample";
            File file = File.createTempFile(fileName, "-01." + RandomGenerator.generateCountryCode()); //yyyyMMdd-filename-No.DX
            FileHelper.writeLineToFile(file, entryToString(entity));
            request.addBodyFile("file", file, MediaType.APPLICATION_JSON_TYPE);
            file.deleteOnExit();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AssertionError("Failed to create file");
        }

        Entity payload = Entity.entity(request.getBody(), request.getMediaType());
        log.debug("Sending request to " + sigintHost + request.getURI());
        Response response = rsClient.client()
                .target(sigintHost + request.getURI())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(request.getCookie())
                .post(payload);

        EntriesUploadResult uploadResult = JsonCoverter.readEntityFromResponse(response, EntriesUploadResult.class, "result");
        if (uploadResult != null) {
            context.put("uploadResult", uploadResult);
        }
        return response.getStatus();
    }

    @Override
    public int remove(EtisalatSubscriberEntry entity) {
        return 0;
    }

    @Override
    public EntityList<EtisalatSubscriberEntry> list(SearchFilter filter) {
        EtisalatSubscriberDataRequest request = new EtisalatSubscriberDataRequest().search();
        Response response = rsClient.post(sigintHost + request.getURI(), filter, request.getCookie());

        SearchResult<EtisalatSubscriberEntry> searchResults = JsonCoverter.readEntityFromResponse(response, EtisalatSubscriberSearchResult.class, "result");
        if (searchResults == null) {
            throw new AssertionError("Unable to read search results from EtisalatSubscriber search");
        } else {
            return new EntityList<EtisalatSubscriberEntry>(searchResults.getContent()) {
                public EtisalatSubscriberEntry getEntity(String param) throws NullReturnException {
                    throw new NotImplementedException();
                }
            };
        }
    }

    @Override
    public int update(EtisalatSubscriberEntry entity) {
        return 0;
    }

    @Override
    public EtisalatSubscriberEntry view(String id) {
        EtisalatSubscriberDataRequest request = new EtisalatSubscriberDataRequest().get(id);
        log.info("Getting derails of EtisalatSubscriberData entry by id: " + id);
        Response response = rsClient.get(sigintHost + request.getURI(), request.getCookie());
        return JsonCoverter.readEntityFromResponse(response, EtisalatSubscriberEntry.class, "result");
    }


    public String entryToString(EtisalatSubscriberEntry entry) {
        String DELIMETER = "~";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return  entry.getAction() + DELIMETER +
                entry.getPhoneNumber() + DELIMETER +
                entry.getAccountSuffix() + DELIMETER +
                entry.getPartyId() + DELIMETER +
                entry.getName() + DELIMETER +
                entry.getAccountNameArabic() + DELIMETER +
                entry.getUserIdOrName() + DELIMETER +
                entry.getInstallationBuilding() + DELIMETER + 
                entry.getInstallationFlatNumber() + DELIMETER +
                entry.getInstallationFloor() + DELIMETER +
                entry.getInstallationStreetName() + DELIMETER +
                entry.getInstallationPlotNumber() + DELIMETER +
                entry.getInstallationMapNumber() + DELIMETER +
                entry.getInstallationSector() + DELIMETER +
                entry.getInstallationTownCode() + DELIMETER +
                entry.getInstallationTownName() + DELIMETER +
                entry.getInstallationTownEmirate() + DELIMETER +
                entry.getFirstAddressLine() + DELIMETER +
                entry.getSecondAddressLine() + DELIMETER +
                entry.getPoBoxNumber() + DELIMETER +
                entry.getCustomerCategoryCode() + DELIMETER +
                entry.getCustomerCategoryCodeDesc() + DELIMETER +
                dateFormat.format(entry.getDateOfInstallation()) + DELIMETER +
                entry.getCountryCodeOriginal() + DELIMETER +
                entry.getCountry() + DELIMETER +
                entry.getSubscriberAccountStatusCode() + DELIMETER +
                entry.getSubscriberAccountStatusDesc() + DELIMETER +
                entry.getProductGroupCode() + DELIMETER +
                entry.getProductGroupDesc() + DELIMETER +
                entry.getProductCode() + DELIMETER +
                entry.getProductDesc() + DELIMETER +
                entry.getImsi() + DELIMETER +
                entry.getIdentificationTypeCode() + DELIMETER +
                entry.getIdentificationTypeDesc() + DELIMETER +
                entry.getIdentificationInfo() + DELIMETER +
                entry.getProvisionedRegionCode() + DELIMETER +
                entry.getProvisionedRegionCodeDesc() + DELIMETER +
                entry.getCityId() + DELIMETER +
                entry.getCityName();
    }
}
