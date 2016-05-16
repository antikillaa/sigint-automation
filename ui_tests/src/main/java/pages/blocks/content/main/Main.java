package pages.blocks.content.main;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class Main {

    public SelenideElement get() {
        return $(By.xpath(".//div[@class='pg-content']/div[@class='main']"));
    }

}
