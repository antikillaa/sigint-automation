package file_generator;

import abs.EntityList;
import abs.TeelaEntity;
import model.*;

import java.util.List;

/**
 * File generator.
 * Used for generate file with test data in G4 project.
 */
public class FileGenerator implements FileService {

    /**
     * FileService for G4files
     */
    private FileService fileService;

    public FileGenerator(Class<? extends TeelaEntity> entityClass) {
        if (entityClass.equals(Target.class)) {
            fileService = new TargetFileService();
        } else if (entityClass.equals(DuSubscriberEntry.class)) {
            fileService = new DuSubscriberFileService();
        } else if (entityClass.equals(EtisalatSubscriberEntry.class)) {
            fileService = new EtisalatSubscriberFileService();
        } else if (entityClass.equals(Phonebook.class)) {
            fileService = new PhoneBookFileService();
        } else if (entityClass.equals(SSMS.class)) {
            fileService = new SSMSFileService();
        } else {
            throw new Error("Unknown TeelaEntity. Unable to initialize FileGenerator instance!");
        }
    }

    /**
     * Read G4 entities from G4File
     *
     * @param file G4File
     * @return EntityList
     */
    @Override
    public EntityList read(G4File file) {
        return fileService.read(file);
    }

    /**
     * Write G4 entities to G4File
     *
     * @param entities list of entities
     * @return G4File
     */
    @Override
    public G4File write(List entities) {
        return fileService.write(entities);
    }

}
