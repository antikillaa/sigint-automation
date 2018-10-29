package ae.pegasus.framework.steps;

import ae.pegasus.framework.app_context.properties.G4Properties;
import ae.pegasus.framework.assertion.Asserter;
import ae.pegasus.framework.data_generators.TextLinesGenerator;
import ae.pegasus.framework.utils.FileHelper;
import ae.pegasus.framework.utils.TimeUtils;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import ae.pegasus.framework.context.Context;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ae.pegasus.framework.utils.PageUtils;

import java.io.IOException;
import java.net.URL;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class UISteps {

    protected final Logger log = Logger.getLogger(this.getClass());
    private boolean skipReloading = true;

    @BeforeStory
    public void initWebDriver() throws IOException {
        Boolean remoteRun = G4Properties.getRunProperties().isRemoteRun();
        String browser = G4Properties.getRunProperties().getWebBrowser();
        String workingDir = System.getProperty("user.dir") + "/chromedriver.exe";
        if (remoteRun) {
            String seleniumHub = G4Properties.getRunProperties().getSeleniumHub();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            RemoteWebDriver webDriver = new RemoteWebDriver(new URL(String.format("http://%s:4444/wd/hub", seleniumHub)), capabilities);
            webDriver.manage().window().setSize(new Dimension(1920,1080));
            WebDriverRunner.setWebDriver(webDriver);
        } else {
            Configuration.browser = browser;
            Configuration.startMaximized = true;
            Configuration.openBrowserTimeoutMs = Long.parseLong(System.getProperty("selenide.openBrowserTimeout", "600000"));
            System.setProperty("chromeoptions.args", "--no-proxy-server");
            //Stage
           // System.setProperty("webdriver.chrome.driver", workingDir);
        }
        Configuration.timeout = Long.parseLong(System.getProperties().getProperty("selenide.timeout", "40000"));
        Configuration.collectionsTimeout = Long.parseLong(System.getProperties().getProperty("selenide.collectionsTimeout", "40000"));
    }

    @BeforeScenario(uponType = ScenarioType.ANY)
    public void resetFrameworkAndBrowser() throws IOException {
        Context.getContext().clearContext();
        Context.getContext().rememberScenarioStartTime();
        if (skipReloading) {
            skipReloading = false;
        } else {
            if (getWebDriver() != null) {
                WebDriver driver = getWebDriver();
                driver.manage().deleteAllCookies();
                driver.navigate().refresh();
                driver.manage().deleteAllCookies();
                driver.navigate().refresh();
            } else {
                initWebDriver();
            }
        }
    }

    @BeforeStories
    public void onStoriesStart() {
        String zoneId = G4Properties.getRunProperties().getTimeZoneId();
        if (zoneId != null) {
            TimeUtils.setZoneId(zoneId);
        }
        TextLinesGenerator.setPathToTextSource(FileHelper.getPathToResourceFile("text_source/CrimeAndPunishment.txt"));
    }

    @BeforeStory
    public void onStoryStart() {
        skipReloading = true;
    }

    private static final String CHECK_JS_ERRORS_STEP_DEFINITION = "Check JS errors";
    public static final String CHECK_JS_ERRORS_STEP_FULL_DEFINITION = "Then " + CHECK_JS_ERRORS_STEP_DEFINITION;
    @Then(CHECK_JS_ERRORS_STEP_DEFINITION)
    public void checkJSErrors() {
        try {
            Asserter.getAsserter().softAssertFalse(PageUtils.checkJsErrors(log), "JS errors are absent", "JS errors are present");
        } catch (Exception e) {
            log.warn("Unable to get JS log");
        }
    }

    @AfterStory
    public void disposeDriver() {
        if (getWebDriver() != null) {
            getWebDriver().quit();
        }
    }
}
