import reporter.AllureReporter;

/**
 * Created by dm on 3/30/16.
 */
public class APILauncher extends TeelaEmbeddable {

    private static AllureReporter reporter = new AllureReporter();

    public APILauncher() {
        super(reporter);
    }
}
