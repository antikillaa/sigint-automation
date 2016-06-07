package blocks.context.toolbars.records.panels;

import org.openqa.selenium.By;
import blocks.context.toolbars.TableToolbar;

public class RecordsActionPanel extends TableToolbar {


    public void clickCreateRecord(){
        base().$(By.xpath("//button[@click.trigger='createNewRecord()']")).click();
    }

    public void clickCreateReport(){
        base().$(By.xpath("//button[@click.trigger='createReportFromMultipleRecords()']")).click();
    }



}
