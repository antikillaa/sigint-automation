package data_generator;

public class DataGenerator {

    private FileProcessor fileProcessor;

    public FileProcessor targets(){
        fileProcessor = new TargetFile();
        return fileProcessor;
    }

}
