package file_generator;

import abs.EntityList;
import org.apache.log4j.Logger;

import java.io.File;

public abstract class FileService {

    Logger log = Logger.getLogger(FileService.class);

    public abstract EntityList read(File file);

    public abstract File write(EntityList entityList);

}
