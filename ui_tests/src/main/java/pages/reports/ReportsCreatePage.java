package pages.reports;

import com.codeborne.selenide.SelenideElement;
import model.AppContext;
import org.openqa.selenium.By;
import pages.blocks.SidebarRightWrapper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class ReportsCreatePage {

    public static final String url = String.format("%s/#/app/reports/create", AppContext.getContext().getHost());

    private SidebarRightWrapper sidebarRightWrapper = page(SidebarRightWrapper.class);


    public SelenideElement getForm() {
        return $(By.xpath("//div[contains(@class, 'pg-form')]"));
    }

    public SelenideElement getSubject() {
        return getForm()
                .$(By.xpath(".//input[@id='subject']"));
    }

    public SidebarRightWrapper getSidebarRightWrapper() {
        return sidebarRightWrapper;
    }



}
