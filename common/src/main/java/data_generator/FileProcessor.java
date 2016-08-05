package data_generator;

import abs.EntityList;
import org.apache.log4j.Logger;

import java.io.File;

public abstract class FileProcessor {

    Logger log = Logger.getLogger(FileProcessor.class);

    public abstract EntityList read(File file);

    public abstract File write(EntityList entityList);

}
