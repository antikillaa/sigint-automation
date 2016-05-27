package pages.blocks.tables;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import pages.BaseSection;

import static com.codeborne.selenide.Condition.present;
import static com.codeborne.selenide.Selenide.sleep;

public abstract class Table extends BaseSection {

    Logger log = Logger.getRootLogger();
    private static final String baseSelector = "div.pg-table";

    public Table() {
        super(baseSelector);
    }

    public SelenideElement getRowByIndex(int indexRow) {
        return get()
                .$(By.xpath(".//div[contains(@class, 'pg-row')][" + indexRow + "]"));
    }

    public SelenideElement getTableHead() {
        return get()
                .$("./div[@class='pg-thead']");
    }

    public ElementsCollection getPgRows() {
        return get().$$("div.pg-row");
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
        return get().$(new Selectors.ByText("Nothing found.")).isDisplayed();
    }



}
