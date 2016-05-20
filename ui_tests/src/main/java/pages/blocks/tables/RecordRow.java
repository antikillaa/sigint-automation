package pages.blocks.tables;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.records.RecordDetailsDialog;

import static com.codeborne.selenide.Selenide.page;

public class RecordRow extends Row {

    public RecordRow(SelenideElement row) {
        super(row);
    }


    public SelenideElement getTypeSMS() {
        return getRow()
                .$(By.xpath("./div[@class='pg-col col-fx-2']" +
                        "//i[@class='au-target fa fa-fw fa-envelope-o']"));
    }

    public SelenideElement getTypeVoice() {
        return getRow()
                .$(By.xpath("./div[@class='pg-col col-fx-2']" +
                        "//i[@class='au-target fa fa-fw fa-phone']"));
    }

    public SelenideElement getCreateReportButton() {
        return getRow()
                .$(By.xpath(".//button[@title='Create a Report']"));
    }

    public RecordDetailsDialog clickCreateReportButton() {
        getCreateReportButton().click();
        return page(RecordDetailsDialog.class);
    }

    public SelenideElement getRecordDescription() {
        return getRow()
                .$(By.xpath(".//record-description-decorator"));
    }

    public SelenideElement getToNumber() {
        return getRecordDescription()
                .$(By.xpath(".//div[2]/span[@class='au-target fx1']"));
    }

    public SelenideElement getFromNumber() {
        return getRecordDescription()
                .$(By.xpath(".//div[1]/span[@class='au-target fx1']"));
    }

    public SelenideElement getStatus() {
        return this.getCellByColumnName("Status")
                .$(By.xpath(".//i"));
    }

    public RecordRow selectRecord() {
        if (!getCheckBox().isSelected()) {
            getCheckBox().click();
        }
        return this;
    }
}
