package steps;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import app_context.RunContext;
import app_context.properties.G4Properties;
import blocks.context.factories.RecordsControllerFactory;
import blocks.context.factories.ReportsControllerFactory;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import controllers.PageControllerFactory;
import controllers.reports.form_page.ReportFormFactoryController;
import java.io.IOException;
import java.net.URL;
import model.Record;
import model.Report;
import model.User;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.Navigator;

public class UISteps {

    static RunContext context = RunContext.get();
    static User user;
    static Navigator navigator = new Navigator();

    @When("I navigate to $mainMenu -> $subMenu page" )
    public void navigateTo(String mainMenu, String subMenu) {
        PageControllerFactory controller = navigator.navigate_to(mainMenu, subMenu);
        context.put("controller", controller);
    }

    @BeforeStory
    public void InitWebDriver() throws IOException {
        Boolean remoteRun = G4Properties.getRunProperties().isRemoteRun();
        if (remoteRun) {
            String browser = G4Properties.getRunProperties().getWebBrowser();
            String seleniumHub = G4Properties.getRunProperties().getSeleniumHub();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            RemoteWebDriver webDriver = new RemoteWebDriver(new URL(String.format("http://%s:4444/wd/hub", seleniumHub)), capabilities);
            webDriver.manage().window().setSize(new Dimension(1920, 1080));
            WebDriverRunner.setWebDriver(webDriver);
        }
        Configuration.timeout = Long.parseLong(System.getProperties().getProperty("selenide.timeout", "80000"));
        Configuration.collectionsTimeout = Long.parseLong(System.getProperties().getProperty("selenide.collectionsTimeout", "40000"));
    }

    @BeforeScenario
    public void reloadBrowser() {
        if (!getWebDriver().toString().contains("(null)")) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            driver.manage().deleteAllCookies();
            driver.navigate().refresh();
        }
    }

    @AfterStory
    public void disposeDriver() {
        if (!getWebDriver().toString().contains("(null)")) {
            getWebDriver().quit();}
    }

    Report getReportFromContext() {
        return context.get("report", Report.class);
    }

    Record getRecordFromContext() {
        return context.get("record", Record.class);
    }


    public RecordsControllerFactory getRecordsController() {
        return RunContext.get().get("controller", RecordsControllerFactory.class);
    }

    public ReportsControllerFactory getReportsController() {
        return RunContext.get().get("controller", ReportsControllerFactory.class);
    }

    public ReportFormFactoryController getReportsFormFactory() {
        return RunContext.get().get("controller", ReportFormFactoryController.class);
    }

    public User getUserFromContext() {
        return context.get("user", User.class);
    }


}
