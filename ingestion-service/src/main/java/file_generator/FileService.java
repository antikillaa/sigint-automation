package file_generator;

import abs.EntityList;
import abs.TeelaEntity;
import model.G4File;
import org.apache.log4j.Logger;

import java.util.List;

interface FileService<T extends TeelaEntity> {

    Logger log = Logger.getLogger(FileService.class);

    EntityList read(G4File file);

    G4File write(List<T> entities);
}
