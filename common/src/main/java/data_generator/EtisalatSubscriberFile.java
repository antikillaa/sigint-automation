package data_generator;

import abs.EntityList;
import model.EtisalatSubscriberEntry;
import utils.FileHelper;
import utils.RandomGenerator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class EtisalatSubscriberFile extends FileProcessor {

    @Override
    public EntityList read(File file) {
        return null;
    }

    @Override
    public File write(EntityList entityList) {
        List<EtisalatSubscriberEntry> entries = entityList.getEntities();
        return write(entries);
    }

    public File write(List<EtisalatSubscriberEntry> entries) {

        File file = null;
        try {
            log.info("Create Etisalat Subscriber file..");
            String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-etisalat_sample";
            file = File.createTempFile(fileName, "-01." + RandomGenerator.generateCountryCode()); //yyyyMMdd-filename-No.DX
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AssertionError("Failed to create Etisalat Subscriber file");
        }

        for (EtisalatSubscriberEntry entry : entries) {
            FileHelper.writeLineToFile(file, entryToString(entry));
        }

        return file;
    }

    private String entryToString(EtisalatSubscriberEntry entry) {
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
