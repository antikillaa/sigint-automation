package file_generator;

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
        } else if (entityClass.equals(XSMS.class)) {
            fileService = new XSMSFileService();
        } else if (entityClass.equals(XVoiceMetadata.class)) {
            fileService = new XVoiceMetadataFileService();
        } else {
            throw new Error("Unknown TeelaEntity. Unable to initialize FileGenerator instance!");
        }
    }

    /**
     * Write G4 entities to G4File
     *
     * @param entities list of entities
     * @return G4File
     */
    @SuppressWarnings("unchecked")
    @Override
    public G4File write(List entities) {
        mkDirs();
        return fileService.write(entities);
    }

    private void mkDirs() {
        G4File file = new G4File(path);

        try {
            boolean newDir = file.mkdirs();
            if (newDir) {
                log.info("Dir: " + file.getAbsolutePath() + " was created");
            } else {
                log.info("Dir: " + file.getAbsolutePath() + " already exist");
            }
        } catch (SecurityException e) {
            log.error(e.getMessage());
            log.trace(e);
            throw e;
        }
    }

}
