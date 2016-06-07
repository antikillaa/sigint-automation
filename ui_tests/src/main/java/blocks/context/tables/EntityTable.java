package blocks.context.tables;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityTable <T extends Row> {

    private Class<T> classType;
    private Table table = new Table();
    Logger log = Logger.getLogger(EntityTable.class);

    public EntityTable(Class<T> classType) {
        this.classType = classType;
    }

    public List<T> getEntities() {
        ArrayList<T> rows = new ArrayList<>();
        for (SelenideElement selRow : table.getRows()) {
            try {
                T row = classType.getConstructor(SelenideElement.class).newInstance(selRow);
                rows.add(row);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new AssertionError();
            }
        }
        return rows;
    }

    public void waitLoading() {
        table.getLoading().shouldBe(Condition.disappear);
    }


    public T getRecordByColumnNameAndValue(String columnName, String columnValue) {
        log.debug("Find record by column name:" + columnName + " and value:" + columnValue + " in the Records table");
        for (T recordRow : getEntities()) {
            if (recordRow.getCellByColumnName(columnName).getText().contentEquals(columnValue)) {
                return recordRow;
            }
        }

        log.warn("Record with column name:" + columnName + " and value:" + columnValue + " was not found in the Records table!");
        throw new AssertionError();
    }

    public T firstRecord() {
        return getEntities().get(0);
    }
}
