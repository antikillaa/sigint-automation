package pages.blocks.tables;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import pages.BasePage;

import static com.codeborne.selenide.Condition.present;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public abstract class Table extends BasePage {

    Logger log = Logger.getRootLogger();


    public SelenideElement getTable() {
        return $(By.xpath("//div[contains(@class, 'pg-table')]")).shouldBe(present);
    }

    public SelenideElement getLoading() {
        return getTable()
                .$(By.xpath(".//span[@class='inline-block' and contains(.,'Loading…')]"));
    }

    public SelenideElement getRowByIndex(int indexRow) {
        return getTable()
                .$(By.xpath(".//div[contains(@class, 'pg-row')][" + indexRow + "]"));
    }

    public SelenideElement getTableHead() {
        return getTable()
                .$("./div[@class='pg-thead']");
    }

    public ElementsCollection getPgRows() {
        return getTable()
                .$$(By.xpath(".//div[contains(@class, 'pg-row')]"));
    }

    public ElementsCollection getRows() {
        getPgRows().first().shouldBe(present);

        sleep(500); //TODO
        log.debug("Rows size: " + getPgRows().size());

        return getPgRows();
    }

    public ElementsCollection getColumns() {
        return getTableHead()
                .$$(By.xpath(".//div[contains(@class, 'pg-col')]"));
    }

    public int getColumnIndexByName(String columnName) {
        log.info("Find column by name:" + columnName + " in the Records table");

        String[] colTexts = getColumns().getTexts();
        for (int i = 0; i < colTexts.length; i++) {
            if (colTexts[i].equals(columnName)) {
                return i+1;
            }
        }

        log.warn("Column with name:" + columnName + " was not found in the Records table");
        throw new AssertionError("Column with name:" + columnName + " was not found in the Records table");
    }

    public boolean isEmpty(){
        return getTable().$(new Selectors.ByText("Nothing found.")).isDisplayed();
    }



}
