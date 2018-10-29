package ae.pegasus.framework.support.reporter;

import ae.pegasus.framework.assertion.Asserter;
import ae.pegasus.framework.reporter.AllureReporter;
import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.events.MakeAttachmentEvent;
import ru.yandex.qatools.allure.events.StepFailureEvent;
import ru.yandex.qatools.allure.events.StepFinishedEvent;
import ru.yandex.qatools.allure.events.TestCaseFailureEvent;
import ae.pegasus.framework.utils.PageUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UIReporter extends AllureReporter {

    private List<String> stepsFailedDueToSoftAsserts = new ArrayList<>();

    @Override
    public void successful(String step) {
        int errorCount = Asserter.getAsserter().getErrorCount();
        if (errorCount == 0) {
            super.successful(step);
        } else {
            softFailed(step, new Throwable(errorCount + " of soft assertions were failed"));
        }
    }

    @Override
    public void failed(String step, Throwable cause) {
        try {
            PageUtils.checkJsErrors(log);
        } catch (Exception e) {
            log.warn("Unable to get JS log");
        }
        super.failed(step, cause);
        Asserter.getAsserter().resetErrorCount();
    }

    private void softFailed(String step, Throwable cause) {
        allure.fire(new StepFailureEvent().withThrowable(cause));
        makeStepFailedAttachment();
        allure.fire(new StepFinishedEvent());
        if(!stepsFailedDueToSoftAsserts.contains(step)) {
            stepsFailedDueToSoftAsserts.add(step);
        }
        Asserter.getAsserter().resetErrorCount();
    }

    @Override
    public void afterScenario() {
        if (!stepsFailedDueToSoftAsserts.isEmpty()) {
            String message = "Following steps are failed due to soft assertions:\n"
            + String.join("\n", stepsFailedDueToSoftAsserts);
            allure.fire(new TestCaseFailureEvent().withThrowable(new AssertionError(message)));
            stepsFailedDueToSoftAsserts.clear();
        }
        super.afterScenario();
    }

    @Attachment(type = "image/png")
    private byte[] screenshot() throws IOException {
        File screenshot = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }

    @Override
    protected void takeScreenshot() {
        try {
            allure.fire(new MakeAttachmentEvent(screenshot(), "Page screenshot", "image/png"));
        } catch (Exception e) {
            log.error("Unable to take screenshot", e);
        }
    }

    @Override
    public void beforeScenario(String scenarioTitle) {
        stepsFailedDueToSoftAsserts.clear();
        Asserter.getAsserter().resetErrorCount();
        super.beforeScenario(scenarioTitle);
    }
}