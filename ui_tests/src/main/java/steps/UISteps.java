package steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import model.AppContext;
import model.Record;
import model.Report;
import model.User;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.Navigator;
import pages.Pages;
import pages.SigintPage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class UISteps extends GlobalSteps {

    static AppContext context = AppContext.getContext();
    Pages pages = new Pages();
    static User user;
    static Navigator navigator = new Navigator();

    @Given("I navigate to $mainMenu -> $subMenu page" )
    public void navigateTo(String mainMenu, String subMenu) {
        SigintPage page = navigator.navigate_to(mainMenu, subMenu);
        context.put("page", page);
    }

    @BeforeStory
    public void InitWebDriver() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/web.properties");
        Properties p = new Properties();
        p.load(is);
        Boolean remoteRun = Boolean.valueOf(AppContext.getContext().getGeneralProperties().getProperty("remoteRun"));
        if (remoteRun) {
            String browser = p.getProperty("webBrowser");
            String seleniumHub = p.getProperty("seleniumHub");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            RemoteWebDriver webDriver = new RemoteWebDriver(new URL(String.format("http://%s:4444/wd/hub", seleniumHub)), capabilities);
            webDriver.manage().window().setSize(new Dimension(1920, 1080));
            WebDriverRunner.setWebDriver(webDriver);
        }
        Configuration.timeout = Long.parseLong(System.getProperties().getProperty("selenide.timeout", "30000"));
        Configuration.collectionsTimeout = Long.parseLong(System.getProperties().getProperty("selenide.collectionsTimeout", "30000"));
    }

    @BeforeScenario
    public void reloadBrowser() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @AfterStory
    public void disposeDriver() {
        getWebDriver().quit();
    }

    Report getReportFromContext() {
        return context.get("report", Report.class);
    }

    Record getRecordFromContext() {
        return context.get("record", Record.class);
    }

}
