package blocks.context.toolbars.reports.panels;

import blocks.context.toolbars.TableToolbar;
import org.openqa.selenium.By;

public class ReportsActionPanel extends TableToolbar {

    public void clickCreateManualReportBtn() {
        base().$(By.xpath("//button[@click.trigger='manualReportCreationHandler()']")).click();

    }

}
