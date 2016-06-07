package blocks.context.tables;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import pages.BaseSection;
import blocks.context.Context;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.present;
import static com.codeborne.selenide.Selenide.sleep;

public class Table extends BaseSection{

    Logger log = Logger.getLogger(Table.class);

    Table() {
        super(Context.baseSelector);
    }


    public SelenideElement getTableHead() {
        return base()
                .$("./div[@class='pg-thead']");
    }

    public SelenideElement getRowByIndex(int indexRow) {
        return base()
                .$(By.xpath(".//div[contains(@class, 'pg-row')][" + indexRow + "]"));
    }

    public ElementsCollection getPgRows() {
        getLoading().shouldBe(disappear);
        return base().$$("div.pg-row");
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
        return base().$(new Selectors.ByText("Nothing found.")).isDisplayed();
    }



}


