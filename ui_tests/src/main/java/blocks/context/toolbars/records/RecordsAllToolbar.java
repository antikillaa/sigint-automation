package blocks.context.toolbars.records;

import blocks.context.toolbars.EntityToolbar;
import blocks.context.toolbars.records.panels.RecordsActionPanel;
import blocks.context.toolbars.records.panels.RecordsFilterPanel;

import static com.codeborne.selenide.Selenide.page;

public class RecordsAllToolbar extends EntityToolbar {

    @Override
    public RecordsActionPanel getActions() {
        return page(RecordsActionPanel.class);
    }

    @Override
    public RecordsFilterPanel getFilters() {
        return page(RecordsFilterPanel.class);
    }
}
