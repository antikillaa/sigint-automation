package pages.blocks;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.reports.ReportsDraftPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class SidebarRightWrapper {

    public SelenideElement getSidebarRightWrapper() {
        return $(By.xpath("//div[@class='sidebar-right-wrapper']"));
    }

    public SelenideElement getSaveDraftButton() {
        return getSidebarRightWrapper()
                .$(By.xpath(".//button[contains(normalize-space(),'Save as Draft')]"));
    }

    public ReportsDraftPage clickSaveDraft() {
        getSaveDraftButton().click();
        return page(ReportsDraftPage.class);
    }

    public ReportsDraftPage clickSubmitButton() {
        $(By.xpath(".//button[@ref='publishButton' and contains(normalize-space(), 'Submit')]")).click();
        return page(ReportsDraftPage.class);
    }

    public void clickPublishButton() {
        $(By.xpath(".//button[@ref='publishButton' and contains(normalize-space(), 'Publish')]")).click();
    }

}
