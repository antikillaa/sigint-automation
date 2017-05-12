import reporter.AllureReporter;
import reporter.UIReporter;

public class UILauncher extends G4Embeddable {

    private static AllureReporter reporter = new UIReporter();

    public UILauncher() {
        super(reporter);
    }
}
