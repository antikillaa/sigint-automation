package file_generator;

public class FileGenerator {

    private FileService fileService;

    public FileService targets(){
        fileService = new TargetFile();
        return fileService;
    }

    public FileService SSMS(){
        fileService = new SSMSFile();
        return fileService;
    }

}
