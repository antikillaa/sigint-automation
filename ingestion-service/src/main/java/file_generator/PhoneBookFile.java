package file_generator;

import abs.EntityList;
import model.Phonebook;
import utils.FileHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PhoneBookFile extends FileService {


    @Override
    public File write(EntityList entityList) {
        List<Phonebook> phonebooks = entityList.getEntities();
        return write(phonebooks);
    }

    /**
     Records look like this:

     967700000000," Arthur King "," postpaid 123 Main St Phoenix AZ ",\N,"usa","Y","mobile"

     Records are terminated by a newline character.
     Some records have a carriage return character inside.
     Some records have a newline character inside, preceded by a backslash.
     Some records have 2 unknown \N fields next to each other.
     // The parts are: phone number, name, address, \N, possibly another \N, country, provider, location.
     */
    public File write(List<Phonebook> phonebooks) {
        File file = null;
        try {
            file = File.createTempFile("Phonebooks", ".csv");
            log.info("File for phonebooks created: " + file.getAbsolutePath());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new Error("Unable to create Phonebook CVS file!");
        }

        for (Phonebook phonebook : phonebooks) {
            FileHelper.writeLineToFile(file, toCSVString(phonebook));
        }

        log.info("Phonebook written successfully to cvs file..");
        return file;
    }

    private String toCSVString(Phonebook phonebook) {
        return String.format(
                "\"%s\",\"%s\",\"%s\",\\N,\\N,\"%s\",\"%s\",\"%s\"",
                phonebook.getPhoneNumber(), phonebook.getName(), phonebook.getAddress(),
                phonebook.getCountry(), phonebook.getProvider(), phonebook.getLocation()
        );
    }

    @Override
    public EntityList read(File file) {
        return null;
    }
}
