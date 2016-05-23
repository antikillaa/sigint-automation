package pages.reports.sidebar;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.reports.ReportsDraftPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class SidebarRightWrapper {

    public SelenideElement getSidebar() {
        return $(By.xpath("//div[@class='sidebar-right-wrapper']"));
    }

    public SelenideElement getSaveDraftButton() {
        return getSidebar()
                .$(By.xpath(".//button[contains(normalize-space(),'Save as Draft')]"));
    }

    public ReportsDraftPage clickSaveDraft() {
        getSaveDraftButton().click();
        return page(ReportsDraftPage.class);
    }

    public SelenideElement getSubmitButton() {
        return $(By.xpath(".//button[@ref='publishButton' and contains(normalize-space(), 'Submit')]"));
    }

    public ReportsDraftPage clickSubmitButton() {
        getSubmitButton().click();
        return page(ReportsDraftPage.class);
    }

    public SelenideElement getPublishButton() {
        return getSidebar()
                .$(By.xpath(".//button[@ref='publishButton' and contains(normalize-space(), 'Publish')]"));
    }

    public void clickPublishButton() {
        getPublishButton().click();
    }

    public SelenideElement getRemoveOwnership(){
        return getSidebar()
                .$(By.xpath(".//button[@click.trigger='removeReportOwnership()']"));
    }

    public void clickRemoveOwnership() {
        getRemoveOwnership().click();
    }

    public SelenideElement getTakeOwnershipButton() {
        return getSidebar()
                .$(By.xpath(".//button[@click.trigger='takeReportOwnership()']"));
    }

    public SidebarRightWrapper clickTakeOwnershipButton() {
        getTakeOwnershipButton().click();
        return this;
    }

    public SelenideElement getRefreshButton() {
        return getSidebar()
                .$(By.xpath(".//button[@click.delegate='refreshPhonesInfo()']"));
    }

    public SidebarRightWrapper clickRefreshButton() {
        getRefreshButton().click();
        return this;
    }

    public SelenideElement getDeleteButton() {
        return getSidebar()
                .$(By.xpath(".//button[@ref='deleteBtn']"));
    }

    public SidebarRightWrapper clickDeleteButton() {
        getDeleteButton().click();
        return this;
    }

}
