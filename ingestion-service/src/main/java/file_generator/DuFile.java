package file_generator;

import abs.EntityList;
import model.DuSubscriberEntry;
import model.G4File;
import utils.FileHelper;

import java.util.Date;
import java.util.List;

public class DuFile extends FileService {

    @Override
    public EntityList read(G4File file) {
        return null;
    }

    @Override
    public G4File write(EntityList entityList) {
        List<DuSubscriberEntry> duSubscriberEntries = entityList.getEntities();
        return write(duSubscriberEntries);
    }

    public G4File write(List<DuSubscriberEntry> entries) {
        G4File file = new G4File("DuSubscriber" + new Date().getTime() + ".csv");

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
