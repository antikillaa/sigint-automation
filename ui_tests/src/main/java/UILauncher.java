import reporter.AllureReporter;
import reporter.UIReporter;

public class UILauncher extends TeelaEmbeddable {

    private static AllureReporter reporter = new UIReporter();

    public UILauncher() {
        super(reporter);
    }
}
