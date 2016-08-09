package data_generator;

import abs.EntityList;
import model.DuSubscriberEntry;
import utils.FileHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DuFile extends FileProcessor {

    @Override
    public EntityList read(File file) {
        return null;
    }

    @Override
    public File write(EntityList entityList) {
        List<DuSubscriberEntry> duSubscriberEntries = entityList.getEntities();
        return write(duSubscriberEntries);
    }

    public File write(List<DuSubscriberEntry> entries) {

        File file = null;
        try {
            file = File.createTempFile("DuSubscriberEntry", ".csv");
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new Error("Unable to create new 'DuSubscriberEntry' .csv file");
        }

        for (DuSubscriberEntry entry : entries) {
            FileHelper.writeLineToFile(file, entryToString(entry));
        }
        return file;
    }

    private String entryToString(DuSubscriberEntry entry) {
        String DELIMETER = "~";
        return  entry.getPhoneNumber() + DELIMETER +
                entry.getTitle() + DELIMETER +
                entry.getFirstName() + DELIMETER +
                entry.getMiddleName() + DELIMETER +
                entry.getLastName() + DELIMETER +
                entry.getPoBox() + DELIMETER +
                entry.getCity() + DELIMETER +
                entry.getNationality() + DELIMETER +
                entry.getVisaType() + DELIMETER +
                entry.getVisaNumber() + DELIMETER +
                entry.getIdType() + DELIMETER +
                entry.getIdNumber() + DELIMETER +
                entry.getStatus() + DELIMETER +
                entry.getCustomerType() + DELIMETER +
                entry.getServiceType() + DELIMETER +
                entry.getCustomerCode();
    }


}
