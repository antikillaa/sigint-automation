import reporter.AllureReporter;

public class APILauncher extends TeelaEmbeddable {

    private static AllureReporter reporter = new AllureReporter();

    public APILauncher() {
        super(reporter);
    }
}
