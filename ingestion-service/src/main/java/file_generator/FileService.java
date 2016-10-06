package file_generator;

import abs.TeelaEntity;
import model.G4File;
import org.apache.log4j.Logger;

import java.util.List;

interface FileService<T extends TeelaEntity> {

    Logger log = Logger.getLogger(FileService.class);

    G4File write(List<T> entities);
}
