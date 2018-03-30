package ae.pegasus.framework;

import ae.pegasus.framework.reporter.AllureReporter;
import ae.pegasus.framework.reporter.UIReporter;

public class UILauncher extends G4Embeddable {

    private static AllureReporter reporter = new UIReporter();

    public UILauncher() {
        super(reporter);
    }
}
