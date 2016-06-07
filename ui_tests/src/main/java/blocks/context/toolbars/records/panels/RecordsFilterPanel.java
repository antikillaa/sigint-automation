package blocks.context.toolbars.records.panels;

import com.codeborne.selenide.Condition;
import blocks.context.toolbars.TableToolbar;

import static com.codeborne.selenide.Selenide.$$;

public class RecordsFilterPanel extends TableToolbar{

    public void setDate(String date) {
        base().$("input[ref='dateInput']").val(date);

    }

    public void applySearch() {
        $$("div.daterangepicker button.applyBtn").filter(Condition.visible)
                .get(0).click();

    }
}
