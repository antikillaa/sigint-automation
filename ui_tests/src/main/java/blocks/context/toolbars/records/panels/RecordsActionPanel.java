package blocks.context.toolbars.records.panels;

import blocks.context.toolbars.TableToolbar;
import org.openqa.selenium.By;

public class RecordsActionPanel extends TableToolbar {

    public void clickCreateReport(){
        base().$(By.xpath("//button[@click.trigger='createReportFromMultipleRecords()']")).click();
    }

    public void clickCreateRecord(){
        base().$(By.xpath("//button[@click.trigger='createNewRecord()']")).click();
    }

}
