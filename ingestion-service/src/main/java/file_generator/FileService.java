package file_generator;

import abs.EntityList;
import model.G4File;
import org.apache.log4j.Logger;

public abstract class FileService {

    Logger log = Logger.getLogger(FileService.class);

    public abstract EntityList read(G4File file);

    public abstract G4File write(EntityList entityList);

}
