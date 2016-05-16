import reporter.AllureReporter;
import reporter.UIReporter;

/**
 * Created by dm on 3/30/16.
 */
public class UILauncher extends TeelaEmbeddable {

    private static AllureReporter reporter = new UIReporter();

    public UILauncher() {
        super(reporter);
    }
}
