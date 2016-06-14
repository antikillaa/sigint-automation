package blocks.context.toolbars.records;

import blocks.context.toolbars.EntityToolbar;
import blocks.context.toolbars.records.panels.RecordsFilterPanel;
import blocks.context.toolbars.records.panels.RecordsProcessedActionPanel;

import static com.codeborne.selenide.Selenide.page;

public class RecordsProcessedToolbar extends EntityToolbar {

    @Override
    public RecordsProcessedActionPanel getActions() {
        return page(RecordsProcessedActionPanel.class);
    }

    @Override
    public RecordsFilterPanel getFilters() {
        return page(RecordsFilterPanel.class);
    }
}
